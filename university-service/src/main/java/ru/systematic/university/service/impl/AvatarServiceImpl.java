package ru.systematic.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.systematic.university.domain.User;
import ru.systematic.university.service.AvatarService;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.domain.AvatarApproveReport;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private static final String ICON_PATH = "/img/";
    private static final String STUDENT_TYPE = "student";
    private static final String PROFESSOR_TYPE = "professor";

    private final ProfessorService professorService;
    private final StudentService studentService;

    @Override
    public List<AvatarApproveReport> getAvatars() {
        List<AvatarApproveReport> result = new ArrayList<>();
        List<AvatarApproveReport> students = convertAvatarApproveReport(
                studentService.getNotApprovedAvatarUser(),
                STUDENT_TYPE);
        List<AvatarApproveReport> professors = convertAvatarApproveReport(
                professorService.getNotApprovedAvatarUser(),
                PROFESSOR_TYPE);

        result.addAll(professors);
        result.addAll(students);

        return result;
    }

    @Override
    public List<AvatarApproveReport> convertAvatarApproveReport(List<? extends User> users, String type) {
        List<AvatarApproveReport> result = new ArrayList<>();
        users.forEach(user -> {
            AvatarApproveReport report = new AvatarApproveReport();
            report.setId(user.getId());
            report.setAvatar(ICON_PATH + user.getAvatar());
            report.setFirstName(user.getFirstName());
            report.setLastName(user.getLastName());
            report.setUserType(type);

            result.add(report);
        });

        return result;
    }

    @Override
    public void approveAvatar(String id, String type) {
        if (type.equals(STUDENT_TYPE)) {
            studentService.approveAvatar(Long.valueOf(id));
        } else if (type.equals(PROFESSOR_TYPE)) {
            professorService.approveAvatar(Long.valueOf(id));
        }
    }

    @Override
    public void banAvatar(String id, String type) {
        if (type.equals(STUDENT_TYPE)) {
            studentService.banAvatar(Long.valueOf(id));
        } else if (type.equals(PROFESSOR_TYPE)) {
            professorService.banAvatar(Long.valueOf(id));
        }
    }
}
