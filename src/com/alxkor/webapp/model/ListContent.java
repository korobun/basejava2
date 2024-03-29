package com.alxkor.webapp.model;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListContent extends Section {
    private static final long serialVersionUID = 1L;
    public static final ListContent EMPTY = new ListContent("");
    private List<String> items;

    public ListContent() {
    }

    public ListContent(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public ListContent(String... items) {
        this(Arrays.asList(items));
    }

    public List<String> getItems() {
        return items == null ? EMPTY.items : items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListContent that = (ListContent) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}