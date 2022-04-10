package com.example.movies_rent_service.actor_in_movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorInMovieRepository extends JpaRepository<ActorInMovie, ActorInMovieId> {
    void deleteActorInMovieByActorId(Long id);
    void deleteActorInMovieByMovieId(Long id);
}
