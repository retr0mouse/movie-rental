package com.example.movieRentalService.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    // SELECT movie.title, count(rental.movie_id) as "number of rentals" from movie
    // left join rental on rental.movie_id = movie.id group by movie.id order by "number of rentals" desc;
    @Query(value = "SELECT movie.title, count(rental.movie_id) as \"number of rentals\" from movie " +
            "left join rental on rental.movie_id = movie.id group by movie.id " +
            "order by \"number of rentals\" desc", nativeQuery = true)
    List<Object> countRentals();
    Optional<Rental> findByMovieIdAndEndDateIsGreaterThanEqualAndEndDateIsLessThanEqual(Long movieId, LocalDate startDate, LocalDate endDate);

    Optional<Rental> findByMovieIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(Long movieId, LocalDate startDate, LocalDate endDate);

    Optional<Rental> findByMovieIdAndStartDateIsGreaterThanEqualAndStartDateIsLessThanEqual(Long movieId, LocalDate startDate, LocalDate endDate);
}
