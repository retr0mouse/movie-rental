package com.example.movieRentalService.actorInMovie;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An ActorInMovie Repository class which represents a Data Access Layer of Movie entity
 */
public interface ActorInMovieRepository extends JpaRepository<ActorInMovie, ActorInMovieId> {
    void deleteActorInMovieByActorId(Long id);
    void deleteActorInMovieByMovieId(Long id);
}
