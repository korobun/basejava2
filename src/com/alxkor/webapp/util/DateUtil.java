package com.alxkor.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate ld) {
        if (ld == null) return "";
        return NOW.equals(ld) ? "сейчас" : ld.format(FORMATTER);
    }

    public static LocalDate parse(String date) {
        if ("сейчас".equals(date) || "".equals(date)) return NOW;
        YearMonth yearMonth = YearMonth.parse(date, FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
