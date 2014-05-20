package com.mprzybylak.minefields.jpa.relationships.manytomany.bi;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Movie is an owner of relationship, but from DB point of view it can be
 * inverse side
 */
@Entity
public class Movie {

	@Id
	private long id;

	
	/**
	 * JoinTable annotation is on the owning side (check if true)
	 * Default name of join table = "<OwningSideEntityName>_<InverseSideEntityName>, so
	 * in this case it would be MOVIE_ACTOR
	 * 
	 * JoinColumns default names are provided with the same rules as with regular join columns:
	 * <EntityNameFromOtherSide>_<NameOfIdFieldInEntityOnTheOtherSide>, so:
	 * 
	 * for owning side: MOVIE_ID
	 * for inverse side: ACTOR_ID
	 * 
	 * @JoinTable annotation with embedded @JoinColumn annotations will override
	 * default names
	 * 
	 */
	@JoinTable(name = "MOVIE_ACCTOR_JOIN_TABLE", 
			joinColumns = @JoinColumn(name = "MOVIE_JOIN_COLUMN"), 
			inverseJoinColumns = @JoinColumn(name = "ACTORS_JOIN_COLUMN"))
	@ManyToMany(
			fetch=FetchType.LAZY // Lazy fetch by default by default but it can be changed by "fetch" parameter
			)
	private Collection<Actor> actors = new ArrayList<>();

	public Movie() {
	}

	public Movie(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	
	public Collection<Actor> getActors() {
		return actors;
	}
}
