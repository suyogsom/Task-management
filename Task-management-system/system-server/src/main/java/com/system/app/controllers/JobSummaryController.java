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

import com.system.app.models.JobSummary;
import com.system.app.services.JobSummaryService;

@RestController
@RequestMapping(value="/jobSummary",consumes = {"application/json", "application/xml"},produces = {"application/json", "application/xml"})
public class JobSummaryController {
	
	@Autowired
	private JobSummaryService jobSummaryService;		
	
	@GetMapping(value="/all")
	public List<JobSummary> getAllJobsSummary(){	
		return jobSummaryService.getAllJobs(); 
	}	

	@GetMapping(value="/{jobId}")
	public ResponseEntity<JobSummary> getJobSummary(@PathVariable UUID jobId)  {	
		return jobSummaryService.getJobSummary(jobId);	
	}

	@PostMapping(value="/add")
	public ResponseEntity<JobSummary> addJobSummary(@Valid @RequestBody JobSummary job)  {	
		return jobSummaryService.addJobSummary(job);   
	}

	@PostMapping(value="/update/{id}")
	public ResponseEntity<JobSummary> updateJobSummary(@Valid @RequestBody JobSummary job, @PathVariable UUID id)  {	
		return jobSummaryService.updateJobSummary(job, id);  
	}

	@PostMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteJobSummary(@PathVariable UUID id)  {   
		return jobSummaryService.deleteJobSummary(id);	
	}		
}
