package com.system.app.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.system.app.audit.Auditable;
import com.system.app.models.interfaces.Status;


@Entity
@Table(name="Jobs")
@EntityListeners(AuditingEntityListener.class)
public class Job extends Auditable<String> {
	
	private UUID jobId;
	private String name,description;
	
	private Status status;

	public Job(Status department, String name, String description) {		
		super(); 
		this.status=department;  
		this.name = name;
		this.description = description; 	
	}	

	/**
	 *  without job just to update task
	 */
	public Job(UUID id, Status department, String name, String description) {		
		super(); 
		this.jobId = id;  
		this.status=department;  
		this.name = name;
		this.description = description; 	
	}
	
	public Job() {
	}	
	
	/**
	 *  to generate id automatically  @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	 *  @Type(type="uuid-char") convert UUID to char string 
	 */
	@Id
	@Column(name = "jobId",updatable = false, nullable = false) 
	@Type(type="uuid-char")
	@GeneratedValue(generator = "uuid2",strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
	public UUID getJobId() {
		return jobId;
	}

	public void setJobId(UUID taskId) {
		this.jobId = taskId;
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
}
