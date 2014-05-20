package com.mprzybylak.minefields.jpa.relationships.onetoone.bi;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Dog {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	
	@OneToOne(
			mappedBy="dog",
			fetch=FetchType.EAGER // eager fetch by default but it can be changed by "fetch" parameter
			)
	private Owner owner;
	
	public Dog() {
	}
	
	public Dog(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	public Owner getOwner() {
		return owner;
	}
}
