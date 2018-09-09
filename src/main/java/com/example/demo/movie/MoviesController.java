package com.example.demo.movie;

import com.example.demo.repositories.IMovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    private IMovieRepository repository;

    public MoviesController(IMovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Movie> all() {
        Iterable<Movie> movies = this.repository.findAll();
        movies.forEach(m -> {
            m.add(linkTo(methodOn(MoviesController.class).movie(m.getMovieId())).withSelfRel());
            m.add(linkTo(methodOn(TrailerController.class).findTrailer(m.getMovieId())).withRel("trailer"));
        });
        return movies;
    }

    @GetMapping("/{id}")
    public Movie movie(@PathVariable Long id) {
        Movie movie = this.repository.findById(id).get();
        movie.add(linkTo(methodOn(MoviesController.class).movie(id)).withSelfRel());
        return movie;
    }
}
