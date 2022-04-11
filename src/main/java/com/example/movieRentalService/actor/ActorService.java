package com.example.movieRentalService.actor;

import com.example.movieRentalService.actorInMovie.ActorInMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

/**
 * A Class that represents a Service layer of Actor entity
 */
@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorInMovieRepository actorInMovieRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, ActorInMovieRepository actorInMovieRepository) {
        this.actorRepository = actorRepository;
        this.actorInMovieRepository = actorInMovieRepository;
    }

    /**
     * Get all actors from the database
     * @return a List of Actor entities
     */
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    /**
     * Get Actor entity from the database
     * @param id actor id
     * @return Actor entity
     * @throws IllegalStateException if actor is not found
     */
    public Actor getActorById(Long id) throws IllegalStateException {
        return actorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The actor with id (" + id + ") is not in the database"
                ));
    }

    /**
     * Add Actor into the database
     * @param actor Actor entity
     * @throws IllegalStateException if Actor already exists
     */
    public void addActor(Actor actor) throws IllegalStateException {
        if (actorRepository.findActorByFirstName(actor.getFirstName()).isPresent() &&
            actorRepository.findActorByLastName(actor.getLastName()).isPresent() &&
            actorRepository.findActorByBirthDate(actor.getBirthDate()).isPresent()&&
            actorRepository.findActorByCitizenship(actor.getCitizenship()).isPresent()
        ) {
            throw new IllegalStateException("This actor is already in the database");
        }
        actorRepository.save(actor);
    }

    /**
     * Update an Actor entity in the database
     * @param id Actor id
     * @param firstName Actor firstname
     * @param lastName Actor lastname
     * @param birthDate Actor birthdate
     * @param citizenship Actor citizenship
     * @throws DateTimeParseException if actor is not found
     */
    @Transactional
    public void updateActor(Long id,
                            String firstName,
                            String lastName,
                            String birthDate,
                            String citizenship) throws DateTimeParseException {

        var actor = actorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The actor with id (" + id + ") is not in the database"
                ));
        if (firstName != null && firstName.length() > 0 && !Objects.equals(actor.getFirstName(), firstName)) {
            actor.setFirstName(firstName);
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(actor.getLastName(), lastName)) {
            actor.setLastName(lastName);
        }
        if (birthDate != null  && birthDate.length() > 0 &&
            !Objects.equals(actor.getBirthDate().toString(), birthDate)) {
            actor.setBirthDate(LocalDate.parse(birthDate));
        }
        if (citizenship != null && citizenship.length() > 0 && !Objects.equals(actor.getCitizenship(), citizenship)) {
            actor.setCitizenship(citizenship);
        }
    }

    /**
     * Deletes an Actor entity from the database
     * @param id Actor id
     * @throws IllegalStateException if Actor is not found
     */
    @Transactional
    public void deleteActor(Long id) throws IllegalStateException{
        var actor = actorRepository.findById(id);
        if (actor.isEmpty()) {
            throw new IllegalStateException("The actor with id (" + id + ") is not in the database");
        }
        actorInMovieRepository.deleteActorInMovieByActorId(id);
        actorRepository.deleteById(id);
    }
}
