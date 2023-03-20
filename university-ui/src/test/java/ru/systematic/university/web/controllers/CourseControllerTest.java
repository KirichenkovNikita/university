package ru.systematic.university.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.systematic.university.service.CourseService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Mock
    private CourseService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CourseController(service)).build();
    }

    @Test
    void indexShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/course"))
                .andExpect(view().name("course/courses"));
    }

    @Test
    void deleteShouldDeleteCourseById() throws Exception {
        mockMvc.perform(delete("/course/delete/1")
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }

    @Test
    void addNewShouldAddCourse() throws Exception {
        mockMvc.perform(post("/course/addNew")
                .param("name", "name")
                .param("description", "1")
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }
}
