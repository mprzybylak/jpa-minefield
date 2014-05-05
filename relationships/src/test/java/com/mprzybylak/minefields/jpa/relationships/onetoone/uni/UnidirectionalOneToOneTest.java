package com.mprzybylak.minefields.jpa.relationships.onetoone.uni;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.junit.Ignore;
import org.junit.Test;

public class UnidirectionalOneToOneTest {

	private static final long BALL_ID = 10L;
	private static final long FOOTBALL_PLAYER_ID = 100L;

	@Test
	public void sourceSideCouldNotHaveAnyTargetEntityInstanceAssigned() {

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		FootballPlayer footballPlayer = new FootballPlayer(FOOTBALL_PLAYER_ID);

		// when
		em.getTransaction().begin();
		em.persist(footballPlayer);
		em.getTransaction().commit();

		// then
		TypedQuery<FootballPlayer> query = em.createQuery(
				"SELECT e FROM FootballPlayer e", FootballPlayer.class);

		FootballPlayer databasePlayer = query.getSingleResult();
		assertThat(databasePlayer).isNotNull();
		assertThat(databasePlayer.getId()).isEqualTo(FOOTBALL_PLAYER_ID);

		Ball databaseBall = databasePlayer.getBall();
		assertThat(databaseBall).isNull();

		em.close();
		emf.close();
	}
	
	@Test
	public void shouldCreateRelation() {

		// given
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		FootballPlayer footballPlayer = new FootballPlayer(FOOTBALL_PLAYER_ID);
		Ball ball = new Ball(BALL_ID);

		// when
		em.getTransaction().begin();
		footballPlayer.setBall(ball);
		em.persist(ball);
		em.persist(footballPlayer);
		em.getTransaction().commit();

		// then
		TypedQuery<FootballPlayer> query = em.createQuery(
				"SELECT e FROM FootballPlayer e", FootballPlayer.class);

		FootballPlayer databasePlayer = query.getSingleResult();
		assertThat(databasePlayer).isNotNull();
		assertThat(databasePlayer.getId()).isEqualTo(FOOTBALL_PLAYER_ID);

		Ball databaseBall = databasePlayer.getBall();
		assertThat(databaseBall).isNotNull();
		assertThat(databaseBall.getId()).isEqualTo(BALL_ID);

		em.close();
		emf.close();
	}

	/**
	 * <p>JSR-338, section 2.10.3.1 ("Unidirectional OneToOne Relationshps") states: </p>  
	 * <p><i>"(...) The foreign key column has the same type as primary key of table B and there is a <b>unique key constraint on it</b>."</i></p>
	 * <p>Hibernate does not follow this rule in version 4.3.5 - that is why this test is ignored for now</p> 
	 */
	@Test
	@Ignore
	public void shouldNotAllowToAttachTargetEntityToMoreThanOneSource_Hibernate() {

		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		try {

			Ball ball = new Ball(1L);
			FootballPlayer firstPlayer = new FootballPlayer(2L);
			FootballPlayer secondPlayer = new FootballPlayer(3L);

			firstPlayer.setBall(ball);
			secondPlayer.setBall(ball);

			// when
			em.getTransaction().begin();
			em.persist(ball);
			em.persist(firstPlayer);
			em.persist(secondPlayer);
			em.getTransaction().commit();
			fail();
			
		} catch (RollbackException e) {
			// then
		}

		em.close();
		emf.close();
	}
	
	
	public void a() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("eclipselink-pu");
		EntityManager em = emf.createEntityManager();

		Ball ball = new Ball(1L);
		FootballPlayer firstPlayer = new FootballPlayer(2L);
		FootballPlayer secondPlayer = new FootballPlayer(3L);

		firstPlayer.setBall(ball);
		secondPlayer.setBall(ball);

		em.getTransaction().begin();
		em.persist(ball);
		em.persist(firstPlayer);
		em.persist(secondPlayer);
		em.getTransaction().commit(); // Exception?

		em.close();
		emf.close();
	}
	
	/**
	 * <p>JSR-338, section 2.10.3.1 ("Unidirectional OneToOne Relationshps") states: </p>  
	 * <p><i>"(...) The foreign key column has the same type as primary key of table B and there is a <b>unique key constraint on it</b>."</i></p>
	 * <p>Eclipselink does not follow this rule in version 2.5.1 - that is why this test is ignored for now</p> 
	 */
	@Test
	@Ignore
	public void shouldNotAllowToAttachTargetEntityToMoreThanOneSource_Eclipselink() {

		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("eclipselink-pu");
		EntityManager em = emf.createEntityManager();

		try {

			Ball ball = new Ball(1L);
			FootballPlayer firstPlayer = new FootballPlayer(2L);
			FootballPlayer secondPlayer = new FootballPlayer(3L);

			firstPlayer.setBall(ball);
			secondPlayer.setBall(ball);

			// when
			em.getTransaction().begin();
			em.persist(ball);
			em.persist(firstPlayer);
			em.persist(secondPlayer);
			em.getTransaction().commit();
			fail();
			
		} catch (RollbackException e) {
			// then
		}

		em.close();
		emf.close();
	}

}
