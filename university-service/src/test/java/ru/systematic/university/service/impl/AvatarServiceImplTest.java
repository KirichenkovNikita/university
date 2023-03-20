package ru.systematic.university.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.systematic.university.domain.Professor;
import ru.systematic.university.domain.Student;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.domain.AvatarApproveReport;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {
    private final List<Student> students = new ArrayList<>();
    private final List<Professor> professors = new ArrayList<>();
    private final List<AvatarApproveReport> result = new ArrayList<>();


    @Mock
    private ProfessorService professorService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AvatarServiceImpl service;

    @BeforeEach
    void setData() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setAvatar("test");
        Student student2 = new Student();
        student2.setId(2L);
        student2.setAvatar("test");
        students.add(student1);
        students.add(student2);

        Professor professor1 = new Professor();
        professor1.setId(1L);
        professor1.setAvatar("test");
        Professor professor2 = new Professor();
        professor2.setId(2L);
        professor2.setAvatar("test");
        professors.add(professor1);
        professors.add(professor2);

        AvatarApproveReport report1 = new AvatarApproveReport();
        report1.setId(1L);
        report1.setUserType("professor");
        report1.setAvatar("/img/test");
        result.add(report1);

        AvatarApproveReport report2 = new AvatarApproveReport();
        report2.setId(2L);
        report2.setUserType("professor");
        report2.setAvatar("/img/test");
        result.add(report2);

        AvatarApproveReport report3 = new AvatarApproveReport();
        report3.setId(1L);
        report3.setUserType("student");
        report3.setAvatar("/img/test");
        result.add(report3);

        AvatarApproveReport report4 = new AvatarApproveReport();
        report4.setId(2L);
        report4.setUserType("student");
        report4.setAvatar("/img/test");
        result.add(report4);
    }

    @Test
    void getAvatars() {
        when(studentService.getNotApprovedAvatarUser()).thenReturn(students);
        when(professorService.getNotApprovedAvatarUser()).thenReturn(professors);

        List<AvatarApproveReport> expected = service.getAvatars();
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void convertAvatarApproveReport() {
        List<AvatarApproveReport> actual = new ArrayList<>();
        AvatarApproveReport report1 = new AvatarApproveReport();
        report1.setId(1L);
        report1.setUserType("professor");
        report1.setAvatar("/img/test");
        actual.add(report1);

        AvatarApproveReport report2 = new AvatarApproveReport();
        report2.setId(2L);
        report2.setUserType("professor");
        report2.setAvatar("/img/test");
        actual.add(report2);

        List<AvatarApproveReport> expected = service.convertAvatarApproveReport(professors, "professor");
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void approveAvatarShouldApproveWhenTypeStudent() {
        assertDoesNotThrow(() -> {
            service.approveAvatar("1", "student");
        });

        verify(studentService).approveAvatar(1L);
        verify(professorService, never()).approveAvatar(1L);
    }

    @Test
    void approveAvatarShouldApproveWhenTypeProfessor() {
        assertDoesNotThrow(() -> {
            service.approveAvatar("1", "professor");
        });

        verify(professorService).approveAvatar(1L);
        verify(studentService, never()).approveAvatar(1L);
    }

    @Test
    void banAvatarShouldBanWhenTypeStudent() {
        assertDoesNotThrow(() -> {
            service.banAvatar("1", "student");
        });

        verify(studentService).banAvatar(1L);
        verify(professorService, never()).banAvatar(1L);
    }

    @Test
    void banAvatarShouldBanWhenTypeProfessor() {
        assertDoesNotThrow(() -> {
            service.banAvatar("1", "professor");
        });

        verify(professorService).banAvatar(1L);
        verify(studentService, never()).banAvatar(1L);
    }
}
