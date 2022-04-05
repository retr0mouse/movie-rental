package com.example.movies_rent_service;

import com.example.movies_rent_service.genre.Genre;
import com.example.movies_rent_service.genre.GenreRepository;
import com.example.movies_rent_service.movie.Movie;
import com.example.movies_rent_service.movie.MovieRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;

@SpringBootApplication
public class MoviesRentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRentServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository, GenreRepository genreRepository) {
        return args -> {
//            var faker = new Faker();
//            var genre = new Genre(
//                    faker.book().genre(),
//                    faker.lorem().sentence()
//            );
//            var movie = new Movie(faker.book().title(), faker.date().between(new Date(0L), new Date()), genre);
//            movieRepository.save(movie);
//            generateRandomMovies(movieRepository);
//            generateRandomGenres(genreRepository);
//            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("releaseDate").descending());
//            Page<Movie> page = movieRepository.findAll(pageRequest);
//            System.out.println(page);
        };
    }

    private void sorting(MovieRepository movieRepository) {
        var sort = Sort.by("genreId").ascending()   // this one doesn't work if the variable name contains '_'
                .and(Sort.by("releaseDate").descending());
        movieRepository.findAll(sort)
                .forEach(movie -> System.out.printf("%s, date: %s \n", movie.getTitle(), movie.getReleaseDate()));
    }

    private void generateRandomMovies(MovieRepository movieRepository) {
        var faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            var movie = new Movie(
                    faker.book().title(),
                    faker.date().between(new Date(0L), new Date()),
                    Integer.toUnsignedLong(Integer.parseInt(faker.number().digit()))
            );
            movieRepository.save(movie);
        }
    }

    private void generateRandomGenres(GenreRepository genreRepository) {
        var faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            var genre = new Genre(
                    faker.book().genre(),
                    faker.lorem().sentence()
            );
            genreRepository.save(genre);
        }
    }
}
