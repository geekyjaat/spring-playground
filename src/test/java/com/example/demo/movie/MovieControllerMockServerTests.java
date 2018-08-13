package com.example.demo.movie;

import com.example.demo.config.OmdbConfig;
import com.example.demo.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@WebMvcTest({MovieController.class, Movies.class, OmdbConfig.class})
@TestPropertySource(properties = {
        "omdb.host=http://www.omdbapi.com/?apikey=",
        "omdb.apiKey=121212"
})
public class MovieControllerMockServerTests {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private Movies movies;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        this.mockServer = MockRestServiceServer.createServer(this.restTemplate);
    }

    @Test
    public void testMockRequestResponse() throws Exception {
        this.mockServer //{key}&s={search}
                .expect(requestTo("http://www.omdbapi.com/?apikey=121212&s=harry"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJSON("/omdbSearchResponse.json"), MediaType.APPLICATION_JSON));

        List<Movie> result = this.movies.search("harry");
        assertTrue(result.size() == 10);
        assertTrue(result.stream().anyMatch(s -> s.getTitle().equals("Harry Potter and the Deathly Hallows: Part 2")));

        this.mockServer.verify();

    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}
