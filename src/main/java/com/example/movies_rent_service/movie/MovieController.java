package com.example.movies_rent_service.movie;

import com.example.movies_rent_service.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("get")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(value = "get", params = "id")
    public Movie getMovieById(@RequestParam String id) {
        if (!id.matches("^\\d+$")) {
            throw new IllegalStateException("Please specify an id");
        }
        Long longId = Long.parseLong(id);
        return movieService.getMovieById(longId);
    }

    @PostMapping("add")
    public void addMovie(@RequestBody Movie movie, @RequestParam String genreTitle) {
        movieService.addMovie(movie, genreTitle);
    }

    @PutMapping(path = "{id}")
    public void updateMovie(@PathVariable("id") Long id,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String releaseDate,
                            @RequestParam(required = false) Long genreId) {
        movieService.updateMovie(id, title, releaseDate, genreId);
    }

    @DeleteMapping(path = "{id}")
    public void deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
    }
}
