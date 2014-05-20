package com.mprzybylak.minefields.jpa.relationships.onetomany.uni;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class UnidirectionalOneToManyTest {

	@Test
	public void shouldAllowOneToHavenNoBooks() {

		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		Reader reader = new Reader(1L);
		Book book = new Book(2L);

		// when
		em.getTransaction().begin();
		em.persist(reader);
		em.persist(book);
		em.getTransaction().commit();

		// then
		TypedQuery<Reader> readerQuery = em.createQuery("SELECT e FROM Reader e", Reader.class);
		Reader readerFromDb = readerQuery.getSingleResult();
		assertThat(readerFromDb.getId()).isEqualTo(reader.getId());
		assertThat(readerFromDb.getBooks()).isEmpty();

		TypedQuery<Book> bookQuery = em.createQuery("SELECT e FROM Book e", Book.class);
		Book bookFromDb = bookQuery.getSingleResult();
		assertThat(bookFromDb.getId()).isEqualTo(book.getId());

		em.close();
		emf.close();

	}

	@Test
	public void shouldAllowReaderToHaveManyBooks() {
		
		// given
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-pu");
		EntityManager em = emf.createEntityManager();

		Reader reader = new Reader(1L);
		Book firstBook = new Book(2L);
		Book secondBook = new Book(3L);
		Book thirdBook = new Book(4L);

		reader.addBook(firstBook);
		reader.addBook(secondBook);
		reader.addBook(thirdBook);

		// when
		em.getTransaction().begin();
		em.persist(reader);
		em.persist(firstBook);
		em.persist(secondBook);
		em.persist(thirdBook);
		em.getTransaction().commit();

		// then
		TypedQuery<Reader> readerQuery = em.createQuery("SELECT e FROM Reader e", Reader.class);
		Reader readerFromDb = readerQuery.getSingleResult();
		assertThat(readerFromDb.getId()).isEqualTo(reader.getId());
		assertThat(readerFromDb.getBooks()).hasSize(3);

		Iterator<Book> readerFromDbBooksIt = readerFromDb.getBooks().iterator();
		Book readerFromDbFirstBook = readerFromDbBooksIt.next();
		Book readerFromDbSecondBook = readerFromDbBooksIt.next();
		Book readerFromDbThirdBook = readerFromDbBooksIt.next();

		assertThat(readerFromDbFirstBook.getId()).isEqualTo(firstBook.getId());
		assertThat(readerFromDbSecondBook.getId()).isEqualTo(secondBook.getId());
		assertThat(readerFromDbThirdBook.getId()).isEqualTo(thirdBook.getId());

		TypedQuery<Book> bookQuery = em.createQuery("SELECT e FROM Book e",	Book.class);
		List<Book> booksFromDb = bookQuery.getResultList();
		Iterator<Book> booksFromDbIt = booksFromDb.iterator();

		Book firstBookFromDb = booksFromDbIt.next();
		Book secondBookFromDb = booksFromDbIt.next();
		Book thirdBookFromDb = booksFromDbIt.next();

		assertThat(firstBookFromDb.getId()).isEqualTo(firstBook.getId());
		assertThat(secondBookFromDb.getId()).isEqualTo(secondBook.getId());
		assertThat(thirdBookFromDb.getId()).isEqualTo(thirdBook.getId());

		em.close();
		emf.close();
	}
}
