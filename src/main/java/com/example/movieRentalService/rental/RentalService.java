package com.example.movieRentalService.rental;

import com.example.movieRentalService.movie.MovieRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public void addRental(List<Rental> rentalList) {
        for (Rental rental: rentalList) {
            var movie = movieRepository.findById(rental.getMovieId());
            if (movie.isEmpty()) {
                throw new IllegalStateException("The movie with id (" + rental.getMovieId() + ") is not in the database");
            }
            if (!checkAvailability(rental)) {
                throw new IllegalStateException(
                        "The movie with id (" + rental.getMovieId() + ") is already rented for this period"
                );
            }
            long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
            double weeks = Math.ceil(days / 7.0);
            System.out.println(weeks);
            var totalPrice = movie.get().getPriceList().getNewMoviePrice().getPrice() * weeks;
            weeks -= movie.get().getPriceList().getNewMoviePrice().getDuration();
            if (weeks > 0) {
                totalPrice += movie.get().getPriceList().getRegularMoviePrice().getPrice() * weeks;
                weeks -= movie.get().getPriceList().getRegularMoviePrice().getDuration();
            }
            if (weeks > 0) {
                totalPrice += movie.get().getPriceList().getOldMoviePrice().getPrice() * weeks;
            }
            rental.setTotalPrice((float) totalPrice);
            rentalRepository.save(rental);
        }
    }

    private boolean checkAvailability(Rental rental) {
        return rentalRepository.findByMovieIdAndEndDateIsGreaterThanEqualAndEndDateIsBefore(rental.getMovieId(), rental.getStartDate(), rental.getEndDate()).isEmpty() &&
                rentalRepository.findByMovieIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(rental.getMovieId(), rental.getStartDate(), rental.getEndDate()).isEmpty() &&
                rentalRepository.findByMovieIdAndStartDateIsAfterAndStartDateIsLessThanEqual(rental.getMovieId(), rental.getStartDate(), rental.getEndDate()).isEmpty();
    }

    @Transactional
    public void updateRental(Long id, Long movieId, LocalDate startDate, LocalDate endDate, float totalPrice) {
        var rental = rentalRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "The rental with id (" + id + ") is not in the database"
        ));
        if (movieId != null && !Objects.equals(rental.getMovieId(), movieId)) {
            rental.setMovieId(movieId);
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
}
