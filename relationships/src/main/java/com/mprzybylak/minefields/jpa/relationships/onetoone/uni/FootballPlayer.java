package com.mprzybylak.minefields.jpa.relationships.onetoone.uni;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class FootballPlayer {

	@Id
	private long id;
	
	@OneToOne
	private Ball ball;

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
