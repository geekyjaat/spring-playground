package com.example.demo.text;

import com.example.demo.config.WordCountConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({WordCounterController.class, WordCounter.class, WordCountConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WordCounterControllerTests {

    @Autowired
    MockMvc mvc;

    @Test
    public void testCount() throws Exception {

        this.mvc.perform(
                post("/words/count")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("A brown cow jumps over a brown fox")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.brown", is(2)));
    }

    @Test
    public void testCountWithPunch() throws Exception {

        this.mvc.perform(
                post("/words/count")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("How now, brown cow")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)))
                .andExpect(jsonPath("$.brown", is(1)));
    }

    @Test
    public void testCountWithPunchCase() throws Exception {

        this.mvc.perform(
                post("/words/count")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("The BROWN cow jumps over a brown fox")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.brown", is(2)));
    }
}
