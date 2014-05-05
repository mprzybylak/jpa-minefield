package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Department {
	
	@Id
	private long id;

	public Department() {
	}

	public Department(long id) {
		this.id = id;
	}
	

}
