package com.alxkor.webapp.model;

public class TextContent extends Section {
    private String content;

    public TextContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content + "\n";
    }
}
