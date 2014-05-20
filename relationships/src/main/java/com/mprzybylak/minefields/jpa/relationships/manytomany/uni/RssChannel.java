package com.mprzybylak.minefields.jpa.relationships.manytomany.uni;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RssChannel {
	
	@Id
	private long id;
	
	public RssChannel() {
	}
	
	public RssChannel(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

}
