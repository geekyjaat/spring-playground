package com.example.demo.movie;

import com.example.demo.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoviesTests {

    @Autowired
    private Movies movies;

    @Test
    public void testMoviesGet() {
        List<Movie> result = movies.search("harry");
        assertTrue(result.size() == 10);
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Harry Potter and the Deathly Hallows: Part 2")));
    }
}