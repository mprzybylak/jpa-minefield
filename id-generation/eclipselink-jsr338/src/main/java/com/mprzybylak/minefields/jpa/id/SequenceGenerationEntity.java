package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Example of sequence key generation strategy
 * @author Michal Przybylak
 */
@Entity
public class SequenceGenerationEntity {

	@Id
	@SequenceGenerator(name="SEQ_GEN",
		sequenceName="SEQUENCE_ID_GENERATOR", 
		initialValue=0,
		allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
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
