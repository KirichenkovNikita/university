package ru.systematic.university.service.domain.week;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LessonWeekTableCell {
    private String name;
    private String link;

    public static LessonWeekTableCell getEmptyCell() {
        return new LessonWeekTableCell("", "");
    }
}
