package com.system.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.system.app.models.SystemTask;
import com.system.app.repositories.JobRepo;
import com.system.app.repositories.TaskRepo;

@Service
public class JobService {

	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private TaskRepo taskRepo;

	public List<Job> getAllJobs(){
		return jobRepo.findAll();
	}

	public ResponseEntity<Job> getJob(UUID id){ 
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}
		else {
			return new ResponseEntity<>(jobRepo.findById(id).get(), HttpStatus.OK);		
		}	
	}

	public ResponseEntity<Job> addJob(Job job){	
		int count = 0 ;

		if(job.getName()==null||job.getDescription()==null||job.getStatus()==null) {
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
			if(job.getName()==null||job.getDescription()==null||job.getStatus()==null) {
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
	
	public boolean availabilityCheck(UUID id) {
		return jobRepo.existsById(id);
	}

	
}
