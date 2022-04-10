package com.example.movies_rent_service.actor;

import com.example.movies_rent_service.actor_in_movie.ActorInMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorInMovieRepository actorInMovieRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, ActorInMovieRepository actorInMovieRepository) {
        this.actorRepository = actorRepository;
        this.actorInMovieRepository = actorInMovieRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Long id) throws IllegalStateException {
        return actorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The actor with id (" + id + ") is not in the database"
                ));
    }

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

    @Transactional
    public void deleteActor(Long id) {
        var actor = actorRepository.findById(id);
        if (actor.isEmpty()) {
            throw new IllegalStateException("The actor with id (" + id + ") is not in the database");
        }
        actorInMovieRepository.deleteActorInMovieByActorId(id);
        actorRepository.deleteById(id);
    }
}
