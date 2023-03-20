package ru.systematic.university.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class UniversityControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UniversityController()).build();
    }

    @Test
    void indexShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/university"))
                .andExpect(view().name("university/index"));
    }
}
