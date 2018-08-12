package com.example.demo.movie;

import com.example.demo.config.OmdbConfig;
import com.example.demo.model.Movie;
import com.example.demo.model.OmdbSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Movies {

    @Autowired
    RestTemplate restTemplate;

    private OmdbConfig omdbConfig;

    public Movies(OmdbConfig omdbConfig) {
        this.omdbConfig = omdbConfig;
    }

    public List<Movie> search(String query) {
        OmdbSearchResponse searchResponse = this.restTemplate.getForObject(
                omdbConfig.getHost() + "{key}&s={search}",
                OmdbSearchResponse.class,
                omdbConfig.getApiKey(),
                query
        );

        return searchResponse.getSearch().stream().map(s -> Movie.builder()
                .title(s.getTitle())
                .imdbId(s.getImdbId())
                .year(s.getYear())
                .poster(s.getPoster())
                .build()).collect(Collectors.toList());
    }
}