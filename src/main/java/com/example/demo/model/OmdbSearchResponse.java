package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OmdbSearchResponse {
    @JsonProperty("Search")
    private List<Search> search;
    private String totalResults;
    @JsonProperty("Response")
    private String response;
}
