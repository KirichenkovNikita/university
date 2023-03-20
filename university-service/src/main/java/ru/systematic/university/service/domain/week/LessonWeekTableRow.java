package ru.systematic.university.service.domain.week;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LessonWeekTableRow {
    private final List<LessonWeekTableCell> rows = new ArrayList<>();

    public void add(LessonWeekTableCell cell) {
        rows.add(cell);
    }
}

