package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Example of identity key generation strategy
 * @author Michal Przybylak
 */
@Entity
public class IdentityGenerationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String text;
	
	public long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
