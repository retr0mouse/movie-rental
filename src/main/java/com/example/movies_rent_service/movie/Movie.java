package com.example.movies_rent_service.movie;

import com.example.movies_rent_service.actor.Actor;
import com.example.movies_rent_service.actor_in_movie.ActorInMovie;
import com.example.movies_rent_service.genre.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity (name = "Movie")
@Table (name = "movie")
public class Movie {
    @Id
    @SequenceGenerator (    // to generate indexes automatically
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1  // a number in which the indexes are increasing
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )
    @Column (
            name= "id",
            updatable = false
    )
    private Long id;

    @Column (
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column (
            name = "release_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private Date releaseDate;

    @Transient  // tells Hibernate that this shouldn't be a table
    private Float price;

//    @Column (
//            name = "genre_id",
//            updatable = false,
//            insertable = false
//    )
//    private Long genreId;

    @ManyToOne (
            cascade = CascadeType.ALL
//            cascade = CascadeType.ALL,  // this allows to save the genre into a db when saving a movie
//                                        // ALL means that all crud operations should update the genre as well
//            fetch = FetchType.LAZY     // eager fetch is by default, it fetches the other table no matter what
    )
    @JoinColumn (
        name = "genre_id",          // column in movie table
        nullable = false,
        referencedColumnName = "id", // id column in genre table
        foreignKey = @ForeignKey(name = "genre_id_fk")  // to specify the name
    )
    private Genre genre;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "movie"
    )
//    @JoinTable(
//            name = "actor_in_movie",
//            joinColumns = @JoinColumn(
//                    name = "actor_id",
//                    foreignKey = @ForeignKey(name = "actor_id_fk")
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "movie_id",
//                    foreignKey = @ForeignKey(name = "movie_id_fk")
//            )
//    )
    private List<ActorInMovie> actorsInMovies = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title, Date releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", actorsInMovies=" + actorsInMovies +
                '}';
    }

    public List<ActorInMovie> getActorsInMovies() {
        return actorsInMovies;
    }

    public void addActorInMovie(ActorInMovie actorInMovie) {
        if (!actorsInMovies.contains(actorInMovie)) {
            actorsInMovies.add(actorInMovie);
        }
    }

    public void removeActorInMovie(ActorInMovie actorInMovie) {
        actorsInMovies.remove(actorInMovie);
    }

    public void setActorsInMovies(List<ActorInMovie> actors) {
        this.actorsInMovies = actors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }
}
