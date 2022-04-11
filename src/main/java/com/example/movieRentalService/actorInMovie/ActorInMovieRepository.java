package com.example.movieRentalService.actorInMovie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorInMovieRepository extends JpaRepository<ActorInMovie, ActorInMovieId> {
    void deleteActorInMovieByActorId(Long id);
    void deleteActorInMovieByMovieId(Long id);
}
