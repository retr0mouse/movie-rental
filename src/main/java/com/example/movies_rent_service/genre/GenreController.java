package com.example.movies_rent_service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired  // to autowire the genreService inside this method (dependency injection)
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("getAll")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("getById")
    public Genre getGenreById(@RequestParam String id) {
        Long longId = Long.parseLong(id);
        return genreService.getGenreById(longId);
    }

    @PostMapping("addGenre")
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
