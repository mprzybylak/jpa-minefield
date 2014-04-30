package com.mprzybylak.minefields.jpa.id.base;

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

public abstract class IdGeneratorBaseTest<A, T extends SampleEntity<A>> {
	
	private static final String TEXT = "Sample Text";
	
	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	
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
	
	protected abstract T createInstance();
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldGenerateEntityId() {

		// given
		T entity = createInstance();
		entity.setText(TEXT);
		
		// when
		A oldId = entity.getId();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();

		TypedQuery<T> query = em.createQuery(QueryGenerator.select(entity.getClass()), (Class<T>)entity.getClass());
		T queryResult = query.getSingleResult(); 
		
		// then
		// sad workaroud
		if(oldId instanceof String) {
			assertThat((String)oldId).isEqualTo("0");
			assertThat((String)queryResult.getId()).isNotEqualTo("0");
			assertThat(queryResult.getText()).isEqualTo(entity.getText());
		}
		if(oldId instanceof Long) {
			assertThat((Long)oldId).isEqualTo(0L);
			assertThat((Long)queryResult.getId()).isNotEqualTo(0L);
			assertThat(queryResult.getText()).isEqualTo(entity.getText());
		}
	}
	
	@Test
	public void shouldGenerateManyEntityIds() {

		// given
		Collection<T> entities = new ArrayList<T>(100);
		for(int i = 0; i < 100; ++i) {
			T entity = createInstance();
			entity.setText(TEXT);
			entities.add(entity);
		}
		
		// when
		for(T entityToPersist : entities) {
			em.getTransaction().begin();
			em.persist(entityToPersist);
			em.getTransaction().commit();
		}
		
		// then
		for(T persistedEntity : entities) {
			assertThat(persistedEntity.getId()).isNotEqualTo(0);
			assertThat(persistedEntity.getText()).isEqualTo(TEXT);
		}
	}

	
}
