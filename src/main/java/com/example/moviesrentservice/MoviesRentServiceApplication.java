package com.example.moviesrentservice;

import com.example.moviesrentservice.movie.Movie;
import com.example.moviesrentservice.movie.MovieRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MoviesRentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRentServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository) {
        return args -> {
            generateRandomMovies(movieRepository);
            var sort = Sort.by("genreId").ascending();
            movieRepository.findAll(sort)
                    .forEach(movie -> System.out.println(movie.getTitle()));
        };
    }

    private void generateRandomMovies(MovieRepository movieRepository) {
        var faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            var movie = new Movie(
                    faker.name().title(),
                    faker.date().between(new Date(0L), new Date()));
            movieRepository.save(movie);
        }
    }
}
