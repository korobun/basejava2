package com.alxkor.webapp.model;

import java.util.Objects;

public class TextContent extends Section {
    private static final long serialVersionUID = 1L;
    public static final TextContent EMPTY = new TextContent("");
    private String content;

    public TextContent() {
    }

    public TextContent(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextContent that = (TextContent) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
