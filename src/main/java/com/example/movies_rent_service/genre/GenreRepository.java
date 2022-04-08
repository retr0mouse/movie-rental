package com.example.movies_rent_service.genre;

import com.example.movies_rent_service.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
