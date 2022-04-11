package com.example.movieRentalService.rental;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    @Column (
            name = "movie_id",
            updatable = false,
            columnDefinition = "INT NOT NULL"
    )
    private Long movieId;

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



    public void setId(Long id) {
        this.id = id;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public Long getMovieId() {
        return movieId;
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

    public Rental(Long movieId, LocalDate startDate, LocalDate endDate, float totalPrice) {
        this.movieId = movieId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Rental() {
    }

//    @Transient
//    private Long totalWeeks = ChronoUnit.WEEKS.between(this.getStartDate(), this.getEndDate());
//
//    public Long getTotalWeeks() {
//        return totalWeeks;
//    }
//
//    public void setTotalWeeks(Long totalWeeks) {
//        this.totalWeeks = totalWeeks;
//    }
}
