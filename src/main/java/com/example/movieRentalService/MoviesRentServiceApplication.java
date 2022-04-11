package com.example.movieRentalService;

import com.example.movieRentalService.actor.ActorRepository;
import com.example.movieRentalService.genre.Genre;
import com.example.movieRentalService.genre.GenreRepository;
import com.example.movieRentalService.movie.Movie;
import com.example.movieRentalService.movie.MovieRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.ZoneId;
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
//            generateRandomMovies(movieRepository, genre);
//            genreRepository.save(genre);
//            var movie1 = generateRandomMovie();
//            movie1.getPriceList();
//            var movie2 = generateRandomMovie();
//            var movie3 = generateRandomMovie();
//
//            genre.addMovie(movie1);
//            genre.addMovie(movie2);
//            genre.addMovie(movie3);
//            System.out.println();
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

    private void generateRandomMovies(MovieRepository movieRepository, Genre genre) {
        var faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            genre.addMovie(generateRandomMovie());
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
