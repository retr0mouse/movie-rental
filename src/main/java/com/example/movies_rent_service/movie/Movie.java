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
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private Date releaseDate;

//    @Column (
//            name = "genre_id",
//            updatable = false,
//            insertable = false
//    )
//    private Long genreId;

    @ManyToOne (
            cascade = CascadeType.ALL
//            cascade = CascadeType.ALL,  // this allows to save the genre into a db when saving a movie
//                                        // ALL means that all crud operations should update the genre as well
//            fetch = FetchType.LAZY     // eager fetch is by default, it fetches the other table no matter what
    )
    @JoinColumn (
        name = "genre_id",          // column in movie table
        nullable = false,
        referencedColumnName = "id", // id column in genre table
        foreignKey = @ForeignKey(name = "genre_id_fk")  // to specify the name
    )
    private Genre genre;

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
                ", releaseDate=" + releaseDate +
                ", genre=" + genre +
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }
}
