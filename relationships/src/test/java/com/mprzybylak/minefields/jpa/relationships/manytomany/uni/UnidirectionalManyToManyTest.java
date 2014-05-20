package com.mprzybylak.minefields.jpa.relationships.manytomany.uni;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class UnidirectionalManyToManyTest {

	@Test
	public void shouldAllowNewsReaderToHaveNoChannels() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		NewsReader firstReader = new NewsReader(1L);
		NewsReader secondReader = new NewsReader(2L);
		
		RssChannel firstRssChannel = new RssChannel(3L);
		RssChannel secondRssChannel = new RssChannel(4L);
		RssChannel thirdRssChannel = new RssChannel(5L);
		
		// when
		em.getTransaction().begin();
		em.persist(firstReader);
		em.persist(secondReader);
		em.persist(firstRssChannel);
		em.persist(secondRssChannel);
		em.persist(thirdRssChannel);
		em.getTransaction().commit();
		
		// then
		TypedQuery<NewsReader> newsReaderQuery = em.createQuery("SELECT e FROM NewsReader e", NewsReader.class);
		List<NewsReader> newsReadersFromDb = newsReaderQuery.getResultList();
		assertThat(newsReadersFromDb).hasSize(2);
		
		Iterator<NewsReader> newsReadersFromDbIt = newsReadersFromDb.iterator();
		NewsReader firstNewsReaderFromDb = newsReadersFromDbIt.next();
		NewsReader secondNewsReaderFromDb = newsReadersFromDbIt.next();
		
		assertThat(firstNewsReaderFromDb.getId()).isEqualTo(firstReader.getId());
		assertThat(secondNewsReaderFromDb.getId()).isEqualTo(secondReader.getId());
		
		TypedQuery<RssChannel> rssChannelQuery = em.createQuery("SELECT e FROM RssChannel e", RssChannel.class);
		List<RssChannel> rssChannelsFromDb = rssChannelQuery.getResultList();
		assertThat(rssChannelsFromDb).hasSize(3);
		
		Iterator<RssChannel> rssChannelsFromDbIt = rssChannelsFromDb.iterator();
		RssChannel firstRssChannelFromDb = rssChannelsFromDbIt.next();
		RssChannel secondRssChannelFromDb = rssChannelsFromDbIt.next();
		RssChannel thirdRssChannelFromDb = rssChannelsFromDbIt.next();
		
		assertThat(firstRssChannelFromDb.getId()).isEqualTo(firstRssChannel.getId());
		assertThat(secondRssChannelFromDb.getId()).isEqualTo(secondRssChannel.getId());
		assertThat(thirdRssChannelFromDb.getId()).isEqualTo(thirdRssChannel.getId());
		
		em.close();
		emf.close();
	}
	
	@Test
	public void shouldAllowNewsReadersToHaveMultipleChannels() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		NewsReader firstReader = new NewsReader(1L);
		NewsReader secondReader = new NewsReader(2L);
		
		RssChannel firstRssChannel = new RssChannel(3L);
		RssChannel secondRssChannel = new RssChannel(4L);
		RssChannel thirdRssChannel = new RssChannel(5L);
		
		firstReader.addRssChannel(firstRssChannel);
		firstReader.addRssChannel(secondRssChannel);
		
		secondReader.addRssChannel(secondRssChannel);
		secondReader.addRssChannel(thirdRssChannel);
		
		// when
		em.getTransaction().begin();
		em.persist(firstReader);
		em.persist(secondReader);
		em.persist(firstRssChannel);
		em.persist(secondRssChannel);
		em.persist(thirdRssChannel);
		em.getTransaction().commit();
		
		// then
		TypedQuery<NewsReader> newsReaderQuery = em.createQuery("SELECT e FROM NewsReader e", NewsReader.class);
		List<NewsReader> newsReadersFromDb = newsReaderQuery.getResultList();
		assertThat(newsReadersFromDb).hasSize(2);
		
		Iterator<NewsReader> newsReadersFromDbIt = newsReadersFromDb.iterator();
		NewsReader firstNewsReaderFromDb = newsReadersFromDbIt.next();
		NewsReader secondNewsReaderFromDb = newsReadersFromDbIt.next();
		
		assertThat(firstNewsReaderFromDb.getId()).isEqualTo(firstReader.getId());
		assertThat(secondNewsReaderFromDb.getId()).isEqualTo(secondReader.getId());
		
		Collection<RssChannel> firstReaderFromDbRssChannels = firstNewsReaderFromDb.getRssChannels();
		assertThat(firstReaderFromDbRssChannels).hasSize(2);
		
		Iterator<RssChannel> firstReaderFromDbRssChannelsIt = firstReaderFromDbRssChannels.iterator();
		RssChannel firstReaderFromDbFirstChannel = firstReaderFromDbRssChannelsIt.next();
		RssChannel firstReaderFromDbSecondChannel = firstReaderFromDbRssChannelsIt.next();
		
		assertThat(firstReaderFromDbFirstChannel.getId()).isEqualTo(firstRssChannel.getId());
		assertThat(firstReaderFromDbSecondChannel.getId()).isEqualTo(secondRssChannel.getId());
		
		Collection<RssChannel> secondReaderFromDbRssChannels = secondNewsReaderFromDb.getRssChannels();
		assertThat(secondReaderFromDbRssChannels).hasSize(2);

		Iterator<RssChannel> secondReaderFromDbRssChannelsIt = secondReaderFromDbRssChannels.iterator();
		RssChannel secondReaderFromDbFirstChannel = secondReaderFromDbRssChannelsIt.next();
		RssChannel secondReaderFromDbSecondChannel = secondReaderFromDbRssChannelsIt.next();
		
		assertThat(secondReaderFromDbFirstChannel.getId()).isEqualTo(secondRssChannel.getId());
		assertThat(secondReaderFromDbSecondChannel.getId()).isEqualTo(thirdRssChannel.getId());
		
		TypedQuery<RssChannel> rssChannelQuery = em.createQuery("SELECT e FROM RssChannel e", RssChannel.class);
		List<RssChannel> rssChannelsFromDb = rssChannelQuery.getResultList();
		assertThat(rssChannelsFromDb).hasSize(3);
		
		Iterator<RssChannel> rssChannelsFromDbIt = rssChannelsFromDb.iterator();
		RssChannel firstRssChannelFromDb = rssChannelsFromDbIt.next();
		RssChannel secondRssChannelFromDb = rssChannelsFromDbIt.next();
		RssChannel thirdRssChannelFromDb = rssChannelsFromDbIt.next();
		
		assertThat(firstRssChannelFromDb.getId()).isEqualTo(firstRssChannel.getId());
		assertThat(secondRssChannelFromDb.getId()).isEqualTo(secondRssChannel.getId());
		assertThat(thirdRssChannelFromDb.getId()).isEqualTo(thirdRssChannel.getId());
		
		em.close();
		emf.close();
	}
	
}
