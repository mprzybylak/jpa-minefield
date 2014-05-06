package com.mprzybylak.minefields.jpa.relationships.onetoone.bi;

import static org.fest.assertions.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Ignore;
import org.junit.Test;

public class BidirectionalOneToOneTest {
	
	@Test
	public void shouldSourceAndTargetNotHaveRelation() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Owner manWithoutDog = new Owner(1L);
		Dog homelessDog = new Dog(2L);
		
		// when
		em.getTransaction().begin();
		em.persist(manWithoutDog);
		em.persist(homelessDog);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e", Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e", Dog.class);
		
		Owner ownerFromDb = ownerQuery.getSingleResult();
		Dog dogFromDb = dogQuery.getSingleResult();
		
		assertThat(ownerFromDb).isNotNull();
		assertThat(dogFromDb).isNotNull();

		assertThat(ownerFromDb.getId()).isEqualTo(manWithoutDog.getId());
		assertThat(dogFromDb.getId()).isEqualTo(homelessDog.getId());

		assertThat(ownerFromDb.getDog()).isNull();
		assertThat(dogFromDb.getOwner()).isNull();
		
		em.close();
		emf.close();
		
	}
	
	@Test
	public void shouldSourceNotHaveRelation() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Owner manWithoutDog = new Owner(1L);
		Dog strayDog = new Dog(2L);
		strayDog.setOwner(manWithoutDog);
		
		// when
		em.getTransaction().begin();
		em.persist(manWithoutDog);
		em.persist(strayDog);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e", Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e", Dog.class);
		
		Owner ownerFromDb = ownerQuery.getSingleResult();
		Dog dogFromDb = dogQuery.getSingleResult();
		
		assertThat(ownerFromDb).isNotNull();
		assertThat(dogFromDb).isNotNull();

		assertThat(ownerFromDb.getId()).isEqualTo(manWithoutDog.getId());
		assertThat(dogFromDb.getId()).isEqualTo(strayDog.getId());
		
		assertThat(ownerFromDb.getDog()).isNull();
		assertThat(dogFromDb.getOwner()).isNotNull();
		assertThat(dogFromDb.getOwner().getId()).isEqualTo(manWithoutDog.getId());
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldTargetNotHaveRelation() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Owner creepyDogSalker = new Owner(1L);
		Dog homelessDog = new Dog(2L);
		creepyDogSalker.setDog(homelessDog);
		
		// when
		em.getTransaction().begin();
		em.persist(creepyDogSalker);
		em.persist(homelessDog);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e", Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e", Dog.class);
		
		Owner ownerFromDb = ownerQuery.getSingleResult();
		Dog dogFromDb = dogQuery.getSingleResult();
		
		assertThat(ownerFromDb).isNotNull();
		assertThat(dogFromDb).isNotNull();

		assertThat(ownerFromDb.getId()).isEqualTo(creepyDogSalker.getId());
		assertThat(dogFromDb.getId()).isEqualTo(homelessDog.getId());
		
		assertThat(ownerFromDb.getDog()).isNotNull();
		assertThat(ownerFromDb.getDog().getId()).isEqualTo(homelessDog.getId());
		
		assertThat(dogFromDb.getOwner()).isNull();
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldSourceAndTargetBeInRelation() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Owner owner = new Owner(1L);
		Dog dog = new Dog(2L);
		owner.setDog(dog);
		dog.setOwner(owner);
		
		// when
		em.getTransaction().begin();
		em.persist(owner);
		em.persist(dog);
		em.getTransaction().commit();

		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e", Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e", Dog.class);
		
		Owner ownerFromDb = ownerQuery.getSingleResult();
		Dog dogFromDb = dogQuery.getSingleResult();
		
		assertThat(ownerFromDb).isNotNull();
		assertThat(dogFromDb).isNotNull();

		assertThat(ownerFromDb.getId()).isEqualTo(owner.getId());
		assertThat(dogFromDb.getId()).isEqualTo(dog.getId());
		
		assertThat(ownerFromDb.getDog()).isNotNull();
		assertThat(ownerFromDb.getDog().getId()).isEqualTo(dog.getId());
		assertThat(dogFromDb.getOwner()).isNotNull();
		assertThat(dogFromDb.getOwner().getId()).isEqualTo(owner.getId());
		
		em.close();
		emf.close();
	}
	
	@Test
	@Ignore
	public void shouldTryToVioliateUniqueConstraint_HIBERNATE() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		
		em.getTransaction().commit();

		// then
		em.close();
		emf.close();
	}
	
	@Test
	@Ignore
	public void shouldSourcePointToTargetButTargetNotPointBack() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		
		em.getTransaction().commit();

		// then
		em.close();
		emf.close();
	}
	
	@Test
	@Ignore
	public void shouldTargetPointToSourceButSourceNotPointBack() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		
		em.getTransaction().commit();

		// then
		em.close();
		emf.close();
	}
	
	@Test
	@Ignore
	public void shouldSourcePointToTargetButTargetPointToOtherSource() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		
		em.getTransaction().commit();

		// then
		em.close();
		emf.close();
	}

}
