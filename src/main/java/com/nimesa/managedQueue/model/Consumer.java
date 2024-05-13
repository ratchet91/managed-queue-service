package com.nimesa.managedQueue.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Consumer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userName", columnDefinition = "TEXT")
	private String userName;

	@Column(name = "isActive")
	private Boolean isActive;

	@Column(name = "date")
	private String date;

	@Column(name = "lastUpdatedTimeStamp")
	private String lastUpdatedTimeStamp;

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLastUpdatedTimeStamp() {
		return lastUpdatedTimeStamp;
	}

	public void setLastUpdatedTimeStamp(String lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Consumer(String userName, Boolean isActive) {
		this.userName = userName;
		this.date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
		this.isActive = isActive;
		this.lastUpdatedTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
				.format(new Date(System.currentTimeMillis()));
	}

}