package com.sf.ennahdi.example.dao.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Language {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "language_id")
    private Long id;
	
	private String name;
	
	@Column(name = "last_update")
	private Date lastUpdate;
	
	public Language() {
		
	}
	
	public Language(Long id) {
		this(id, "", null);
	}
	
	public Language(Long id, String name, Date lastUpdate) {
		this.id = id;
		this.name = name;
		this.lastUpdate = lastUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
