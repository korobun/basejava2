package com.alxkor.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.alxkor.webapp.util.DateUtil.NOW;
import static com.alxkor.webapp.util.DateUtil.of;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link homepage;
    private final List<Position> positions;

    public Organization(Link homepage, List<Position> positions) {
        this.homepage = homepage;
        this.positions = positions;
    }

    public Organization(String title, String url, Position... positions) {
        this(new Link(title, url), Arrays.asList(positions));
    }

    @Override
    public String toString() {
        return "Organization(" + homepage + ", " + positions + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homepage.equals(that.homepage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homepage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        private final LocalDate from;
        private final LocalDate to;
        private final String position;
        private final String description;

        public Position(LocalDate from, LocalDate to, String position, String description) {
            Objects.requireNonNull(from, "start date must not be null");
            Objects.requireNonNull(to, "end date must not be null");
            Objects.requireNonNull(position, "position must not be null");
            this.from = from;
            this.to = to;
            this.position = position;
            this.description = description;
        }

        public Position(int startYear, Month startMonth, String position, String description) {
            this(of(startYear, startMonth), NOW, position, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String position, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), position, description);
        }

        @Override
        public String toString() {
            return "Record(" + from + ", " + to + ", " + position + ", " + description + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position record = (Position) o;

            if (!from.equals(record.from)) return false;
            if (!to.equals(record.to)) return false;
            if (!position.equals(record.position)) return false;
            return description != null ? description.equals(record.description) : record.description == null;
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            result = 31 * result + position.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}
