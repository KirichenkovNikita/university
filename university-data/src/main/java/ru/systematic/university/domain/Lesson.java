package ru.systematic.university.domain;

import lombok.Data;
import ru.systematic.university.anotation.TimetableMatch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lessons")
@TimetableMatch(start = "startTime", end = "endTime",
        message = "The start date must be earlier than the end date")
public class Lesson {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courseid")
    private Long courseId;

    @Column(name = "starttime")
    private LocalDateTime startTime;

    @Column(name = "endtime")
    private LocalDateTime endTime;

    @Column(name = "locationid")
    private Long locationId;
}
