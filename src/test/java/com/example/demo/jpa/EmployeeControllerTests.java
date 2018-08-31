package com.example.demo.jpa;

import com.example.demo.config.SecurityConfig;
import com.example.demo.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@Import({SecurityConfig.class, EmployeeRepository.class})
public class EmployeeControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeRepository employeeRepository;

    @Before
    public void before() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());
    }

    @Test
    public void testAccessManager() throws Exception {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());
        RequestBuilder request = get("/admin/employees")
                .with(user("boss").roles("MANAGER"));
        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testAccessEmployee() throws Exception {
        RequestBuilder request = get("/admin/employees")
                .with(user("boss").roles("USER"));
        mvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void testAccessManagerMock() throws Exception {
        RequestBuilder request = get("/admin/employees");
        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void disallowsAnonymousUsers() throws Exception {
        RequestBuilder request = get("/admin/employees").with(anonymous());
        mvc.perform(request).andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void disallowsAnonymousUsers2() throws Exception {
        RequestBuilder request = get("/admin/employees");
        mvc.perform(request).andExpect(status().isUnauthorized());
    }
}
