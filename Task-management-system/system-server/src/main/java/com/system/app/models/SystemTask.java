package com.system.app.models;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.system.app.audit.Auditable;
import com.system.app.models.interfaces.Status;


@Entity
@Table(name="Tasks")
@EntityListeners(AuditingEntityListener.class)
public class SystemTask extends Auditable<String> {
	
	private UUID taskId;
	private String name,description;
	
	private Status status;
	private Job job;

	
	/**
	 *  with job and ID just to update task
	 */
	public SystemTask(UUID id, Job job,Status department, String name, String description) {		
		super(); 
		this.taskId = id;  
		this.status=department;  
		this.name = name;
		this.description = description; 
		this.job = job;
	}
	
	
	/**
	 *  without id just to add in database //with Job
	 */
	public SystemTask(Status department, String name, String description) {		
		super(); 
		this.status=department;  
		this.name = name;
		this.description = description; 
		//this.job = job;
	}	

	/**
	 *  without job just to update task
	 */
	public SystemTask(UUID id, Status department, String name, String description) {		
		super(); 
		this.taskId = id;  
		this.status=department;  
		this.name = name;
		this.description = description; 	
	}
	
	public SystemTask() {
	}	
	
	/**
	 *  to generate id automatically  @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	 *  @Type(type="uuid-char") convert UUID to char string 
	 */
	@Id
	@Column(name = "taskId",updatable = false, nullable = false) 
	@Type(type="uuid-char")
	@GeneratedValue(generator = "uuid2",strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
	public UUID getTaskId() {
		return taskId;
	}

	public void setTaskId(UUID taskId) {
		this.taskId = taskId;
	}
	
	@Column(name = "name", nullable = false)
	@Size(max = 50)
	public String getName() {
		return name;
	}
			
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "deacription", nullable = false)
	@Size(max = 50)
	public String getDescription() {
		return description;
	}
			
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable = false)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity=Job.class)
	@JoinColumn(name="jobIdFK")
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
}
