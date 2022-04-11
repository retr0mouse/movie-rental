package com.example.movieRentalService.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A Class that represents a Service layer of Genre entity
 */
@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Get all genres from the database
     * @return a List of Genre entities
     */
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * Get Genre from the database
     * @param id genre id
     * @return Genre entity
     * @throws IllegalStateException if entity is not found
     */
    public Genre getGenreById(Long id) throws IllegalStateException {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The genre with id (" + id + ") is not in the database"));
    }

    /**
     * Add Genre into the database
     * @param genre Genre entity
     * @throws IllegalStateException if genre is not found
     */
    public void addGenre(Genre genre) throws IllegalStateException {
        Optional<Genre> genreOptional = genreRepository
                .findGenreByTitle(genre.getTitle());
        if (genreOptional.isPresent()) {
            throw new IllegalStateException("This genre is already in the database");
        }
        genreRepository.save(genre);
    }

    /**
     * Update a Genre entity in the database
     * @param id Genre id
     * @param title Genre title
     * @param description Genre description
     * @throws IllegalStateException if genre is not found
     */
    @Transactional
    public void updateGenre(Long id, String title, String description) throws  IllegalStateException {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The genre with id (" + id + ") is not in the database"));
        if (title != null && title.length() > 0 && !Objects.equals(genre.getTitle(), title)) {
            genre.setTitle(title);
        }
        if (description != null && description.length() > 0 && !Objects.equals(genre.getDescription(), description)) {
            genre.setDescription(description);
        }
    }

    /**
     * Delete a Genre entity from the database
     * @param id Genre id
     * @throws IllegalStateException if genre is not found
     */
    public void deleteGenre(Long id) throws IllegalStateException {
        boolean exists = genreRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("The genre with id (" + id + ") is not in the database");
        }
        genreRepository.deleteById(id);
    }
}
