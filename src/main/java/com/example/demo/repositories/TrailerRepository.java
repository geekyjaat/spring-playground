package com.example.demo.repositories;

import com.example.demo.movie.Trailer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailerRepository extends CrudRepository<Trailer, Long> {
}
