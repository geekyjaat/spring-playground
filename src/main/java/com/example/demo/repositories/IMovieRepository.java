package com.example.demo.repositories;

import com.example.demo.movie.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends CrudRepository<Movie, Long> {
}
