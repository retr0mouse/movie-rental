package com.example.movies_rent_service.movie;

import com.example.movies_rent_service.genre.Genre;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "Movie")
@Table (name = "movie")
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
            name = "release_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date releaseDate;

//    @OneToOne (cascade = CascadeType.ALL)
//    @JoinColumn (
//            name = "genre_id",
//            referencedColumnName = "id"
//    )
//    private Genre genre;

//    public void setGenre(Genre genre) {
//        this.genre = genre;
//    }
//
//    public Genre getGenre() {
//        return genre;
//    }

    @Column (
            name = "genre_id",
            updatable = false,
            insertable = false
    )
    private Long genreId;

    public Movie(String title, Date releaseDate, Long genreId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genreId = genreId;
    }

//    public Movie(String title, Date releaseDate, Genre genre) {
//        this.title = title;
//        this.releaseDate = releaseDate;
//        this.genre = genre;
//    }

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
