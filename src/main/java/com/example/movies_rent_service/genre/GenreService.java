package com.example.movies_rent_service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) throws RuntimeException{ // TO DO: better exception handling
        try {
            return genreRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void addGenre(Genre genre) {
        Optional<Genre> genreOptional = genreRepository
                .findGenreByTitle(genre.getTitle());
        if (genreOptional.isPresent()) {
            throw new IllegalStateException("Genre with this title already exists");
        }
        genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        boolean exists = genreRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("there is no genre with this id: " + id);
        }
        genreRepository.deleteById(id);
    }

    @Transactional
    public void updateGenre(Long id, String title, String description) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "genre with this id: " + id + " does not exist"));
        if (title != null && title.length() > 0 && !Objects.equals(genre.getTitle(), title)) {
            genre.setTitle(title);
        }
        if (description != null && description.length() > 0 && !Objects.equals(genre.getDescription(), description)) {
            genre.setDescription(description);
        }
    }
}
