package com.system.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.app.models.JobSummary;

@Repository
public interface JobSummaryRepo extends JpaRepository<JobSummary, UUID>{ 
}