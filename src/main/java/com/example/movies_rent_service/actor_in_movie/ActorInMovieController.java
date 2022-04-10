package com.example.movies_rent_service.actor_in_movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/actorInMovie")
public class ActorInMovieController {
    private final ActorInMovieService actorInMovieService;

    @Autowired
    public ActorInMovieController(ActorInMovieService actorInMovieService) {
        this.actorInMovieService = actorInMovieService;
    }

    @GetMapping("get")
    public List<ActorInMovie> getAllActorsInMovies() {
        return actorInMovieService.getAllActorsInMovies();
    }

    @PostMapping("add")
    public void addActorsInMovies(@RequestBody ActorInMovieId actorInMovieId) {
        actorInMovieService.addActorInMovie(actorInMovieId);
    }

    @DeleteMapping(path = "{actorId}/{movieId}")
    public void deleteActorInMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId) {
        actorInMovieService.deleteActorInMovie(actorId, movieId);
    }
}
