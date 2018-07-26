package com.example.demo.math;

import com.example.demo.service.MathService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({MathController.class, MathService.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MathControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test_pi() throws Exception {
        this.mvc
                .perform(get("/math/pi")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("3.141592653589793"));
    }

    @Test
    public void test_calculate_add() throws Exception {
        this.mvc
                .perform(get("/math/calculate?operation=add&x=4&y=6")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("4 + 6 = 10"));
    }

    @Test
    public void test_calculate_multiply() throws Exception {
        this.mvc
                .perform(get("/math/calculate?operation=multiply&x=4&y=6")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("4 * 6 = 24"));
    }

    @Test
    public void test_calculate_subtract() throws Exception {
        this.mvc
                .perform(get("/math/calculate?operation=subtract&x=4&y=6")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("4 - 6 = -2"));
    }

    @Test
    public void test_calculate_divide() throws Exception {
        this.mvc
                .perform(get("/math/calculate?operation=divide&x=30&y=5")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("30 / 5 = 6"));
    }

    @Test
    public void test_calculate_default() throws Exception {
        this.mvc
                .perform(get("/math/calculate?x=30&y=5")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("30 + 5 = 35"));
    }

    @Test
    public void test_sum() throws Exception {
        this.mvc
                .perform(post("/math/sum?n=4&n=5&n=6")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("4 + 5 + 6 = 15"));
    }

    @Test
    public void test_volume() throws Exception {
        int length = 3;
        int width = 4;
        int height = 5;


        this.mvc
                .perform(get(String.format("/math/volume/%d/%d/%d", length, width, height))
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 3x4x5 rectangle is 60"));

        this.mvc
                .perform(post(String.format("/math/volume/%d/%d/%d", length, width, height))
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 3x4x5 rectangle is 60"));

        this.mvc
                .perform(patch(String.format("/math/volume/%d/%d/%d", length, width, height))
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 3x4x5 rectangle is 60"));

        length = 6;
        width = 7;
        height = 8;

        this.mvc
                .perform(patch(String.format("/math/volume/%d/%d/%d", length, width, height))
                        .accept(MediaType.TEXT_PLAIN)
                ).andExpect(status().isOk())
                .andExpect(content().string("The volume of a 6x7x8 rectangle is 336"));

    }

    @Test
    public void test_area_circle() throws Exception {

        MockHttpServletRequestBuilder cirlce = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("radius", "4");

        this.mvc.perform(cirlce)
                .andExpect(status().isOk())
                .andExpect(content().string("Area of a circle with a radius of 4 is 50.26548"));
    }

    @Test
    public void test_area_rectangle() throws Exception {
        MockHttpServletRequestBuilder rectangle = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "rectangle")
                .param("width", "4")
                .param("height", "7");

        this.mvc.perform(rectangle)
                .andExpect(status().isOk())
                .andExpect(content().string("Area of a 4x7 rectangle is 28"));
    }

    @Test
    public void test_area_invalid() throws Exception {
        MockHttpServletRequestBuilder invalid = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "rectangle")
                .param("radius", "4");

        this.mvc.perform(invalid)
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid"));
    }

}