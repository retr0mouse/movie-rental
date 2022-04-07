package com.example.movies_rent_service.genre;

import com.example.movies_rent_service.movie.Movie;

import javax.persistence.*;

@Entity(name = "Genre")
@Table(
        name = "genre",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "genre_title_unique",
                        columnNames = "title"
                )
        }
    )
public class Genre {

    @Id
    @SequenceGenerator(    // to generate indexes automatically
            name = "genre_sequence",
            sequenceName = "genre_sequence",
            allocationSize = 1  // a number in which the indexes are increasing
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_sequence"
    )
    @Column (
            name = "id",
            updatable = false
    )
    private Long id;

    @Column (
            name = "title",
            nullable = false
    )
    private String title;

    @Column (
            name = "description"
    )
    private String description;

    @OneToOne (
            mappedBy = "genre"  // for bidirectional relationship
    )
    private Movie movie;

    public Genre() {
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Genre(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Genre(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
