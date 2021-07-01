package com.sf.ennahdi.example.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Film {
	@Id
	@Column(name="film_id")
    private Long id;
	
	private String title;
	
	private String description;
	
	@JoinColumn(name = "language_id")
	@ManyToOne
	private Language language;
	
	public Film() {
		// TODO Auto-generated constructor stub
	}
	
	public Film(Long id, String title, String description, Language language) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.language = language;
	}

	@Override
	public String toString() {
		return this.id + " " + this.title + " " + this.description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
