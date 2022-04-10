package com.example.movies_rent_service.actor_in_movie;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable // means that this class can be embeddable into another entity
public class ActorInMovieId implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "actor_id")
    private Long actorId;

    public ActorInMovieId(Long actor_id, Long movie_id) {
        this.movieId = movie_id;
        this.actorId = actor_id;
    }

    public ActorInMovieId() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movie_id) {
        this.movieId = movie_id;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actor_id) {
        this.actorId = actor_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorInMovieId that = (ActorInMovieId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(actorId, that.actorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, actorId);
    }
}
