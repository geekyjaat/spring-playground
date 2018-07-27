package com.example.demo.jpa;

import com.example.demo.entities.Lesson;
import com.example.demo.repositories.LessonRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LessonsControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Test
    @Transactional
    @Rollback
    public void testCreate() throws Exception {

        this.mvc.perform(
                post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"SQL\"}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.title", is("SQL")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Blah");
        lesson = repository.save(lesson);

        this.mvc.perform(
                get(String.format("/lessons/%d", lesson.getId().intValue()))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$.title", is(lesson.getTitle())));
    }

    @Test
    @Transactional
    @Rollback
    public void testList() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Blah");
        lesson = repository.save(lesson);

        this.mvc.perform(
                get("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$[0].title", equalTo(lesson.getTitle())));
    }

    @Test
    @Transactional
    @Rollback
    public void testPatch() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Blah");
        lesson = repository.save(lesson);

        this.mvc.perform(
                patch(String.format("/lessons/%d", lesson.getId().intValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Spring Security\", \"deliveredOn\": \"2017-04-12\"}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$.title", equalTo("Spring Security")))
                .andExpect(jsonPath("$.deliveredOn", equalTo("2017-04-12")));


        this.mvc.perform(
                patch(String.format("/lessons/%d", 1000))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"SQL\"}")
        )
                .andExpect(status().isNotFound());
    }
}
