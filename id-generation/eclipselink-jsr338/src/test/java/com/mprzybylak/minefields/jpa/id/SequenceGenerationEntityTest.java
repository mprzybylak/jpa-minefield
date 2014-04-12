package com.mprzybylak.minefields.jpa.id;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mprzybylak.minefields.jpa.id.common.QueryGenerator;

public class SequenceGenerationEntityTest {
	
	private static final String SELECT_QUERY = QueryGenerator.select(SequenceGenerationEntity.class);
	private static final String TEXT = "Sample Text";
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	@BeforeClass
	public static void setUpOnce() {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}

	@AfterClass
	public static void tearDownOnce() {
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldGenerateEntityId() {

		// given
		SequenceGenerationEntity entity = new SequenceGenerationEntity();
		entity.setText(TEXT);
		
		// when
		long oldId = entity.getId();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();

		TypedQuery<SequenceGenerationEntity> query = em.createQuery(SELECT_QUERY, SequenceGenerationEntity.class);
		SequenceGenerationEntity queryResult = query.getSingleResult(); 
		
		// then
		assertThat(oldId).isEqualTo(0); 
		assertThat(queryResult.getId()).isNotEqualTo(0);
		assertThat(queryResult.getText()).isEqualTo(entity.getText());
	}
	
	
	@Test
	public void shouldGenerateManyEntityIds() {

		// given
		Collection<SequenceGenerationEntity> entities = new ArrayList<SequenceGenerationEntity>(100);
		for(int i = 0; i < 150; ++i) {
			SequenceGenerationEntity entity = new SequenceGenerationEntity();
			entity.setText(TEXT);
			entities.add(entity);
		}
		
		// when
		em.getTransaction().begin();
		for(SequenceGenerationEntity entityToPersist : entities) {
			em.persist(entityToPersist);
		}
		em.getTransaction().commit();
		
		// then
		for(SequenceGenerationEntity persistedEntity : entities) {
			assertThat(persistedEntity.getId()).isNotEqualTo(0);
			assertThat(persistedEntity.getText()).isEqualTo(TEXT);
		}
	}
	
	
}
