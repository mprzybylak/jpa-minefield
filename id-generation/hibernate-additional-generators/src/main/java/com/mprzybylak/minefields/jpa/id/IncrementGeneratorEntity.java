package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.mprzybylak.minefields.jpa.id.base.SampleEntity;

@Entity
public class IncrementGeneratorEntity implements SampleEntity<Long> {

	@Id
	@GenericGenerator(name="hibernate-increment-generator", strategy="increment")
	@GeneratedValue(generator="hibernate-increment-generator")
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
