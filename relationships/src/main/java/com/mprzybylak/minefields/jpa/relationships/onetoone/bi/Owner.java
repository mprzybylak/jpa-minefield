package com.mprzybylak.minefields.jpa.relationships.onetoone.bi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Owner {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	@OneToOne
	private Dog dog;

	public Owner() {
	}
	
	public Owner(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setDog(Dog dog) {
		this.dog = dog;
	}
	
	public Dog getDog() {
		return dog;
	}

}
