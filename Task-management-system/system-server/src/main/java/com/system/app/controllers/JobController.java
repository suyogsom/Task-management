package com.system.app.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.app.models.Job;
import com.system.app.models.SystemTask;
import com.system.app.services.JobService;

@RestController
@RequestMapping(value="/job",consumes = {"application/json", "application/xml"},produces = {"application/json", "application/xml"})
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping(value="/all")
	public List<Job> getAllJobs(){	
		return jobService.getAllJobs(); 
	}	

	@GetMapping(value="/{jobId}")
	public ResponseEntity<Job> getJob(@PathVariable UUID jobId)  {	
		return jobService.getJob(jobId);	
	}
	
	@GetMapping(value="/{jobId}/tasks")
	public List<SystemTask> getJobAndTasks(@PathVariable UUID jobId)  {	
		return jobService.getAllTask(jobId);	
	}

	@PostMapping(value="/add")
	public ResponseEntity<Job> addJob(@Valid @RequestBody Job job)  {	
		return jobService.addJob(job);   
	}

	@PostMapping(value="/update/{id}")
	public ResponseEntity<Job> updateJob(@Valid @RequestBody Job job, @PathVariable UUID id)  {	
		return jobService.updateJob(job, id);  
	}

	@PostMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable UUID id)  {   
		return jobService.deleteJob(id);	
	}									
}
