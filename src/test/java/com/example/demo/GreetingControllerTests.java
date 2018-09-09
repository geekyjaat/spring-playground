package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
@AutoConfigureMockMvc(secure = false)
public class GreetingControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testHateosGreeting() throws Exception {
        this.mvc.perform(
                get("/greeting")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/greeting?name=World")));
    }
}
