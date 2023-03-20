package ru.systematic.university.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "classrooms")
public class ClassRoom extends Location {
    @Column(name = "number")
    private String number;

    @Column(name = "locationlink")
    private String locationLink;
}
