package com.example.movieRentalService.actorInMovie;

import com.example.movieRentalService.actor.Actor;
import com.example.movieRentalService.actor.ActorRepository;
import com.example.movieRentalService.movie.Movie;
import com.example.movieRentalService.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A Class that represents a Service layer of ActorInMovie entity
 */
@Service
public class ActorInMovieService {
    private final ActorInMovieRepository actorInMovieRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public ActorInMovieService(ActorInMovieRepository actorInMovieRepository,
                               MovieRepository movieRepository,
                               ActorRepository actorRepository) {
        this.actorInMovieRepository = actorInMovieRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    /**
     * Get all actors in movies from the database
     * @return a List of ActorInMovie entities
     */
    public List<ActorInMovie> getAllActorsInMovies() {
        return actorInMovieRepository.findAll();
    }

    /**
     * Get ActorInMovie entity from the database
     * @param actorId actor id
     * @param movieId movie id
     * @return ActorInMovie entity
     * @throws IllegalStateException if actor in movie is not found
     */
    public ActorInMovie getActorInMovieById(Long actorId, Long movieId) throws IllegalStateException{
        var index = new ActorInMovieId(actorId, movieId);
        return actorInMovieRepository.findById(index)
                .orElseThrow(() -> new IllegalStateException(
                        "The 'actor in movie' field with id (" + actorId + "," + movieId + ") is not in the database"
                ));
    }

    /**
     * Add ActorInMovie into the database
     * @param actorInMovieId ActorInMovie id
     * @throws IllegalStateException if either actor or movie is not found or this set is already in the database
     */
    public void addActorInMovie(ActorInMovieId actorInMovieId) throws IllegalStateException{
        Optional<ActorInMovie> actorInMovieOptional = actorInMovieRepository.findById(actorInMovieId);
        Optional<Actor> actor = actorRepository.findById(actorInMovieId.getActorId());
        Optional<Movie> movie = movieRepository.findById(actorInMovieId.getMovieId());
        if (actor.isEmpty()) {
            throw new IllegalStateException("The actor with id (" +
                    actorInMovieId.getActorId() + ") is not in the database");
        }
        if (movie.isEmpty()) {
            throw new IllegalStateException("The movie with id (" +
                    actorInMovieId.getMovieId() + ") is not in the database");
        }
        if (actorInMovieOptional.isPresent()) {
            throw new IllegalStateException(
                "The 'actor in movie' field with id (" +
                actorInMovieId.getActorId() + "," +
                actorInMovieId.getMovieId() + ") is already in the database"
            );
        }
        actorInMovieRepository.save(new ActorInMovie(
                new ActorInMovieId(actorInMovieId.getActorId(), actorInMovieId.getMovieId()),
                movie.get(),
                actor.get())
        );
    }

    /**
     * Delete an ActorInMovie entity from the database
     * @param actorId actor id
     * @param movieId movie id
     * @throws IllegalStateException if ActorInMovie is not found
     */
    public void deleteActorInMovie(Long actorId, Long movieId) throws IllegalStateException{
        var index = new ActorInMovieId(actorId, movieId);
        var actorInMovie = actorInMovieRepository.findById(index);
        if (actorInMovie.isEmpty()) {
            throw new IllegalStateException("The 'actor in movie' field with id (" +
                    actorId + "," +
                    movieId + ") is not in the database");
        }
        actorInMovieRepository.deleteById(index);
    }
}
