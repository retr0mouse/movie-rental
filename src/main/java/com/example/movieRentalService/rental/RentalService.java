package com.example.movieRentalService.rental;

import com.example.movieRentalService.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, MovieRepository movieRepository) {
        this.rentalRepository = rentalRepository;
        this.movieRepository = movieRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) throws IllegalStateException {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The rental with id (" + id + ") is not in the database"
                ));
    }

    @Transactional
    public void addRental(List<Rental> rentalList, Long movieId) {
        for (Rental rental: rentalList) {
            var movie = movieRepository.findById(movieId);
            if (movie.isEmpty()) {
                throw new IllegalStateException("The movie with id (" + movieId + ") is not in the database");
            }
            if (!checkAvailability(rental, movieId)) {
                throw new IllegalStateException(
                        "The movie with id (" + movieId + ") is already rented for this period"
                );
            }
            rental.setMovie(movie.get());
            rental.calculateTotalWeeks();
            rental.calculateTotalPrice();
            rentalRepository.save(rental);
        }
    }



    private boolean checkAvailability(Rental rental, Long movieId) {
        return rentalRepository.findByMovieIdAndEndDateIsGreaterThanEqualAndEndDateIsLessThanEqual(movieId, rental.getStartDate(), rental.getEndDate()).isEmpty() &&
               rentalRepository.findByMovieIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(movieId, rental.getStartDate(), rental.getEndDate()).isEmpty() &&
               rentalRepository.findByMovieIdAndStartDateIsGreaterThanEqualAndStartDateIsLessThanEqual(movieId, rental.getStartDate(), rental.getEndDate()).isEmpty();
    }

    @Transactional
    public void updateRental(Long id, Long movieId, LocalDate startDate, LocalDate endDate, float totalPrice) {
        var rental = rentalRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "The rental with id (" + id + ") is not in the database"
        ));
        if (movieId != null && !Objects.equals(rental.getMovie().getId(), movieId)) {
            rental.setMovie(movieRepository.findById(movieId).orElseThrow(() -> new IllegalStateException(
                    "The movie with id (" + movieId + ") is not in the database"
            )));
        }
        if (startDate != null && !Objects.equals(rental.getStartDate(), startDate)) {
            rental.setStartDate(startDate);
        }
        if (endDate != null && !Objects.equals(rental.getEndDate(), endDate)) {
            rental.setEndDate(endDate);
        }
        if (rental.getTotalPrice() != totalPrice) {
            rental.setTotalPrice(totalPrice);
        }
    }

    public void deleteRental(Long id) {
        rentalRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "The rental with id (" + id + ") is not in the database"
        ));
        rentalRepository.deleteById(id);
    }

    public List<Object> getStatistics() {
        return rentalRepository.countRentals();
    }
}
