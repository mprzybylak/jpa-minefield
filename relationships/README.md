# Many to many relationship

## Bidirectional

From logical point of view - one of entities is owning side, but from database schema point of view it doesn't matter, because either way there will be join table.

### Entities 

Actor Entity

```java
@Entity
public class Movie {
  @Id private long id
  @ManyToMany private Collection<Actor> actors;
}
```

Movie Entity

```java
@Entity
public class Actor {
  @Id private long id;
  @ManyToMany(mappedBy="actors") private Collection<Movie> movies = new ArrayList<>();
}
```

Schema

```sql
CREATE TABLE MOVIE (ID BIGINT NOT NULL, PRIMARY KEY (ID))
CREATE TABLE ACTOR (ID BIGINT NOT NULL, PRIMARY KEY (ID))
CREATE TABLE MOVIE_ACTOR (movies_ID BIGINT NOT NULL, actors_ID BIGINT NOT NULL, PRIMARY KEY (movies_ID, actors_ID))
ALTER TABLE MOVIE_ACTOR ADD CONSTRAINT MOVIEACTORmoviesID FOREIGN KEY (movies_ID) REFERENCES MOVIE (ID)
ALTER TABLE MOVIE_ACTOR ADD CONSTRAINT MOVIEACTORactorsID FOREIGN KEY (actors_ID) REFERENCES ACTOR (ID)
```

### Relationship example

Java code

```java
Movie firstMovie = new Movie(1L);
Movie secondMovie = new Movie(2L);
Movie thirdMovie = new Movie(3L);

Actor firstActor = new Actor(4L);
Actor secondActor = new Actor(5L);
Actor thirdActor = new Actor(6L);

firstMovie.addActor(firstActor);

secondMovie.addActor(firstActor);
secondMovie.addActor(secondActor);
secondMovie.addActor(thirdActor);

thirdMovie.addActor(thirdActor);

firstActor.addMovie(firstMovie);
firstActor.addMovie(secondMovie);

secondActor.addMovie(secondMovie);

thirdActor.addMovie(secondMovie);
thirdActor.addMovie(thirdMovie);
```

SQLs

```sql
INSERT INTO MOVIE (ID) VALUES (1)
INSERT INTO MOVIE (ID) VALUES (2)
INSERT INTO MOVIE (ID) VALUES (3)

INSERT INTO ACTOR (ID) VALUES (4)
INSERT INTO ACTOR (ID) VALUES (5)
INSERT INTO ACTOR (ID) VALUES (6)

INSERT INTO MOVIE_ACTOR (actors_ID, movies_ID) VALUES (4, 1)
INSERT INTO MOVIE_ACTOR (actors_ID, movies_ID) VALUES (4, 2)
INSERT INTO MOVIE_ACTOR (actors_ID, movies_ID) VALUES (5, 2)
INSERT INTO MOVIE_ACTOR (actors_ID, movies_ID) VALUES (6, 2)
INSERT INTO MOVIE_ACTOR (actors_ID, movies_ID) VALUES (6, 3)
```
