package ru.systematic.university.service.domain.week;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LessonWeekTableTimes {
    public final static List<LocalTime> TIMES = new ArrayList<>();

    static {
        TIMES.add(LocalTime.of(9, 0));
        TIMES.add(LocalTime.of(11, 0));
        TIMES.add(LocalTime.of(13, 0));
        TIMES.add(LocalTime.of(15, 0));
        TIMES.add(LocalTime.of(17, 0));
    }
}
