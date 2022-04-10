package com.example.movies_rent_service.actor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findActorByFirstName(String firstName);

    Optional<Actor> findActorByLastName(String lastName);

    Optional<Actor> findActorByBirthDate(LocalDate birthDate);

    Optional<Actor> findActorByCitizenship(String citizenship);
}
