package com.mprzybylak.minefields.jpa.relationships.onetoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * This class is owner of relationship
 */
@Entity
public class FootballPlayer {

	@Id
	private long id;
	
	/*
	 * Default name for join column will be
	 * 
	 * BALL_ID
	 * 
	 * because:
	 * 1. field in FootballPlayer class is called ball
	 * 2. field that contains identifier in Ball class is called id
	 * 
	 * Join collumn will override that default name
	 */
	@OneToOne
	@JoinColumn(unique=true, name="FOOTBALL_ID")
	private Ball ball;
	
	public FootballPlayer() {
	}

	public FootballPlayer(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Ball getBall() {
		return ball;
	}
}
