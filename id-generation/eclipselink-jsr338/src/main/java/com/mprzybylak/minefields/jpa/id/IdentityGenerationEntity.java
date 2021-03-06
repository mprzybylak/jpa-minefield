package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mprzybylak.minefields.jpa.id.base.SampleEntity;

/**
 * Example of identity key generation strategy
 * @author Michal Przybylak
 */
@Entity
public class IdentityGenerationEntity implements SampleEntity<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id = 0L;

	private String text;
	
	public Long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
