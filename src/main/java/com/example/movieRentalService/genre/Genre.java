package com.example.movieRentalService.genre;

import com.example.movieRentalService.movie.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Genre Class that represents a "genre" table in the database.
 * Is used to store metadata about genres.
 * This entity is in One-To-Many relationship with Movie entity.
 */
@Entity(name = "Genre")
@Table(
        name = "genre",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "genre_title_unique",
                        columnNames = "title"
                )
        }
    )
public class Genre {

    @Id
    @SequenceGenerator(    // to generate indexes automatically
            name = "genre_sequence",
            sequenceName = "genre_sequence",
            allocationSize = 1  // a number in which the indexes are increasing
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_sequence"
    )
    @Column (
            name = "id",
            updatable = false
    )
    private Long id;

    @Column (
            name = "title",
            nullable = false
    )
    private String title;

    @Column (
            name = "description"
    )
    private String description;

    @OneToMany (
            mappedBy = "genre",
            cascade = CascadeType.ALL,
            // orphan removal in this type of relation is a bad thing
            fetch = FetchType.LAZY
    )
    private List<Movie> movies = new ArrayList<>();

    public Genre() {
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @JsonBackReference  // to prevent StackOverflowError when accessing this method
    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", movies=" + movies +
                '}';
    }

    public Genre(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Genre(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Add Movie into this entity's List and into the database
     * @param movie Movie entity
     */
    public void addMovie(Movie movie) {
        if (!this.movies.contains(movie)) {
            this.movies.add(movie);
            movie.setGenre(this);
        }
    }

    /**
     * Remove Movie from this entity's List and from the database
     * @param movie Movie entity
     */
    public void removeMovie(Movie movie) {
        if (this.movies.contains(movie)) {
            this.movies.remove(movie);
            movie.setGenre(null);
        }
    }
}
