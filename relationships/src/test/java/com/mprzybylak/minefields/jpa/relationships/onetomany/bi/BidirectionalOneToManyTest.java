package com.mprzybylak.minefields.jpa.relationships.onetomany.bi;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class BidirectionalOneToManyTest {
	
	@Test
	public void shouldHaveRelation() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Team team = new Team();
		Member firstMember = new Member();
		Member secondMember = new Member();
		
		team.addMember(firstMember);
		team.addMember(secondMember);
		firstMember.setTeam(team);
		secondMember.setTeam(team);
		
		// when
		em.getTransaction().begin();
		em.persist(team);
		em.persist(firstMember);
		em.persist(secondMember);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Team> teamQuery = em.createQuery("SELECT e FROM Team e", Team.class);
		TypedQuery<Member> memberQuery = em.createQuery("SELECT e FROM Member e", Member.class);

		Team teamFromDb = teamQuery.getSingleResult();
		
		List<Member> membersFromDb = memberQuery.getResultList();
		Iterator<Member> it = membersFromDb.iterator();
		Member firstMemberFromDb = it.next();
		Member secondMemberFromDb = it.next();
		
		assertThat(teamFromDb.getId()).isEqualTo(team.getId());
		assertThat(teamFromDb.getMembers()).isNotEmpty();
		assertThat(teamFromDb.getMembers()).isEqualTo(team.getMembers());
		
		assertThat(firstMemberFromDb.getId()).isEqualTo(firstMember.getId());
		assertThat(firstMemberFromDb.getTeam()).isNotNull();
		assertThat(firstMemberFromDb.getTeam().getId()).isEqualTo(team.getId());
		
		assertThat(secondMemberFromDb.getId()).isEqualTo(secondMember.getId());
		assertThat(secondMemberFromDb.getTeam()).isNotNull();
		assertThat(secondMemberFromDb.getTeam().getId()).isEqualTo(team.getId());
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowToSourceWithoutTarget() {
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		em.getTransaction().commit();
		
		// then
		TypedQuery<Team> teamQuery = em.createQuery("SELECT e FROM Team e", Team.class);
		TypedQuery<Member> memberQuery = em.createQuery("SELECT e FROM Member e", Member.class);

		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowToTargetWithoutSource() {
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		em.getTransaction().commit();
		
		// then
		TypedQuery<Team> teamQuery = em.createQuery("SELECT e FROM Team e", Team.class);
		TypedQuery<Member> memberQuery = em.createQuery("SELECT e FROM Member e", Member.class);

		em.close();
		emf.close();
	}

	
	// Illegal?
	@Test
	public void shouldTargetToPointOtherSorce(){
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		em.getTransaction().commit();
		
		// then
		TypedQuery<Team> teamQuery = em.createQuery("SELECT e FROM Team e", Team.class);
		TypedQuery<Member> memberQuery = em.createQuery("SELECT e FROM Member e", Member.class);

		em.close();
		emf.close();
	}
	
	// Illegal?
	@Test
	public void shouldAllowSourceToPointOtherTarget() {
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		// when
		em.getTransaction().begin();
		em.getTransaction().commit();
		
		// then
		TypedQuery<Team> teamQuery = em.createQuery("SELECT e FROM Team e", Team.class);
		TypedQuery<Member> memberQuery = em.createQuery("SELECT e FROM Member e", Member.class);

		em.close();
		emf.close();
	}
	
}
