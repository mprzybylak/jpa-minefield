package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AutoGenerationEntity {

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
