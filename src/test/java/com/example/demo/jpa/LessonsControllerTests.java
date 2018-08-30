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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LessonsControllerTests {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
        Lesson lesson = getBlahLesson();
        lesson = repository.save(lesson);

        this.mvc.perform(
                get(String.format("/lessons/%d", lesson.getId().intValue()))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$.title", is(lesson.getTitle())));
    }

    private Lesson getBlahLesson() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Blah");
        return lesson;
    }

    @Test
    @Transactional
    @Rollback
    public void testList() throws Exception {
        Lesson lesson = getBlahLesson();
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
        Lesson lesson = getBlahLesson();
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

    @Test
    @Transactional
    @Rollback
    public void testGetTitle() throws Exception {
        Lesson lesson = repository.save(getBlahLesson());

        this.mvc.perform(
                get(String.format("/lessons/find/%s", lesson.getTitle()))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(lesson.getId().intValue())))
                .andExpect(jsonPath("$.title", equalTo(lesson.getTitle())));

        this.mvc.perform(
                get(String.format("/lessons/find/%s", "not found"))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetBetweenDates() throws Exception {
        // insert one to be found
        Lesson lesson = new Lesson();
        lesson.setTitle("Dependency Injection");
        lesson.setDeliveredOn(DATE_FORMAT.parse("2014-03-17"));
        lesson = repository.save(lesson);

        // insert another not to be found
        Lesson lesson2 = getBlahLesson();
        lesson2.setTitle("Transactions");
        lesson2.setDeliveredOn(DATE_FORMAT.parse("2014-03-17"));
        lesson2 = repository.save(lesson2);

        Lesson lesson3 = getBlahLesson();
        lesson3.setDeliveredOn(new Date());
        repository.save(lesson3);

        this.mvc.perform(
                get(String.format("/lessons/between?date1=%s&date2=%s", "2014-03-16", "2014-03-18"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].deliveredOn", is("2014-03-17")))
                .andExpect(jsonPath("$[1].deliveredOn", is("2014-03-17")));

    }
}
