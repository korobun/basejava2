package com.alxkor.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListOrganization extends Section {
    private final List<Organization> items;

    public ListOrganization(List<Organization> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public ListOrganization(Organization... items) {
        this(Arrays.asList(items));
    }

    public List<Organization> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOrganization that = (ListOrganization) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
