package com.mprzybylak.minefields.jpa.relationships.onetomany.bi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Owner of the realation
 */
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;

	@ManyToOne
	private Team team;
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public long getId() {
		return id;
	}
}
