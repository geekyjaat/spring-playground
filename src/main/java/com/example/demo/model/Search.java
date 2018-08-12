package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Search {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
}
