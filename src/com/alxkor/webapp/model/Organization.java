package com.alxkor.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Link homepage;
    private final LocalDate from;
    private final LocalDate to;
    private final String position;
    private final String description;

    public Organization(String title, String url, LocalDate from, LocalDate to, String position, String description) {
        Objects.requireNonNull(from, "start date must not be null");
        Objects.requireNonNull(to, "end date must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.homepage = new Link(title, url);
        this.from = from;
        this.to = to;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homepage=" + homepage +
                ", from=" + from +
                ", to=" + to +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homepage.equals(that.homepage)) return false;
        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;
        if (!position.equals(that.position)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = homepage.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
