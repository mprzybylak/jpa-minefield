package com.mprzybylak.minefields.jpa.relationships.manytomany.bi;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class BidirectionalManyToManyTest {

	
	@Test
	public void shouldAlowToManyToManyRelationship() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();
		
		Movie firstMovie = new Movie(1L);
		Movie secondMovie = new Movie(2L);
		Movie thirdMovie = new Movie(3L);
		
		Actor firstActor = new Actor(4L);
		Actor secondActor = new Actor(5L);
		Actor thirdActor = new Actor(6L);
		
		firstMovie.addActor(firstActor);
		
		secondMovie.addActor(firstActor);
		secondMovie.addActor(secondActor);
		secondMovie.addActor(thirdActor);
		
		thirdMovie.addActor(thirdActor);
		
		firstActor.addMovie(firstMovie);
		firstActor.addMovie(secondMovie);
		
		secondActor.addMovie(secondMovie);
		
		thirdActor.addMovie(secondMovie);
		thirdActor.addMovie(thirdMovie);
		
		// when
		em.getTransaction().begin();
		em.persist(firstActor);
		em.persist(secondActor);
		em.persist(thirdActor);
		em.persist(firstMovie);
		em.persist(secondMovie);
		em.persist(thirdMovie);
		em.getTransaction().commit();
		
		// then
		TypedQuery<Movie> movieQuery = em.createQuery("SELECT e FROM Movie e", Movie.class);
		List<Movie> moviesFromDb = movieQuery.getResultList();
		
		assertThat(moviesFromDb).hasSize(3);
		
		Iterator<Movie> moviesIt = moviesFromDb.iterator();
		
		// first movie
		Movie firstMovieFromDb = moviesIt.next();
		assertThat(firstMovieFromDb.getId()).isEqualTo(firstMovie.getId());
		assertThat(firstMovieFromDb.getActors()).hasSize(1);
		
		Iterator<Actor> firstMovieFromDbIt = firstMovieFromDb.getActors().iterator();
		Actor firstMovieFromDbFirstActor = firstMovieFromDbIt.next();
		assertThat(firstMovieFromDbFirstActor.getId()).isEqualTo(firstActor.getId());
		
		// second movie
		Movie secondMovieFromDb = moviesIt.next();
		assertThat(secondMovieFromDb.getId()).isEqualTo(secondMovie.getId());
		assertThat(secondMovieFromDb.getActors()).hasSize(3);
		
		Iterator<Actor> secondMovieFromDbIt = secondMovieFromDb.getActors().iterator();
		Actor secondMovieFromDbFirstActor = secondMovieFromDbIt.next();
		Actor secondMovieFromDbSecondActor = secondMovieFromDbIt.next();
		Actor secondMovieFromDbThirdActor = secondMovieFromDbIt.next();
		assertThat(secondMovieFromDbFirstActor.getId()).isEqualTo(firstActor.getId());
		assertThat(secondMovieFromDbSecondActor.getId()).isEqualTo(secondActor.getId());
		assertThat(secondMovieFromDbThirdActor.getId()).isEqualTo(thirdActor.getId());

		// third movie
		Movie thirdMovieFromDb = moviesIt.next();
		assertThat(thirdMovieFromDb.getId()).isEqualTo(thirdMovie.getId());
		assertThat(thirdMovieFromDb.getActors()).hasSize(1);
		
		Iterator<Actor> thirdMovieFromDbIt = thirdMovieFromDb.getActors().iterator();
		Actor thirdMovieFromDbFirstActor = thirdMovieFromDbIt.next();
		assertThat(thirdMovieFromDbFirstActor.getId()).isEqualTo(thirdActor.getId());
		
		// actors
		
		TypedQuery<Actor> actorQuery = em.createQuery("SELECT e FROM Actor e", Actor.class);
		List<Actor> actorsFromDb = actorQuery.getResultList();
		
		assertThat(actorsFromDb).hasSize(3);
		
		Iterator<Actor> actorsIt = actorsFromDb.iterator();
		
		// first actor
		Actor firstActorFromDb = actorsIt.next();
		assertThat(firstActorFromDb.getId()).isEqualTo(firstActor.getId());
		assertThat(firstActorFromDb.getMovies()).hasSize(2);
		
		Iterator<Movie> firstActorFromDbIt = firstActorFromDb.getMovies().iterator();
		Movie firstActorFromDbFirstMovie = firstActorFromDbIt.next();
		Movie firstActorFromDbSecondMovie = firstActorFromDbIt.next();
		assertThat(firstActorFromDbFirstMovie.getId()).isEqualTo(firstMovie.getId());
		assertThat(firstActorFromDbSecondMovie.getId()).isEqualTo(secondMovie.getId());
		
		// second actor
		Actor secondActorFromDb = actorsIt.next();
		assertThat(secondActorFromDb.getId()).isEqualTo(secondActor.getId());
		assertThat(secondActorFromDb.getMovies()).hasSize(1);
		
		Iterator<Movie> secondActorFromDbIt = secondActorFromDb.getMovies().iterator();
		Movie secondActorFromDbFirstMovie = secondActorFromDbIt.next();
		assertThat(secondActorFromDbFirstMovie.getId()).isEqualTo(secondMovie.getId());

		// third actor
		Actor thirdActorFromDb = actorsIt.next();
		assertThat(thirdActorFromDb.getId()).isEqualTo(thirdActor.getId());
		assertThat(thirdActorFromDb.getMovies()).hasSize(2);
		
		Iterator<Movie> thirdActorFromDbIt = thirdActorFromDb.getMovies().iterator();
		Movie thirdActorFromDbFirstMovie = thirdActorFromDbIt.next();
		Movie thirdActorFromDbSecondMovie = thirdActorFromDbIt.next();
		assertThat(thirdActorFromDbFirstMovie.getId()).isEqualTo(secondMovie.getId());
		assertThat(thirdActorFromDbSecondMovie.getId()).isEqualTo(thirdMovie.getId());
		
		em.close();
		emf.close();
	}
	
}
