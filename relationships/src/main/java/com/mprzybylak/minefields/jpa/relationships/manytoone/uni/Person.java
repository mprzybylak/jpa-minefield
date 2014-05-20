package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Person {

	@Id
	private long id;
	
	@ManyToOne
	private Computer computer;
	
	public Person() {
	}
	
	public Person(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	
	public Computer getComputer() {
		return computer;
	}
	
}
