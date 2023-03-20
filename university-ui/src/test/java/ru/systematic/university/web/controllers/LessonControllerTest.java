package ru.systematic.university.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.systematic.university.service.LessonService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class LessonControllerTest {
    @Mock
    private LessonService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LessonController(service))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void indexShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/lesson"))
                .andExpect(view().name("lesson/lessons"));
    }

    @Test
    void deleteShouldDeleteLessonById() throws Exception {
        mockMvc.perform(delete("/lesson/delete/1")
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }

    @Test
    void addNewShouldAddLesson() throws Exception {
        mockMvc.perform(post("/lesson/addNew")
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }
}
