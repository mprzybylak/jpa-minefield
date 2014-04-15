package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mprzybylak.minefields.jpa.id.base.SampleEntity;

/**
 * Example of AUTO key generation strategy
 * @author Michal Przybylak
 */
@Entity
public class AutoGenerationEntity implements SampleEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String text;
	
	public long getId() {
		return id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
