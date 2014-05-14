package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
