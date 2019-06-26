package com.system.app.services;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.system.app.models.JobSummary;
import com.system.app.repositories.JobSummaryRepo;

@Service
public class JobSummaryService {

	@Autowired
	private JobSummaryRepo jobSummaryRepo;
	
	public boolean availabilityCheck(UUID id) {
		return jobSummaryRepo.existsById(id);
	}

	public List<JobSummary> getAllJobs() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<JobSummary> getJobSummary(UUID jobId) {
		return new ResponseEntity<>(jobSummaryRepo.findById(jobId).get(), HttpStatus.OK);
	}

	public ResponseEntity<JobSummary> addJobSummary(@Valid JobSummary job) {
		jobSummaryRepo.save(job);
		return new ResponseEntity<>(job, HttpStatus.OK);
	}

	public ResponseEntity<JobSummary> updateJobSummary(@Valid JobSummary job, UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<String> deleteJobSummary(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
}
