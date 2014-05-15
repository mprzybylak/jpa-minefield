package com.mprzybylak.minefields.jpa.relationships.manytomany.bi;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Actor is an inverse side, but from DB point of view it can be owning side
 */
@Entity
public class Actor {

	@Id
	private long id;
	
	@ManyToMany(mappedBy="actors")
	private Collection<Movie> movies = new ArrayList<>();
	
	public Actor() {
	}
	
	public Actor(long id) {
		this.id = id;
	}
	
	public void addMovie(Movie movie) {
		movies.add(movie);
	}
	
	public Collection<Movie> getMovies() {
		return movies;
	}
	
	public long getId() {
		return id;
	}
	
}
