package com.example.movies_rent_service.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/actor")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("get")
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping(value = "get", params = "id")
    public Actor getActorById(@RequestParam Long id) throws IllegalStateException {
        if (!id.toString().matches("^\\d+$")) {
            throw new IllegalStateException("Please provide an id");
        }
        return actorService.getActorById(id);
    }

    @PostMapping("add")
    public void addActor(@RequestBody Actor actor) {
        actorService.addActor(actor);
    }

    @PutMapping(path = "{id}")
    public void updateActor(@PathVariable("id") Long id,
                            @RequestParam(required = false) String firstName,
                            @RequestParam(required = false) String lastName,
                            @RequestParam(required = false) String birthDate,
                            @RequestParam(required = false) String citizenship) {
        actorService.updateActor(id, firstName, lastName, birthDate, citizenship);
    }

    @DeleteMapping(path = "{id}")
    public void deleteActor(@PathVariable("id") Long id) {
        actorService.deleteActor(id);
    }
}
