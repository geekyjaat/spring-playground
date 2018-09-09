package com.example.demo.jpa;

import com.example.demo.movie.Album;
import com.example.demo.repositories.AlbumRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({AlbumController.class, AlbumRepository.class})
@AutoConfigureMockMvc(secure = false)
public class AlbumRepositoryTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    AlbumRepository albumRepository;

    @Test
    public void testGet() throws Exception {
        Long id = new Random().nextLong();
        Album album = new Album();
        album.setId(id);
        album.setTitle("ABC");

        when(albumRepository.findAll()).thenReturn(Collections.singleton(album));

        this.mvc.perform(get("/albums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(id)))
                .andExpect(jsonPath("$[0].title", equalTo(album.getTitle())));
    }

    @Test
    public void testPost() throws Exception {
        JSONObject album = new JSONObject();
        album.put("title", "DEF");

        this.mvc.perform(
                post("/albums")
                        .content(album.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("DEF")));

        verify(albumRepository).save(any(Album.class));
    }
}
