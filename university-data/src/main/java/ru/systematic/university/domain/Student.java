package ru.systematic.university.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"courses"}, callSuper = true)
@Entity
@Table(name = "students")
public class Student extends User {
    @Column(name = "groupid")
    private Long groupId;

    @Column(name = "type")
    private Long type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "studentscourses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Course> courses;
}
