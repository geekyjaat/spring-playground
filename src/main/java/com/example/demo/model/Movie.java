package com.example.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    private String title;
    private String imdbId;
    private String poster;
    private String year;
}