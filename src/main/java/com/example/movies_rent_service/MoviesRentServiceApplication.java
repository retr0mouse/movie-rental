package com.example.movies_rent_service;

import com.example.movies_rent_service.actor.Actor;
import com.example.movies_rent_service.actor.ActorRepository;
import com.example.movies_rent_service.actor_in_movie.ActorInMovie;
import com.example.movies_rent_service.actor_in_movie.ActorInMovieId;
import com.example.movies_rent_service.genre.Genre;
import com.example.movies_rent_service.genre.GenreRepository;
import com.example.movies_rent_service.movie.Movie;
import com.example.movies_rent_service.movie.MovieRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class MoviesRentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRentServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository,
                                        GenreRepository genreRepository,
                                        ActorRepository actorRepository) {
        return args -> {
//            var faker = new Faker();
//
//            var genre = new Genre(
//                    faker.book().genre(),
//                    faker.lorem().sentence()
//            );
//            var movie1 = generateRandomMovie();
//            var movie2 = generateRandomMovie();
//            var movie3 = generateRandomMovie();
//
//            genre.addMovie(movie1);
//            genre.addMovie(movie2);
//            genre.addMovie(movie3);
//
//            var actor1 = new Actor(
//                    "Not Ryan",
//                    "Not Gosling",
//                    null,
//                    null
//            );
//            var actor2 = new Actor(
//                    "Ryan",
//                    "Gosling",
//                    "Canadian",
//                    LocalDate.of(1980, Calendar.NOVEMBER, 12)
//            );
//            var actor3 = new Actor(
//                    "Ryan",
//                    "Reynolds",
//                    "Canadian",
//                    LocalDate.of(1976, Calendar.OCTOBER, 23)
//            );
//            movie1.addActorInMovie(new ActorInMovie(
//                    new ActorInMovieId(movie1.getId(), actor1.getId()),
//                    movie1,
//                    actor1
//            ));
//            movie2.addActorInMovie(new ActorInMovie(
//                    new ActorInMovieId(movie2.getId(), actor2.getId()),
//                    movie2,
//                    actor2
//            ));
//            movie3.addActorInMovie(new ActorInMovie(
//                    new ActorInMovieId(movie3.getId(), actor3.getId()),
//                    movie3,
//                    actor3
//            ));
//            movieRepository.save(movie1);
//            movieRepository.save(movie2);
//            movieRepository.save(movie3);
        };
    }

    private Movie generateRandomMovie() {
        var faker = new Faker();
        return new Movie(
                faker.book().title(),
                faker.date().between(new Date(0L), new Date())
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate()); // convert Date to LocalDate
    }

    private void sorting(MovieRepository movieRepository) {
        var sort = Sort.by("genreId").ascending()   // this one doesn't work if the variable name contains '_'
                .and(Sort.by("releaseDate").descending());
        movieRepository.findAll(sort)
                .forEach(movie -> System.out.printf("%s, date: %s \n", movie.getTitle(), movie.getReleaseDate()));
    }

//    private void generateRandomMovies(MovieRepository movieRepository) {
//        var faker = new Faker();
//        for (int i = 0; i <= 20; i++) {
//            var movie = new Movie(
//                    faker.book().title(),
//                    faker.date().between(new Date(0L), new Date()),
//                    Integer.toUnsignedLong(Integer.parseInt(faker.number().digit()))
//            );
//            movieRepository.save(movie);
//        }
//    }

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
