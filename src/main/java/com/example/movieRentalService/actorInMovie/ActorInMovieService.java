package com.example.movieRentalService.actorInMovie;

import com.example.movieRentalService.actor.Actor;
import com.example.movieRentalService.actor.ActorRepository;
import com.example.movieRentalService.movie.Movie;
import com.example.movieRentalService.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public List<ActorInMovie> getAllActorsInMovies() {
        return actorInMovieRepository.findAll();
    }

    public ActorInMovie getActorInMovieById(Long actorId, Long movieId) {
        var index = new ActorInMovieId(actorId, movieId);
        return actorInMovieRepository.findById(index)
                .orElseThrow(() -> new IllegalStateException(
                        "The 'actor in movie' field with id (" + actorId + "," + movieId + ") is not in the database"
                ));
    }

    public void addActorInMovie(ActorInMovieId actorInMovieId) {
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

    public void deleteActorInMovie(Long actorId, Long movieId) {
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
