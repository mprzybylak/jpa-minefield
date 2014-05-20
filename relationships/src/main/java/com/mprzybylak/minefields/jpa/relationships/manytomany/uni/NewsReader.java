package com.mprzybylak.minefields.jpa.relationships.manytomany.uni;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class NewsReader {
	
	@Id
	private long id;
	
	@ManyToMany
	private Collection<RssChannel> rssChannels = new ArrayList<>();
	
	public NewsReader() {
	}
	
	public NewsReader(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void addRssChannel(RssChannel rssChannel) {
		rssChannels.add(rssChannel);
	}
	
	public Collection<RssChannel> getRssChannels() {
		return rssChannels;
	}

}
