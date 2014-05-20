package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Computer {

	@Id
	private long id;
	
	public Computer() {
	}
	
	public Computer(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
}
