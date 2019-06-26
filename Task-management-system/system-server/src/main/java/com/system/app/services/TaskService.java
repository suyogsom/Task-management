package com.system.app.services;

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
import com.system.app.models.SystemTask;
import com.system.app.repositories.TaskRepo;

@Service
public class TaskService {

	@Autowired
	private TaskRepo taskRepo;

	public List<SystemTask> getAllTasks(){
		return taskRepo.findAll();
	}

	public ResponseEntity<SystemTask> getTask(UUID id){ 
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}
		else {
			return new ResponseEntity<>(taskRepo.findById(id).get(), HttpStatus.OK);		
		}	
	}

	public ResponseEntity<SystemTask> addTask(SystemTask task){	
		int count = 0 ;

		if(task.getName()==null||task.getDescription()==null||task.getStatus()==null) {
			count++; throw new NullFieldsException(); 
		}
		if(task.getName().trim().length()==0||task.getDescription().trim().length()==0) {
			count++; throw new EmptyBlankFieldsException(); 
		}
		if(task.getName().trim().equalsIgnoreCase("null")||task.getDescription().trim().equalsIgnoreCase("null")) {
			count++; throw new FieldValueNullException(); 
		}

		if(count > 0) {
			return new ResponseEntity<>(task, HttpStatus.BAD_REQUEST);
		}
		else {
			taskRepo.save(task);
			return new ResponseEntity<>(task, HttpStatus.OK);
		}
	}

	public ResponseEntity<SystemTask> updateTask(SystemTask task, UUID id) {
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}	
		else {
			if(task.getName()==null||task.getDescription()==null||task.getStatus()==null) {
				throw new NullFieldsException(); 
			}
			if(task.getName().trim().length()==0||task.getDescription().trim().length()==0||task.getStatus().toString().length()==0) {
				throw new EmptyBlankFieldsException(); 
			}
			if(task.getName().trim().equalsIgnoreCase("null")||task.getDescription().trim().equalsIgnoreCase("null")) {
				throw new FieldValueNullException(); 
			}
			
			if(!availabilityCheck(task.getTaskId())) { 
				throw new UUIDUpdateException(); 
			}
			 			
			SystemTask taskToUpdate = taskRepo.findById(id).get();
			taskToUpdate = task;
			taskRepo.save(taskToUpdate);
		    return new ResponseEntity<>(taskRepo.findById(id).get(), HttpStatus.OK);
		}		
	}

	public ResponseEntity<String> deleteTask(UUID id){			 
		if(!availabilityCheck(id)) {
			throw new ResourceNotFoundException();
		}
		else {
			taskRepo.deleteById(id);
			return new ResponseEntity<>("[ Task id " + id + " deleted ]", HttpStatus.OK);
		}		
	}
	
	public boolean availabilityCheck(UUID id) {
		return taskRepo.existsById(id);
	}
}
