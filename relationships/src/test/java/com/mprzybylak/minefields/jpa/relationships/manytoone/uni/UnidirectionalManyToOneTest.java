package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

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
		
		Employer unemployed = new Employer(1L);
		
		// when
		em.getTransaction().begin();
		em.persist(unemployed);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Employer> query = em.createQuery("SELECT e FROM Employer e", Employer.class);
		Employer employerFromDb = query.getSingleResult();
		
		assertThat(employerFromDb.getId()).isEqualTo(unemployed.getId());
		assertThat(employerFromDb.getDepartment()).isNull();
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowOneSourceToHaveOneTarget() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Employer employer = new Employer(1L);
		Department department = new Department(2L);
		employer.setDepartment(department);
		
		// when
		em.getTransaction().begin();
		em.persist(employer);
		em.persist(department);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Employer> query = em.createQuery("SELECT e FROM Employer e", Employer.class);
		Employer employerFromDb = query.getSingleResult();
		
		assertThat(employerFromDb.getId()).isEqualTo(employer.getId());
		assertThat(employerFromDb.getDepartment()).isNotNull();
		assertThat(employerFromDb.getDepartment().getId()).isEqualTo(department.getId());
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowManySourcesToHaveOneTarget() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Employer firstEmployer = new Employer(1L);
		Employer secondEmployer = new Employer(2L);
		Employer thirdEmployer = new Employer(3L);
		
		Department department = new Department(10L);
		
		firstEmployer.setDepartment(department);
		secondEmployer.setDepartment(department);
		thirdEmployer.setDepartment(department);
		
		// when
		em.getTransaction().begin();
		em.persist(firstEmployer);
		em.persist(secondEmployer);
		em.persist(thirdEmployer);
		em.persist(department);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Employer> query = em.createQuery("SELECT e FROM Employer e", Employer.class);
		List<Employer> employers = query.getResultList();
		Iterator<Employer> it = employers.iterator();
		
		Employer firstEmployerFromDb = it.next();
		Employer secondEmployerFromDb = it.next();
		Employer thirdEmployerFromDb = it.next();
		
		assertThat(firstEmployerFromDb.getId()).isEqualTo(firstEmployer.getId());
		assertThat(firstEmployerFromDb.getDepartment()).isNotNull();
		assertThat(firstEmployerFromDb.getDepartment().getId()).isEqualTo(department.getId());
		
		assertThat(secondEmployerFromDb.getId()).isEqualTo(secondEmployer.getId());
		assertThat(secondEmployerFromDb.getDepartment()).isNotNull();
		assertThat(secondEmployerFromDb.getDepartment().getId()).isEqualTo(department.getId());

		assertThat(thirdEmployerFromDb.getId()).isEqualTo(thirdEmployer.getId());
		assertThat(thirdEmployerFromDb.getDepartment()).isNotNull();
		assertThat(thirdEmployerFromDb.getDepartment().getId()).isEqualTo(department.getId());
		
		em.close();
		emf.close();
	}
	
	
	
	
}
