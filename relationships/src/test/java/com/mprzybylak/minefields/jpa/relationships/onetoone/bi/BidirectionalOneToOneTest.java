package com.mprzybylak.minefields.jpa.relationships.onetoone.bi;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.junit.Ignore;
import org.junit.Test;

public class BidirectionalOneToOneTest {

	@Test
	public void shouldSourceAndTargetNotHaveRelation() {

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		Owner manWithoutDog = new Owner();
		Dog homelessDog = new Dog();

		// when
		em.getTransaction().begin();
		em.persist(manWithoutDog);
		em.persist(homelessDog);
		em.getTransaction().commit();

		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e",
				Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e",
				Dog.class);

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
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		Owner manWithoutDog = new Owner();
		Dog strayDog = new Dog();
		strayDog.setOwner(manWithoutDog);

		// when
		em.getTransaction().begin();
		em.persist(manWithoutDog);
		em.persist(strayDog);
		em.getTransaction().commit();

		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e",
				Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e",
				Dog.class);

		Owner ownerFromDb = ownerQuery.getSingleResult();
		Dog dogFromDb = dogQuery.getSingleResult();

		assertThat(ownerFromDb).isNotNull();
		assertThat(dogFromDb).isNotNull();

		assertThat(ownerFromDb.getId()).isEqualTo(manWithoutDog.getId());
		assertThat(dogFromDb.getId()).isEqualTo(strayDog.getId());

		assertThat(ownerFromDb.getDog()).isNull();
		assertThat(dogFromDb.getOwner()).isNotNull();
		assertThat(dogFromDb.getOwner().getId()).isEqualTo(
				manWithoutDog.getId());

		em.close();
		emf.close();
	}

	@Test
	public void shouldTargetNotHaveRelation() {

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		Owner creepyDogSalker = new Owner();
		Dog homelessDog = new Dog();
		creepyDogSalker.setDog(homelessDog);

		// when
		em.getTransaction().begin();
		em.persist(creepyDogSalker);
		em.persist(homelessDog);
		em.getTransaction().commit();

		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e",
				Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e",
				Dog.class);

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
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		Owner owner = new Owner();
		Dog dog = new Dog();
		owner.setDog(dog);
		dog.setOwner(owner);

		// when
		em.getTransaction().begin();
		em.persist(owner);
		em.persist(dog);
		em.getTransaction().commit();

		// then
		TypedQuery<Owner> ownerQuery = em.createQuery("SELECT e FROM Owner e",
				Owner.class);
		TypedQuery<Dog> dogQuery = em.createQuery("SELECT e FROM Dog e",
				Dog.class);

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
	public void shouldTryToVioliateUniqueConstraint_multipleSources_HIBERNATE() {

		// Table A contains foreign key to table B
		// hibernate does not follow JSR in this case

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		try {
			Owner owner = new Owner();
			Owner sonOfOwner = new Owner();
			Dog dog = new Dog();

			owner.setDog(dog);
			dog.setOwner(owner);
			sonOfOwner.setDog(dog);

			// when
			em.getTransaction().begin();
			em.persist(owner);
			em.persist(sonOfOwner);
			em.persist(dog);
			em.getTransaction().commit();
			fail();
		} catch (RollbackException e) {
			// then
		}

		em.close();
		emf.close();
	}

	@Test
	@Ignore
	public void shouldTryToVioliateUniqueConstraint_multipleSources_ECLIPSELINK() {

		// Table A contains foreign key to table B
		// eclipse link does not JSR in this case
		
		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("eclipselink-pu");
		EntityManager em = emf.createEntityManager();

		try {
			Owner owner = new Owner();
			Owner sonOfOwner = new Owner();
			Dog dog = new Dog();

			owner.setDog(dog);
			dog.setOwner(owner);
			sonOfOwner.setDog(dog);

			// when
			em.getTransaction().begin();
			em.persist(owner);
			em.persist(sonOfOwner);
			em.persist(dog);
			em.getTransaction().commit();
			fail();
		} catch (RollbackException e) {
			e.printStackTrace();
		}

		em.close();
		emf.close();
	}

	@Test
	@Ignore
	public void shouldTryToVioliateUniqueConstraint_multipleTargets_HIBERNATE() {

		// Table A contains foreign key to table B
		// hibernate does not follow JSR in this case

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		try {
			Owner owner = new Owner();
			Dog dog = new Dog();
			Dog strayDog = new Dog();

			owner.setDog(dog);
			dog.setOwner(owner);
			strayDog.setOwner(owner);

			// when
			em.getTransaction().begin();
			em.persist(owner);
			em.persist(dog);
			em.persist(strayDog);
			em.getTransaction().commit();
			fail();
		} catch (RollbackException e) {
			// then
		}

		em.close();
		emf.close();
	}

	@Test
	@Ignore
	public void shouldTryToVioliateUniqueConstraint_multipleTargets_ECLIPSELINK() {

		// Table A contains foreign key to table B
		// eclipse link does not follow JSR in this case
		
		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("eclipselink-pu");
		EntityManager em = emf.createEntityManager();

		try {
			Owner owner = new Owner();
			Dog dog = new Dog();
			Dog strayDog = new Dog();

			owner.setDog(dog);
			dog.setOwner(owner);
			strayDog.setOwner(owner);

			// when
			em.getTransaction().begin();
			em.persist(owner);
			em.persist(dog);
			em.persist(strayDog);
			em.getTransaction().commit();
			fail();
		} catch (RollbackException e) {
			// then
		}

		em.close();
		emf.close();
	}

	/**
	 * Not sure if this should works "logically" - should JPA provides that if
	 * source points to target than target can points only to this source?
	 */
	@Test
	@Ignore
	public void shouldSourcePointToTargetButTargetPointToOtherSource() {

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		try {
			Owner owner = new Owner();
			Owner manWithoutDog = new Owner();
			Dog ungratefulDog = new Dog();

			owner.setDog(ungratefulDog);
			ungratefulDog.setOwner(manWithoutDog);

			// when
			em.getTransaction().begin();
			em.persist(owner);
			em.persist(manWithoutDog);
			em.persist(ungratefulDog);
			em.getTransaction().commit();
			fail();
		} catch (RollbackException e) {

		}
		// then
		em.close();
		emf.close();
	}

}
