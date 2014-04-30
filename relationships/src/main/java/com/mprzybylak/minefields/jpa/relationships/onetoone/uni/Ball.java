package com.mprzybylak.minefields.jpa.relationships.onetoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ball {

	@Id
	private long id;

	public Ball(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

}
