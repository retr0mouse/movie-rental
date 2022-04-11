package com.example.movieRentalService.rental;

import com.example.movieRentalService.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A Class that represents a Service layer of Rental entity
 */
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, MovieRepository movieRepository) {
        this.rentalRepository = rentalRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * Get all rentals from the database
     * @return a List of Rental entities
     */
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Get a rental entity from the database
     * @param id rental id
     * @return Rental entity
     * @throws IllegalStateException if rental does not exist in the database
     */
    public Rental getRentalById(Long id) throws IllegalStateException {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "The rental with id (" + id + ") is not in the database"
                ));
    }

    /**
     * Add rental into the database
     * @param rentalList a list of rental entities
     * @param movieId movie id
     * @throws IllegalStateException if Movie is not found or is already rented for this period
     */
    @Transactional
    public void addRental(List<Rental> rentalList, Long movieId) throws  IllegalStateException{
        for (Rental rental: rentalList) {
            var movie = movieRepository.findById(movieId);
            if (movie.isEmpty()) {
                throw new IllegalStateException("The movie with id (" + movieId + ") is not in the database");
            }
            if (checkAvailability(rental, movieId)) {
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


    /**
     * Check if the time of rental for specific movie is available
     * @param rental rental entity
     * @param movieId movie id
     * @return true if available, otherwise false
     */
    private boolean checkAvailability(Rental rental, Long movieId) {
        return !(rentalRepository.findByMovieIdAndEndDateIsGreaterThanEqualAndEndDateIsLessThanEqual(movieId, rental.getStartDate(), rental.getEndDate()).isEmpty() &&
               rentalRepository.findByMovieIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(movieId, rental.getStartDate(), rental.getEndDate()).isEmpty() &&
               rentalRepository.findByMovieIdAndStartDateIsGreaterThanEqualAndStartDateIsLessThanEqual(movieId, rental.getStartDate(), rental.getEndDate()).isEmpty());
    }

    /**
     * Update a Rental entity in the database
     * @param id Rental id
     * @param movieId movie id
     * @param startDate rental start date
     * @param endDate rental end date
     * @param totalPrice rental total price
     * @throws IllegalStateException if Rental or Movie is not found
     */
    @Transactional
    public void updateRental(Long id, Long movieId, LocalDate startDate, LocalDate endDate, float totalPrice) throws IllegalStateException{
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

    /**
     * Delete a Rental entity from the database
     * @param id rental id
     * @throws IllegalStateException if Rental is not found
     */
    public void deleteRental(Long id) throws IllegalStateException{
        rentalRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "The rental with id (" + id + ") is not in the database"
        ));
        rentalRepository.deleteById(id);
    }

    public List<Object> getStatistics() {
        return rentalRepository.countRentals();
    }
}
