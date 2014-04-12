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

public class TableGenerationEntityTest {
	
	private static final String SELECT_QUERY = "SELECT e FROM TableGenerationEntity e";
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
		TableGenerationEntity entity = new TableGenerationEntity();
		entity.setText(TEXT);
		
		// when
		long oldId = entity.getId();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();

		TypedQuery<TableGenerationEntity> query = em.createQuery(SELECT_QUERY, TableGenerationEntity.class);
		TableGenerationEntity queryResult = query.getSingleResult(); 
		
		// then
		assertThat(oldId).isEqualTo(0); 
		assertThat(queryResult.getId()).isNotEqualTo(0);
		assertThat(queryResult.getText()).isEqualTo(entity.getText());
	}
	
	
	@Test
	public void shouldGenerateManyEntityIds() {

		// given
		Collection<TableGenerationEntity> entities = new ArrayList<TableGenerationEntity>(100);
		for(int i = 0; i < 100; ++i) {
			TableGenerationEntity entity = new TableGenerationEntity();
			entity.setText(TEXT);
			entities.add(entity);
		}
		
		// when
		em.getTransaction().begin();
		for(TableGenerationEntity entityToPersist : entities) {
			em.persist(entityToPersist);
		}
		em.getTransaction().commit();
		
		// then
		for(TableGenerationEntity persistedEntity : entities) {
			assertThat(persistedEntity.getId()).isNotEqualTo(0);
			assertThat(persistedEntity.getText()).isEqualTo(TEXT);
		}
	}
	
	
}
