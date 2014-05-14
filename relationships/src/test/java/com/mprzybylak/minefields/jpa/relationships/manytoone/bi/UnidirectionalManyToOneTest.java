package com.mprzybylak.minefields.jpa.relationships.manytoone.bi;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class UnidirectionalManyToOneTest {

	@Test
	public void shouldAllowToHaveZeroTargets() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Employee unemployed = new Employee(1L);
		
		// when
		em.getTransaction().begin();
		em.persist(unemployed);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
		Employee employerFromDb = query.getSingleResult();
		
		assertThat(employerFromDb.getId()).isEqualTo(unemployed.getId());
		assertThat(employerFromDb.getDepartment()).isNull();
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowToHaveZeroSources() {
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Department department = new Department(1L);
		
		// when
		em.getTransaction().begin();
		em.persist(department);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Department> query = em.createQuery("SELECT e FROM Department e", Department.class);
		Department departmentFromDb = query.getSingleResult();
		
		assertThat(departmentFromDb.getId()).isEqualTo(department.getId());
		assertThat(departmentFromDb.getEmployees()).isEmpty();
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowOneSourceToHaveOneTarget() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Employee employee = new Employee(1L);
		Department department = new Department(2L);
		employee.setDepartment(department);
		department.addEmployee(employee);
		
		// when
		em.getTransaction().begin();
		em.persist(employee);
		em.persist(department);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Employee> employeeQuery = em.createQuery("SELECT e FROM Employee e", Employee.class);
		Employee employerFromDb = employeeQuery.getSingleResult();
		
		TypedQuery<Department> departmentQuery = em.createQuery("SELECT e FROM Department e", Department.class);
		Department departmentFromDb = departmentQuery.getSingleResult();

		assertThat(employerFromDb.getId()).isEqualTo(employee.getId());
		assertThat(employerFromDb.getDepartment()).isNotNull();
		assertThat(employerFromDb.getDepartment().getId()).isEqualTo(department.getId());

		assertThat(departmentFromDb.getId()).isEqualTo(department.getId());
		assertThat(departmentFromDb.getEmployees()).hasSize(1);
		assertThat(departmentFromDb.getEmployees().iterator().next()).isNotNull();
		assertThat(departmentFromDb.getEmployees().iterator().next().getId()).isNotNull();
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowManySourcesToHaveOneTarget() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Employee firstEmployer = new Employee(1L);
		Employee secondEmployer = new Employee(2L);
		Employee thirdEmployer = new Employee(3L);
		
		Department department = new Department(10L);
		
		firstEmployer.setDepartment(department);
		secondEmployer.setDepartment(department);
		thirdEmployer.setDepartment(department);
		
		department.addEmployee(firstEmployer);
		department.addEmployee(secondEmployer);
		department.addEmployee(thirdEmployer);
		
		// when
		em.getTransaction().begin();
		em.persist(firstEmployer);
		em.persist(secondEmployer);
		em.persist(thirdEmployer);
		em.persist(department);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
		List<Employee> employers = query.getResultList();
		Iterator<Employee> it = employers.iterator();
		
		Employee firstEmployerFromDb = it.next();
		Employee secondEmployerFromDb = it.next();
		Employee thirdEmployerFromDb = it.next();
		
		assertThat(firstEmployerFromDb.getId()).isEqualTo(firstEmployer.getId());
		assertThat(firstEmployerFromDb.getDepartment()).isNotNull();
		assertThat(firstEmployerFromDb.getDepartment().getId()).isEqualTo(department.getId());
		
		assertThat(secondEmployerFromDb.getId()).isEqualTo(secondEmployer.getId());
		assertThat(secondEmployerFromDb.getDepartment()).isNotNull();
		assertThat(secondEmployerFromDb.getDepartment().getId()).isEqualTo(department.getId());

		assertThat(thirdEmployerFromDb.getId()).isEqualTo(thirdEmployer.getId());
		assertThat(thirdEmployerFromDb.getDepartment()).isNotNull();
		assertThat(thirdEmployerFromDb.getDepartment().getId()).isEqualTo(department.getId());
				
		TypedQuery<Department> departmentQuery = em.createQuery("SELECT e FROM Department e", Department.class);
		Department departmentFromDb = departmentQuery.getSingleResult();
		
		assertThat(departmentFromDb.getId()).isEqualTo(department.getId());
		assertThat(departmentFromDb.getEmployees()).hasSize(3);
		
		Iterator<Employee> empIt = departmentFromDb.getEmployees().iterator();
		Employee firstEmployeeFromDbDepartment = empIt.next();
		Employee secondEmployeeFromDbDepartment = empIt.next();
		Employee thirdEmployeeFromDbDepartment = empIt.next();
		
		assertThat(firstEmployeeFromDbDepartment.getId()).isEqualTo(firstEmployer.getId());
		assertThat(secondEmployeeFromDbDepartment.getId()).isEqualTo(secondEmployer.getId());
		assertThat(thirdEmployeeFromDbDepartment.getId()).isEqualTo(thirdEmployer.getId());
		
		em.close();
		emf.close();
	}
	
	
	
	
}
