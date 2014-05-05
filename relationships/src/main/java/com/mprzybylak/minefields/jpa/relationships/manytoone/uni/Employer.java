package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employer {

	@Id
	private long id;

	@ManyToOne
	private Department department;

	public Employer() {
	}
	
	public Employer(long id) {
		this.id = id;
	}

	public Object getId() {
		return id;
	}

	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}

}
