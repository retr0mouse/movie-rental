package com.example.movieRentalService.actor;

import com.example.movieRentalService.actorInMovie.ActorInMovie;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * An Actor Class that represents an "actor" table in the database.
 * Is used to store metadata about actors.
 * This entity is in One-To-Many relationship with ActorInMovie entity.
 */
@Entity(name = "Actor")
@Table (name = "actor")
public class Actor {

    @Id
    @SequenceGenerator (
            name = "actor_sequence",
            sequenceName = "actor_sequence",
            allocationSize = 1
    )
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "actor_sequence"
    )
    @Column (
            name = "id",
            updatable = false
    )
    private Long id;

    @Column (
            name = "first_name",
            columnDefinition = "VARCHAR(255) NOT NULL"
    )
    private String firstName;

    @Column (
            name = "last_name",
            columnDefinition = "VARCHAR(255) NOT NULL"
    )
    private String lastName;

    @Column (
            name = "citizenship"
    )
    private String citizenship;

    @Column (
            name = "birth_date",
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDate birthDate;

    @OneToMany (
            cascade = CascadeType.ALL,
            mappedBy = "actor"
    )
    private List<ActorInMovie> actorsInMovies = new ArrayList<>();

    public Actor(String firstName, String lastName, String citizenship, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
    }

    public Actor() {
    }

    public void addActorInMovie(ActorInMovie actorInMovie) {
        if (!actorsInMovies.contains(actorInMovie)) {
            actorsInMovies.add(actorInMovie);
        }
    }

    public void removeActorInMovie(ActorInMovie actorInMovie) {
        actorsInMovies.remove(actorInMovie);
    }

    @JsonBackReference
    public List<ActorInMovie> getActorsInMovies() {
        return actorsInMovies;
    }

    public void setActorsInMovies(List<ActorInMovie> actorsInMovies) {
        this.actorsInMovies = actorsInMovies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
