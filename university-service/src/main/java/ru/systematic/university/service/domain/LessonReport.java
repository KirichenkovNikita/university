package ru.systematic.university.service.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonReport {
    private Long id;
    private String courseName;
    private String locationName;
    private LocalDateTime start;
    private LocalDateTime end;
    private String locationHref;
}
