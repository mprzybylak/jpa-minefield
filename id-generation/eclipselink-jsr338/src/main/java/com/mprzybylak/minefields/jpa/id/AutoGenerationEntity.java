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
public class AutoGenerationEntity implements SampleEntity<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id = 0L;
	
	private String text;
	
	public Long getId() {
		return id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
