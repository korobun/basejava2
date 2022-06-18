package com.alxkor.webapp.model;


import java.util.ArrayList;
import java.util.List;

public class ListContent extends Section {
    private List<String> content;

    public ListContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (String s : content) sb.append(s + "\n");
        return sb.toString();
    }
}
