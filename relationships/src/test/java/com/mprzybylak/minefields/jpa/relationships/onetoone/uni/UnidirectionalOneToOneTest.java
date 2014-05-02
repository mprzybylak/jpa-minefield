package com.mprzybylak.minefields.jpa.relationships.onetoone.uni;

import static org.fest.assertions.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class UnidirectionalOneToOneTest {

	private static final long BALL_ID = 10L;
	private static final long FOOTBALL_PLAYER_ID = 100L;

	@Test
	public void shouldCreateRelation() {

		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
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
	

}
