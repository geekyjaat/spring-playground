package com.example.demo.movie;

import com.example.demo.repositories.TrailerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trailers")
public class TrailerController {
    private final TrailerRepository trailerRepository;

    public TrailerController(TrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    @GetMapping("/{id}")
    public Trailer findTrailer(@PathVariable Long id) {
        return this.trailerRepository.findById(id).get();
    }
}