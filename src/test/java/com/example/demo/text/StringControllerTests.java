package com.example.demo.text;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({WordCounterController.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringControllerTests {

    @MockBean
    WordCounter wordCounter;

    @Autowired
    MockMvc mvc;

    @Before
    public void setUp() {
        Map<String, Integer> result = new HashMap<>();
        result.put("to", 2);
        result.put("the", 2);
        result.put("moon", 2);
        when(wordCounter.count("to the moon, to the moon")).thenReturn(result);
    }

    @Test
    public void testCounter() throws Exception {
        this.mvc.perform(
                post("/words/count")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("to the moon, to the moon")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.moon", is(2)));
    }
}
