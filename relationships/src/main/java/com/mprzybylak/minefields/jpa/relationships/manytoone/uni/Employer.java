package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employer {

	@Id
	private long id;

	/*
	 * Default name for join column will be
	 * 
	 * DEPARTMENT_ID
	 * 
	 * because:
	 * 1. field in Employer class is called department
	 * 2. field that contains identifier in Department dlass is called id
	 * 
	 * Join collumn will override that default name
	 */
	@ManyToOne
	@JoinColumn(name="DEP_IDENTIFIER")
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
