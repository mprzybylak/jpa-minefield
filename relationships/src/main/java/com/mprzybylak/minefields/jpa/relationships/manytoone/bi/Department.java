package com.mprzybylak.minefields.jpa.relationships.manytoone.bi;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Department is inverse side of relation
 */
@Entity
public class Department {
	
	@Id
	private long id;
	
	
	@OneToMany(
			mappedBy="department",
			fetch=FetchType.LAZY // lazy fetch by default but it can be changed by "fetch" parameter
			)
	private Collection<Employee> employees = new ArrayList<>();

	public Department() {
	}

	public Department(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
	
	public Collection<Employee> getEmployees() {
		return employees;
	}

}
