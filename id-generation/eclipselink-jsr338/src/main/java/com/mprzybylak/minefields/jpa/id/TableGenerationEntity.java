package com.mprzybylak.minefields.jpa.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import com.mprzybylak.minefields.jpa.id.base.SampleEntity;

/**
 * Example of Table key generation strategy
 * @author Michal Przybylak
 */
@Entity
public class TableGenerationEntity implements SampleEntity<Long> {

	@Id
	@TableGenerator(name = "TAB_GEN", 
		table = "TABLE_ID_GENERATOR", 
		pkColumnName = "GENERATOR_NAME", 
		valueColumnName = "GENERATOR_VALUE",
		initialValue=0,
		allocationSize=10)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TAB_GEN")
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
