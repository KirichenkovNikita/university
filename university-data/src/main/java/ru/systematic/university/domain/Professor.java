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
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"courses"}, callSuper = true)
@Entity
@Table(name = "professors")
public class Professor extends User {
    @Column(name = "linkedin")
    private String linkedin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "professorscourses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id"))
    private List<Course> courses = new ArrayList<>();
}
