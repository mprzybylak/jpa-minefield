package com.mprzybylak.minefields.jpa.relationships.onetomany.uni;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	private long id;
	
	public Book() {
	}
	
	public Book(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
}
