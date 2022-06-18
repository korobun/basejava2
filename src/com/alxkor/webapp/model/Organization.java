package com.alxkor.webapp.model;

import java.time.LocalDate;

public class Organization {
    private final LocalDate from;
    private final LocalDate to;
    private final String title;
    private final String position;
    private final String description;

    public Organization(LocalDate from, LocalDate to, String title, String position, String description) {
        this.from = from;
        this.to = to;
        this.title = title;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Период: " + from + " - " + to + "\n" +
                "Наименование: " + title + "\n" +
                "Специальность: " + position + "\n" +
                (description != null ? "Описание: " + description : "");
    }
}
