package com.ssanta.racetracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="Events")
public class Event {
	private long id;
	private String status;
	private Horse horse;
	private long time;

	@Column(name="event_id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}
	 @JsonSetter("event")
	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
    @JoinColumn(name="horse_fk", insertable = false, updatable = false)
	public Horse getHorse() {
		return horse;	
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	

}
