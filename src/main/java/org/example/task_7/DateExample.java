package org.example.task_7;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateExample {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static void main(String[] args) {
        String dateString = "2024-05-13 14:30:00";
        Optional<LocalDateTime> date = getDate(dateString);
        date.ifPresent(localDateTime -> System.out.println("Date: " + localDateTime));
    }

    private static Optional<LocalDateTime> getDate(String date) {
        try {
            return Optional.of(LocalDateTime.parse(date, DATE_TIME_FORMATTER));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}