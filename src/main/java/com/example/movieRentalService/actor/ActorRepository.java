package com.example.movieRentalService.actor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * An Actor Repository class which represents a Data Access Layer of Actor entity
 */
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findActorByFirstName(String firstName);

    Optional<Actor> findActorByLastName(String lastName);

    Optional<Actor> findActorByBirthDate(LocalDate birthDate);

    Optional<Actor> findActorByCitizenship(String citizenship);
}
