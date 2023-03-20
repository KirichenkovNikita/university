package ru.systematic.university.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.systematic.university.domain.Professor;
import ru.systematic.university.domain.Student;
import ru.systematic.university.service.LessonService;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.domain.LessonReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private ProfessorService professorService;

    @Mock
    private  LessonService lessonService;

    @InjectMocks
    private ProfileController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void profileStudentShouldReturnProfileWhenIdCorrect() throws Exception {
        Student student = new Student();
        student.setAvatar("Avatar");
        student.setAvatarApproved(true);
        List<LessonReport> lessons = new ArrayList<>();
        when(studentService.findById(1L)).thenReturn(Optional.of(student));
        when(lessonService.getWeekTable(lessons)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/profile/student/1"))
                .andExpect(view().name("profile/student"));
    }

    @Test
    void profileStudentShouldReturnErrorPageWhenIdIncorrect() throws Exception {
        when(studentService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/profile/student/1"))
                .andExpect(view().name("profile/error"));
    }

    @Test
    void profileProfessorShouldReturnProfileWhenIdCorrect() throws Exception {
        Professor professor = new Professor();
        professor.setAvatar("Avatar");
        professor.setAvatarApproved(true);
        List<LessonReport> lessons = new ArrayList<>();
        when(professorService.findById(1L)).thenReturn(Optional.of(professor));
        when(lessonService.getWeekTable(lessons)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/profile/professor/1"))
                .andExpect(view().name("profile/professor"));
    }

    @Test
    void profileProfessorShouldReturnErrorPageWhenIdIncorrect() throws Exception {
        when(professorService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/profile/professor/1"))
                .andExpect(view().name("profile/error"));
    }

    @Test
    void studentUpdateShouldUpdateEntityAndRedirectPage() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "", "application/json", "{\"key1\": \"value1\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/profile/student/update")
                .file("file", jsonFile.getBytes())
                .flashAttr("studentUpdate", new Student())
                .flashAttr("id", 1L)
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }

    @Test
    void professorUpdateShouldUpdateEntityAndRedirectPage() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "", "application/json", "{\"key1\": \"value1\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/profile/professor/update")
                .file("file", jsonFile.getBytes())
                .flashAttr("professorUpdate", new Professor())
                .flashAttr("id", 1L)
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }
}
