package com.example.moviesrentservice.movie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Movie")
@Table
public class Movie {

    @Id
    @SequenceGenerator (    // to generate indexes automatically
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1  // a number in which the indexes are increasing
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )

    @Column (
            name= "id",
            updatable = false
    )
    private Long id;

    @Column (
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column (
            name = "releaseDate",
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date releaseDate;

    @Column (name = "genreId")
    private Long genreId;

    public Movie(String title, Date releaseDate, Long genreId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genreId = genreId;
    }

    public Movie() {
    }

    public Movie(String title, Date releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", release_date=" + releaseDate +
                ", genre_id=" + genreId +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Long getGenreId() {
        return genreId;
    }
}
