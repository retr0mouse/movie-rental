package com.example.movieRentalService.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired  // to autowire the genreService inside this method (dependency injection)
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("get")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping(value = "get", params = "id")
    public Genre getGenreById(@RequestParam Long id) {
        if (!id.toString().matches("^\\d+$")) {
            throw new IllegalStateException("Please provide an id");
        }
        return genreService.getGenreById(id);
    }

    @PostMapping("add")
    public void addGenre(@RequestBody Genre genre) {
        genreService.addGenre(genre);
    }

    @DeleteMapping(path = "{genreId}")
    public void deleteGenre(@PathVariable("genreId") Long id) {
        genreService.deleteGenre(id);
    }

    @PutMapping(path = "{genreId}")
    public void updateGenre(@PathVariable("genreId") Long id,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String description) {
        genreService.updateGenre(id, title, description);
    }
}
