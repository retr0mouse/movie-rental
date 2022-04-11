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
import java.util.List;
import java.util.Objects;

/**
 * A Class that represents a Service layer of Movie entity
 */
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

    /**
     * Get all movies from the database
     * @return a List of Movie entities
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Get Movie entity from the database
     * @param id movie id
     * @return Movie entity
     * @throws IllegalStateException if movie is not found
     */
    public Movie getMovieById(Long id) throws IllegalStateException{
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Movie with id (" + id + ") does not exist"
                ));
    }

    /**
     * Add Movie into the database
     * @param movie Movie entity
     * @param genreTitle Genre title
     * @throws IllegalStateException if Movie already exists
     */
    public void addMovie(Movie movie, String genreTitle) throws IllegalStateException{
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

    /**
     * Update a Movie entity in the database
     * @param id Movie id
     * @param title Movie title
     * @param releaseDate Movie release date
     * @param genreId Genre id
     * @throws DateTimeParseException if given date is not in format "YYYY-MM-DD"
     */
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

    /**
     * Delete a Movie entity from the database
     * @param id Movie id
     * @throws IllegalStateException if Genre is not found
     */
    @Transactional
    public void deleteMovie(Long id) throws IllegalStateException{
        var movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new IllegalStateException("The genre with id (" + id + ") is not in the database");
        }
        actorInMovieRepository.deleteActorInMovieByMovieId(id);
        movieRepository.deleteById(id);
    }

    /**
     * Sort movies with two optional parameters and with optional pagination
     * @param sort1 the first parameter to sort through, property represents a column in database
     * @param sort2 the second parameter to sort through, property represents a column in database
     * @param type1 the first sort type, can be "a" for 'Ascending' or "d" for 'Descending'
     * @param type2 the second sort type, can be "a" for 'Ascending' or "d" for 'Descending'
     * @param pages the number of pages to show
     * @return a list of sorted movies with or without pagination
     */
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
}
