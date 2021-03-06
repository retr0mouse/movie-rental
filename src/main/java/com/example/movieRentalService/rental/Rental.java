package com.example.movieRentalService.rental;

import com.example.movieRentalService.movie.Movie;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * A Rental Class that represents a "rental" table in the database.
 * Is used to store metadata about rentals and keep statistics about rented movies.
 * This entity is in Many-to-one relationship with Movie entity.
 */
@Entity(name = "Rental")
@Table (name = "rental")
public class Rental {
    @Id
    @SequenceGenerator(
            name = "rental_sequence",
            sequenceName = "rental_sequence",
            allocationSize = 1
    )
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "rental_sequence"
    )
    @Column (
            name = "id",
            updatable = false,
            columnDefinition = "INT NOT NULL"
    )
    private Long id;

    @ManyToOne
    @JoinColumn (
            name = "movie_id",          // column in movie table
            nullable = false,
            referencedColumnName = "id", // id column in genre table
            foreignKey = @ForeignKey(name = "movie_id_fk")  // to specify the name
    )
    private Movie movie;

    @Column (
            name = "start_date"
    )
    private LocalDate startDate;

    @Column (
            name = "end_date"
    )
    private LocalDate endDate;

    @Column (
            name = "total_price"
    )
    private float totalPrice;

//    @Transient  // don't make it a column in table in database
//    @JsonIgnore // don't include it in resulting JSON object
    private Long totalWeeks;

    public Long getTotalWeeks() {
        return totalWeeks;
    }

    public void setTotalWeeks(Long totalWeeks) {
        this.totalWeeks = totalWeeks;
    }

    /**
     * Method to calculate duration of rental in weeks
     */
    public void calculateTotalWeeks() {
        this.totalWeeks = (long) Math.ceil(ChronoUnit.DAYS.between(this.startDate, this.endDate) / 7.0);
    }

    /**
     * Method to calculate a number of weeks before it begins
     * @return number of weeks
     */
    public long getWeeksUntilRentalStart() {
        return (long) Math.max(Math.ceil(ChronoUnit.DAYS.between(LocalDate.now(), this.startDate) / 7.0), 0);
    }

    /**
     * Method to calculate and set a total price of rental,
     * according to current date,
     * rental start date and movie release date
     */
    public void calculateTotalPrice() {
        float totalPrice = 0.0f;
        long weeksUntil = getWeeksUntilRentalStart();   // weeks until the start of rental
        int newDuration = movie.getPriceList().getNewMoviePrice().getDuration();    // duration of "new movie" price
        int regularDuration = movie.getPriceList().getRegularMoviePrice().getDuration();    // duration of "regular movie" price
        long weeks = totalWeeks;    // weeks of rental
        newDuration = Math.max((int) (newDuration - weeksUntil), 0);
        weeksUntil = Math.max(weeksUntil - movie.getPriceList().getNewMoviePrice().getDuration(), 0);
        if (newDuration > 0) {
            totalPrice = movie.getPriceList().getNewMoviePrice().getPrice() * weeks;
            weeks -= weeks;
        }
        if (weeks > 0) {
            regularDuration = Math.max((int) (regularDuration - weeksUntil), 0);
            if (regularDuration > 0) {
                totalPrice += movie.getPriceList().getRegularMoviePrice().getPrice() * weeks;
                weeks -= weeks;
            }
            totalPrice += movie.getPriceList().getOldMoviePrice().getPrice() * weeks;
        }
        this.totalPrice = totalPrice;
        System.out.println(weeks);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Set end date of rental
     * @param endDate end date
     * @throws IllegalStateException if the end date is before the start date
     */
    public void setEndDate(LocalDate endDate) throws IllegalStateException {
        if (endDate.isBefore(this.startDate)) {
            throw new IllegalStateException(
                    "The rental end date shouldn't be before the start date");
        }
        this.endDate = endDate;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public Rental(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rental(LocalDate startDate, LocalDate endDate, float totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Rental() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
