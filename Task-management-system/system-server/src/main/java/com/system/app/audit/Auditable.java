package com.system.app.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
        
    protected U createdBy,lastModifiedBy;          
    protected LocalDateTime createdDate,lastModifiedDate;

    @CreatedBy
    @Column(name = "created_by",updatable = false, nullable = false)
    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }
    
    @CreatedDate
    @Column(name = "created_date",updatable = false, nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @LastModifiedBy
    @Column(name = "last_modified_by")
    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
