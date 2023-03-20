package ru.systematic.university.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"studentsCount"})
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "students",
            joinColumns = @JoinColumn(name = "groupid"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Student> studentsCount;
}
