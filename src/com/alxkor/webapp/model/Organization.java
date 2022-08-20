package com.alxkor.webapp.model;

import com.alxkor.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.alxkor.webapp.util.DateUtil.NOW;
import static com.alxkor.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    public static final Organization EMPTY = new Organization("", "", Organization.Position.EMPTY);
    private static final long serialVersionUID = 1L;

    private Link homepage;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(Link homepage, List<Position> positions) {
        this.homepage = homepage;
        this.positions = positions;
    }

    public Organization(String title, String url, Position... positions) {
        this(new Link(title, url), Arrays.asList(positions));
    }

    public Link getHomepage() {
        return homepage;
    }

    public List<Position> getPositions() {
        return positions;
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
        return Objects.equals(homepage, that.homepage) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, positions);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        public static final Position EMPTY = new Position();
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate from;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate to;
        private String position;
        private String description;

        public Position() {
        }

        public Position(LocalDate from, LocalDate to, String position, String description) {
            Objects.requireNonNull(from, "start date must not be null");
            Objects.requireNonNull(to, "end date must not be null");
            Objects.requireNonNull(position, "position must not be null");
            this.from = from;
            this.to = to;
            this.position = position;
            this.description = description == null ? "" : description;
        }

        public Position(int startYear, Month startMonth, String position, String description) {
            this(of(startYear, startMonth), NOW, position, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String position, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), position, description);
        }

        public LocalDate getFrom() {
            return from;
        }

        public LocalDate getTo() {
            return to;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Record(" + from + ", " + to + ", " + position + ", " + description + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return from.equals(position1.from) && to.equals(position1.to) && position.equals(position1.position) && Objects.equals(description, position1.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, position, description);
        }
    }
}