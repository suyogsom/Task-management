package com.system.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.system.app.exceptions.EmptyBlankFieldsException;
import com.system.app.exceptions.FieldValueNullException;
import com.system.app.exceptions.NullFieldsException;
import com.system.app.exceptions.ResourceNotFoundException;
import com.system.app.exceptions.UUIDUpdateException;
import com.system.app.models.Job;
import com.system.app.models.JobSummary;
import com.system.app.models.SystemTask;
import com.system.app.models.interfaces.Status;
import com.system.app.repositories.JobRepo;
import com.system.app.repositories.TaskRepo;

@Service
public class JobService {

	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private TaskRepo taskRepo;
	
	JobSummary jobSummary = new JobSummary();

	public List<Job> getAllJobs(){
		return jobRepo.findAll();
	}

	public ResponseEntity<Job> getJob(UUID id){ 
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}
		else {			
			Job job = jobRepo.findById(id).get();
			job.setStatus(setJobStatusUsingAllTaskStatus(id));	
			job.setJobSummary(jobSummary);
			ResponseEntity<Job> job1 = updateJob( job, id);
			return job1;		
		}	
	}

	public ResponseEntity<Job> addJob(Job job){	
		int count = 0 ;

		if(job.getName()==null||job.getDescription()==null) {
			count++; throw new NullFieldsException(); 
		}
		if(job.getName().trim().length()==0||job.getDescription().trim().length()==0) {
			count++; throw new EmptyBlankFieldsException(); 
		}
		if(job.getName().trim().equalsIgnoreCase("null")||job.getDescription().trim().equalsIgnoreCase("null")) {
			count++; throw new FieldValueNullException(); 
		}

		if(count > 0) {
			return new ResponseEntity<>(job, HttpStatus.BAD_REQUEST);
		}
		else {
			jobRepo.save(job);
			return new ResponseEntity<>(job, HttpStatus.OK);
		}
	}

	public ResponseEntity<Job> updateJob(Job job, UUID id) {
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}	
		else {
			if(job.getName()==null||job.getDescription()==null) {
				throw new NullFieldsException(); 
			}
			if(job.getName().trim().length()==0||job.getDescription().trim().length()==0||job.getStatus().toString().length()==0) {
				throw new EmptyBlankFieldsException(); 
			}
			if(job.getName().trim().equalsIgnoreCase("null")||job.getDescription().trim().equalsIgnoreCase("null")) {
				throw new FieldValueNullException(); 
			}
			
			if(!availabilityCheck(job.getJobId())) { 
				throw new UUIDUpdateException(); 
			}
			 			
			Job taskToUpdate = jobRepo.findById(id).get();
			taskToUpdate = job;
			jobRepo.save(taskToUpdate);
		    return new ResponseEntity<>(jobRepo.findById(id).get(), HttpStatus.OK);
		}		
	}

	public ResponseEntity<String> deleteJob(UUID id){			 
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}
		else {
			jobRepo.deleteById(id);
			return new ResponseEntity<>("[ Task id " + id + " deleted ]", HttpStatus.OK);
		}		
	}
	
	public List<SystemTask> getAllTask(UUID jobId){
		if(!availabilityCheck(jobId)) {
			throw new ResourceNotFoundException();
		}
		else {
			List<SystemTask> taskList = taskRepo.findAll();
			List<SystemTask> taskListWithJobId = new ArrayList<SystemTask>();
			
			for(int i=0;i<taskList.size();i++) {
				if(taskList.get(i).getJob().getJobId().equals(jobId)) {
					taskListWithJobId.add(taskList.get(i));
					}	
				}
			return taskListWithJobId;
		}	
	}
	
	public Status setJobStatusUsingAllTaskStatus(UUID id) {
		List<SystemTask> taskList = taskRepo.findAll();
		
		Integer ok=0, queued=0, cancelled=0, failed=0, completed=0,running=0;
		
		for(int i=0;i<taskList.size();i++) {
			if(taskList.get(i).getJob().getJobId().equals(id)) {
				if(taskList.get(i).getStatus()==Status.CANCELLED)cancelled++;
				if(taskList.get(i).getStatus()==Status.COMPLETED)completed++;
				if(taskList.get(i).getStatus()==Status.FAILED)failed++;
				if(taskList.get(i).getStatus()==Status.OK)ok++;
				if(taskList.get(i).getStatus()==Status.QUEUED)queued++;
				if(taskList.get(i).getStatus()==Status.RUNNING)running++;
				}	
			}
		
		jobSummary.setOkCount(ok);
		jobSummary.setQueuedCount(queued);
		jobSummary.setRunningCount(running);
		jobSummary.setCancelledCount(cancelled);
		jobSummary.setCompletedCount(completed);
		jobSummary.setFailedCount(failed);
		//jobSummary.setJobSummaryId(id);
		
		Map<Status, Integer> allStatus = new HashMap<Status, Integer>();
		allStatus.put(Status.CANCELLED, cancelled);
		allStatus.put(Status.COMPLETED,completed);
		allStatus.put(Status.FAILED,failed);
		allStatus.put(Status.OK,ok);
		allStatus.put(Status.QUEUED,queued);
		allStatus.put(Status.RUNNING,running);
		
		Map<Status, Integer> allStatusSorted = allStatus.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		
		Status jobStatus = null;
		
		if(ok==0&&queued==0&&cancelled==0&&failed==0&&completed==0&&running==0) {
			jobStatus = Status.NoTaskAssignedYet;			
		}
		else {
			Iterator<Entry<Status, Integer>> entries = allStatusSorted.entrySet().iterator();
			while (entries.hasNext()){
			    Entry<Status, Integer> entry = entries.next();
			    jobStatus = entry.getKey();
			}			
		}
		return jobStatus;
	}
	
	public boolean availabilityCheck(UUID id) {
		return jobRepo.existsById(id);
	}
}
