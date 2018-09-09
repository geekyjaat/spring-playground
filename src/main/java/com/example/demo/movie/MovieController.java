package com.example.demo.movie;

import com.example.demo.model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies/")
public class MovieController {

    private Movies movies;

    public MovieController(Movies movies) {
        this.movies = movies;
    }

    @GetMapping
    public List<Movie> searchMovies(@RequestParam("q") String query) {
        return this.movies.search(query);
    }
}
