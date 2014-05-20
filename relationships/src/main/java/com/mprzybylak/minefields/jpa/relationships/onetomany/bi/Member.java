package com.mprzybylak.minefields.jpa.relationships.onetomany.bi;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Owner of the realation
 * Bidirectional OneToMany and ManyToOne is in fact the same
 */
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;

	
	@ManyToOne(
			fetch=FetchType.EAGER // eager fetch by default but it can be changed by "fetch" parameter
			)
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
