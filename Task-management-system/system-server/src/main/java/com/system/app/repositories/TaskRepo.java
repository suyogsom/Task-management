package com.system.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.app.models.SystemTask;

@Repository
public interface TaskRepo extends JpaRepository<SystemTask, UUID>{ 
	
}


