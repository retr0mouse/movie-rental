package com.example.movies_rent_service.actor_in_movie;

import com.example.movies_rent_service.actor.Actor;
import com.example.movies_rent_service.movie.Movie;

import javax.persistence.*;

@Entity(name = "Actor_in_movie")
@Table(name = "actor_in_movie")
public class ActorInMovie {

    @EmbeddedId
    private ActorInMovieId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(
            name = "movie_id",
            foreignKey = @ForeignKey(name = "movie_id_fk")
    )
    private Movie movie;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(
            name = "actor_id",
            foreignKey = @ForeignKey(name = "actor_id_fk")
    )
    private Actor actor;

    public ActorInMovieId getId() {
        return id;
    }

    public void setId(ActorInMovieId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public ActorInMovie() {
    }

    public ActorInMovie(Movie movie, Actor actor) {
        this.movie = movie;
        this.actor = actor;
    }

    public ActorInMovie(ActorInMovieId id, Movie movie, Actor actor) {
        this.id = id;
        this.movie = movie;
        this.actor = actor;
    }
}
