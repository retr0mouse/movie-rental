package com.example.movieRentalService.rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> findByMovieIdAndEndDateIsGreaterThanEqualAndEndDateIsBefore(Long movieId, LocalDate startDate, LocalDate endDate);

    Optional<Rental> findByMovieIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(Long movieId, LocalDate startDate, LocalDate endDate);

    Optional<Rental> findByMovieIdAndStartDateIsAfterAndStartDateIsLessThanEqual(Long movieId, LocalDate startDate, LocalDate endDate);
}
