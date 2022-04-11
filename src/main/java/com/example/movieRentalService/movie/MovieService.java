package com.example.movieRentalService.movie;

import com.example.movieRentalService.actorInMovie.ActorInMovieRepository;
import com.example.movieRentalService.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

    public  List<Movie> getSortedAndPagedMovies(String sort1, String sort2, String type1, String type2, Integer pages) {
        Sort sort = Sort.by("title").ascending();
        if (type1 != null && sort1 != null) {
            if ("d".equals(type1)) {
                sort = Sort.by(sort1).descending();
            } else {
                sort = Sort.by(sort1).ascending();
            }
        }
        if (type2 != null && sort2 != null) {
            if ("d".equals(type2)) {
                sort = sort.and(Sort.by(sort2).descending());
            } else {
                sort = sort.and(Sort.by(sort2).ascending());
            }
        }
        if (pages != null) {
            return movieRepository.findAll(PageRequest.of(0, pages, sort)).getContent();
        }
        return movieRepository.findAll(sort);
    }

    private void sorting(MovieRepository movieRepository) {
        var sort = Sort.by("genreId").ascending()   // this one doesn't work if the variable name contains '_'
                .and(Sort.by("releaseDate").descending());
        movieRepository.findAll(sort)
                .forEach(movie -> System.out.printf("%s, date: %s \n", movie.getTitle(), movie.getReleaseDate()));
    }
}
