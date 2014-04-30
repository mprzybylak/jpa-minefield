package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.mprzybylak.minefields.jpa.id.base.SampleEntity;

@Entity
public class UUIDGeneratorEntity implements SampleEntity<String> {

	@Id
	@GenericGenerator(name="uuid-generator", strategy="uuid")
	@GeneratedValue(generator="uuid-generator")
	private String id;
	
	private String text;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

}
