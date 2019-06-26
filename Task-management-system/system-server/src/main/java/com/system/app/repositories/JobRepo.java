package com.system.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.app.models.Job;

@Repository
public interface JobRepo extends JpaRepository<Job, UUID>{ 
}