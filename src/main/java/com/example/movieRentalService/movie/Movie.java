package com.example.movieRentalService.movie;

import com.example.movieRentalService.actorInMovie.ActorInMovie;
import com.example.movieRentalService.genre.Genre;
import com.example.movieRentalService.moviePrice.NewMoviePrice;
import com.example.movieRentalService.moviePrice.OldMoviePrice;
import com.example.movieRentalService.moviePrice.PriceList;
import com.example.movieRentalService.moviePrice.RegularMoviePrice;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * A Movie Class that represents a "movie" table in the database.
 * Is used to store metadata about movies.
 * This entity is in Many-To-One relationship with Genre entity.
 * This entity is in Many-To-Many relationship with Actor entity.
 */
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
    private LocalDate releaseDate;

    @Transient  // tells Hibernate that this shouldn't be in a table
    private PriceList priceList;

    /**
     * A Method to calculate the prices according to the release date of the movie and return it,
     * the older the movie is, the cheaper it is to rent it.
     * The idea behind it is that PriceList class contains up-to-date information about a single movie price,
     * so we should update constantly. This implementation is not ideal, but it feels like simplest one.
     * @return PriceList class
     */
    public PriceList getPriceList() {
        long weeksOld = ChronoUnit.WEEKS.between(this.releaseDate, LocalDate.now());
        var newPrice = new NewMoviePrice(Math.max(52 - (int) weeksOld, 0));
        var regularPrice = new RegularMoviePrice(Math.max(156 - (int) weeksOld - newPrice.getDuration(), 0));
        var oldPrice = new OldMoviePrice();
        this.setPriceList(new PriceList(newPrice, regularPrice, oldPrice));
        return this.priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    @ManyToOne (
            cascade = CascadeType.ALL
    )
    @JoinColumn (
        name = "genre_id",          // column in movie table
        nullable = false,
        referencedColumnName = "id", // id column in genre table
        foreignKey = @ForeignKey(name = "genre_id_fk")  // to specify the name
    )
    private Genre genre;

    /**
     * Stores all entities of the actor-in-movie table
     */
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "movie"
    )
    private List<ActorInMovie> actorsInMovies = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + priceList +
                ", genre=" + genre +
                ", actorsInMovies=" + actorsInMovies +
                '}';
    }

    @JsonBackReference
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

    /**
     * @param releaseDate release date of the movie
     * @throws IllegalStateException if we are trying to assign a release date that is in the future
     */
    public void setReleaseDate(LocalDate releaseDate) throws IllegalStateException{
        if (releaseDate.isAfter(LocalDate.now())) {
            throw new IllegalStateException("Movie release date should not be in future");
        }
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }
}
