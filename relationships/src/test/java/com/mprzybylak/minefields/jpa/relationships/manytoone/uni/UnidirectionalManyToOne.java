package com.mprzybylak.minefields.jpa.relationships.manytoone.uni;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class UnidirectionalManyToOne {
	
	@Test
	public void shouldAllowSourceToNotHaveTarget() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Person personWithourComputer = new Person(1L);
		Computer computer = new Computer(1L);
		
		// when
		em.getTransaction().begin();
		em.persist(personWithourComputer);
		em.persist(computer);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Person> personQuery = em.createQuery("SELECT e FROM Person e", Person.class);
		Person personFromDb = personQuery.getSingleResult();
		
		assertThat(personFromDb.getId()).isEqualTo(personWithourComputer.getId());
		assertThat(personFromDb.getComputer()).isNull();
		
		TypedQuery<Computer> computerQuery = em.createQuery("SELECT e FROM Computer e", Computer.class);
		Computer computerFromDb = computerQuery.getSingleResult();
		
		assertThat(computerFromDb.getId()).isEqualTo(computer.getId());
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowTargetToBePointedByMultipleSources() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Person firstPerson = new Person(1L);
		Person secondPerson = new Person(2L);
		Person thirdPerson = new Person(3L);
		Computer computer = new Computer(4L);
		
		firstPerson.setComputer(computer);
		secondPerson.setComputer(computer);
		thirdPerson.setComputer(computer);
		
		// when
		em.getTransaction().begin();
		em.persist(firstPerson);
		em.persist(secondPerson);
		em.persist(thirdPerson);
		em.persist(computer);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Person> personQuery = em.createQuery("SELECT e FROM Person e", Person.class);
		List<Person> personsFromDb = personQuery.getResultList();
		assertThat(personsFromDb).hasSize(3);
		
		Iterator<Person> personsFromDbIt = personsFromDb.iterator();
		Person firstPersonFromDb = personsFromDbIt.next();
		Person secondPersonFromDb = personsFromDbIt.next();
		Person thirdPersonFromDb = personsFromDbIt.next();
		
		assertThat(firstPersonFromDb.getId()).isEqualTo(firstPerson.getId());
		assertThat(secondPersonFromDb.getId()).isEqualTo(secondPerson.getId());
		assertThat(thirdPersonFromDb.getId()).isEqualTo(thirdPerson.getId());
		
		assertThat(firstPersonFromDb.getComputer()).isNotNull();
		assertThat(secondPersonFromDb.getComputer()).isNotNull();
		assertThat(thirdPersonFromDb.getComputer()).isNotNull();
		
		assertThat(firstPersonFromDb.getComputer().getId()).isEqualTo(computer.getId());
		assertThat(secondPersonFromDb.getComputer().getId()).isEqualTo(computer.getId());
		assertThat(thirdPersonFromDb.getComputer().getId()).isEqualTo(computer.getId());

		TypedQuery<Computer> computerQuery = em.createQuery("SELECT e FROM Computer e", Computer.class);
		Computer computerFromDb = computerQuery.getSingleResult();
		
		assertThat(computerFromDb.getId()).isEqualTo(computer.getId());
		
	}

}
