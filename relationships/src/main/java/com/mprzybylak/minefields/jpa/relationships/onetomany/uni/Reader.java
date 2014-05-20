package com.mprzybylak.minefields.jpa.relationships.onetomany.uni;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Reader {

	@Id
	private long id;
	
	@OneToMany
	private Collection<Book> books = new ArrayList<>();
	
	public Reader() {
	}
	
	public Reader(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
	
	public Collection<Book> getBooks() {
		return books;
	}
}
