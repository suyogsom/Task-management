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

import com.system.app.models.SystemTask;
import com.system.app.services.TaskService;

@RestController
@RequestMapping(value="/task",consumes = {"application/json", "application/xml"},produces = {"application/json", "application/xml"})
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping(value="/all")
	public List<SystemTask> getAllTasks(){	
		return taskService.getAllTasks(); 
	}	

	@GetMapping(value="/{taskId}")
	public ResponseEntity<SystemTask> getTTask(@PathVariable UUID taskId)  {	
		return taskService.getTask(taskId);	
	}

	@PostMapping(value="/add")
	public ResponseEntity<SystemTask> addTask(@Valid @RequestBody SystemTask task)  {	
		return taskService.addTask(task);   
	}

	@PostMapping(value="/update/{id}")
	public ResponseEntity<SystemTask> updateTask(@Valid @RequestBody SystemTask task, @PathVariable UUID id)  {	
		return taskService.updateTask(task, id);  
	}

	@PostMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable UUID id)  {   
		return taskService.deleteTask(id);	
	}									
}
