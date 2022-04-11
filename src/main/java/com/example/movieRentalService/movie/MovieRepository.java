package com.example.movieRentalService.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MovieRepository extends JpaRepository<Movie, Long> {   // In generics are entity class and id
    @Query("SELECT m FROM Movie m WHERE m.title = ?1") // Overrides the Hibernate prediction which is based on method title
    Optional<Movie> findMovieByTitle(String title);

    Optional<Movie> findMovieByReleaseDate(LocalDate releaseDate);

//    @Query("SELECT m FROM Movie m WHERE m.releaseDate = ?1 AND m.genreId > ?2")
//    List<Movie> findMoviesByReleaseDateEqualsAndGenreIdIsGreaterThan(LocalDate date, Long genreId);

    @Query(value = "SELECT * FROM Movie WHERE releaseDate = :date AND genreId > :genre", nativeQuery = true)
    List<Movie> findMoviesByReleaseDateEqualsAndGenreIdIsGreaterThanNative(
            @Param("date") LocalDate date,
            @Param("genre") Long genre
    );

    @Transactional  // wrap it into a transaction
    @Modifying  // we are just modifying, nothing to map here
    @Query("DELETE FROM Movie m WHERE m.id = ?1")
    void deleteMovieById(Long id);   // return a number of affected rows
}
