package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class IncrementGeneratorEntity {

	@Id
	@GenericGenerator(name="hibernate-increment-generator", strategy="increment")
	@GeneratedValue(generator="hibernate-increment-generator")
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
