package com.mprzybylak.minefields.jpa.relationships.manytoone.bi;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Department is inverse side of relation
 */
@Entity
public class Department {
	
	@Id
	private long id;
	
	@OneToMany(mappedBy="department")
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
