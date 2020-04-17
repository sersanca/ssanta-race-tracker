package com.ssanta.racetracker.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Horse {
	private long id;
	private String name;
	private Set<Event> events;
	
	@Column(name="id")
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@JsonSetter("horse")
	public void setName(String name) {
		this.name = name;
	}
	
	 
	@OneToMany(mappedBy="horse")
	@JsonIgnore
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
