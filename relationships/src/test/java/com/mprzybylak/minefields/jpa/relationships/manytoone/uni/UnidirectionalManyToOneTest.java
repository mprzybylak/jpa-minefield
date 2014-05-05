package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import static org.fest.assertions.Assertions.assertThat;

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
	
	
}
