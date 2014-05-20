package com.mprzybylak.minefields.jpa.relationships.onetoone.bi;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Owner {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;

	/*
	 * Default name for join column will be
	 * 
	 * DOG_ID
	 * 
	 * because:
	 * 1. field in Owner class is called dog
	 * 2. field that contains identifier in Dog class is called id
	 * 
	 * Join collumn will override that default name
	 */
	@JoinColumn(name="DOGGY_ID")
	@OneToOne(
			fetch=FetchType.EAGER // eager fetch by default but it can be changed by "fetch" parameter
			)
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
