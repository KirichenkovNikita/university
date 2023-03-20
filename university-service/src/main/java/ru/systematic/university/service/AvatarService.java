package ru.systematic.university.service;

import ru.systematic.university.domain.User;
import ru.systematic.university.service.domain.AvatarApproveReport;

import java.util.List;

public interface AvatarService {
    List<AvatarApproveReport> getAvatars();

    List<AvatarApproveReport> convertAvatarApproveReport(List<? extends User> users, String type);

    void approveAvatar(String id, String type);

    void banAvatar(String id, String type);
}
