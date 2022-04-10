package com.example.movies_rent_service.movie;

import com.example.movies_rent_service.actor_in_movie.ActorInMovieRepository;
import com.example.movies_rent_service.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorInMovieRepository actorInMovieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        ActorInMovieRepository actorInMovieRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorInMovieRepository = actorInMovieRepository;
    }

    // returns all movies from database
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // returns movie by id form database
    public Movie getMovieById(Long id) throws IllegalStateException{
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Movie with id (" + id + ") does not exist"
                ));
    }

    // add movie by genre id both to list of movies in genre entity and movie table in database
    public void addMovie(Movie movie, String genreTitle) {
        if (movieRepository.findMovieByTitle(movie.getTitle()).isPresent() &&
                movieRepository.findMovieByReleaseDate(movie.getReleaseDate()).isPresent()) {
            throw new IllegalStateException("Inserted movie already exists");
        }
        var genre = genreRepository.findGenreByTitle(genreTitle)
                .orElseThrow(() -> new IllegalStateException(
                        "Genre with title (" + genreTitle + ") does not exist"
                ));
        genre.addMovie(movie);
        genreRepository.save(genre);
    }

    @Transactional
    public void updateMovie(Long id, String title, String releaseDate, Long genreId) throws DateTimeParseException {
        var actor = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The movie with id (" + id + ") is not in the database"
                ));
        if (title != null && title.length() > 0 && !Objects.equals(actor.getTitle(), title)) {
            actor.setTitle(title);
        }
        if (releaseDate != null && releaseDate.length() > 0 && !Objects.equals(actor.getReleaseDate().toString(), releaseDate)) {
            actor.setReleaseDate(LocalDate.parse(releaseDate));
        }
        if (genreId != null  && !Objects.equals(actor.getGenre().getId(), genreId)) {
            var genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalStateException(
                            "The movie with id (" + id + ") is not in the database"));
            actor.setGenre(genre);
        }
    }

    @Transactional
    public void deleteMovie(Long id) {
        var movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new IllegalStateException("The genre with id (" + id + ") is not in the database");
        }
        actorInMovieRepository.deleteActorInMovieByMovieId(id);
        movieRepository.deleteById(id);
    }
}
