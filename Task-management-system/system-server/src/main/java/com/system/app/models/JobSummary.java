package com.system.app.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name="JobsSummary")
public class JobSummary{
	
	private UUID jobSummaryId;
	
	private Integer okCount, runningCount, cancelledCount, failedCount, queuedCount, completedCount;

	/**
	 *  without job just to update task
	 */
	public JobSummary(UUID id) {		
		super(); 
		this.jobSummaryId = id;   	
	}
	
	public JobSummary() {
	}	
	
	/**
	 *  to generate id automatically  @GeneratedValue(strategy=GenerationType.SEQUENCE) 
	 *  @Type(type="uuid-char") convert UUID to char string 
	 */
	@Id
	@Column(name = "jobSummaryId",updatable = false, nullable = false,insertable=false) 
	@Type(type="uuid-char")
	@GeneratedValue(generator = "uuid2",strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	public UUID getJobSummaryId() {
		return jobSummaryId;
	}

	public void setJobSummaryId(UUID jobSummaryId) {
		this.jobSummaryId = jobSummaryId;
	}

	public Integer getOkCount() {
		return okCount;
	}

	public void setOkCount(Integer okCount) {
		this.okCount = okCount;
	}

	public Integer getRunningCount() {
		return runningCount;
	}

	public void setRunningCount(Integer runningCount) {
		this.runningCount = runningCount;
	}

	public Integer getCancelledCount() {
		return cancelledCount;
	}

	public void setCancelledCount(Integer cancelledCount) {
		this.cancelledCount = cancelledCount;
	}

	public Integer getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	public Integer getQueuedCount() {
		return queuedCount;
	}

	public void setQueuedCount(Integer queuedCount) {
		this.queuedCount = queuedCount;
	}

	public Integer getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}
	
	
// 	this is for bidirectional but it does not work 
//	one to one has three types of implementations associative table, fk, and primaryKeyColumn	
//	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//	@MapsId
//	@JoinColumn(name="job_id_fk")
//	public Job getJob() {
//		return job;
//	}
//
//	public void setJob(Job job) {
//		this.job = job;
//	}
}
