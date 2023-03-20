package ru.systematic.university.service.domain;

import lombok.Data;

@Data
public class AvatarApproveReport {
    private Long id;
    private String userType;
    private String firstName;
    private String lastName;
    private String avatar;
}
