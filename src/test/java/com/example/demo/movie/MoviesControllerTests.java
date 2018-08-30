package com.example.demo.movie;

import com.example.demo.config.OmdbConfig;
import com.example.demo.model.OmdbSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({MovieController.class, Movies.class, OmdbConfig.class})
@TestPropertySource(properties = {
        "omdb.host=http://www.omdbapi.com/?apikey=",
        "omdb.apiKey=121212"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc(secure = false)
public class MoviesControllerTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        OmdbSearchResponse response = OBJECT_MAPPER.readValue(getJSON("/omdbSearchResponse.json"), OmdbSearchResponse.class);
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(), anyString(), anyString())).thenReturn(response);
    }

    @Test
    public void testResponse() throws Exception {
        this.mvc.perform(
                get("/movies/?q=harry")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$.[0].title", is("Harry Potter and the Deathly Hallows: Part 2")));
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}