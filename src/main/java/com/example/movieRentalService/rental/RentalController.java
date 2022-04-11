package com.example.movieRentalService.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/rental")
public class RentalController {
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("get")
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping(value = "get", params = "id")
    public Rental getRentalById(@RequestParam Long id) {
        if (!id.toString().matches("^\\d+$")) {
            throw new IllegalStateException("Please provide an id");
        }
        return rentalService.getRentalById(id);
    }

    @GetMapping(value = "getStatistics")
    public List<Object> getStatistics() {
        return rentalService.getStatistics();
    }

    @PostMapping("add")
    public void addRental(@RequestBody List<Rental> rentalList, @RequestParam Long movieId) {
        rentalService.addRental(rentalList, movieId);
    }

    @PutMapping(path = "{rentalId}")
    public void updateRental(@PathVariable("rentalId") Long id,
                             @RequestParam(required = false) Long movieId,
                             @RequestParam(required = false) LocalDate startDate,
                             @RequestParam(required = false) LocalDate endDate,
                             @RequestParam(required = false) float totalPrice) {
        rentalService.updateRental(id, movieId,startDate, endDate, totalPrice);
    }

    @DeleteMapping(path = "{id}")
    public void deleteRental(@PathVariable("id") Long id) {
        rentalService.deleteRental(id);
    }
}
