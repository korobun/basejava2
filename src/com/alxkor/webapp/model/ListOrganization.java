package com.alxkor.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListOrganization extends Section {
    List<Organization> organizationList;

    public ListOrganization(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : organizationList) sb.append(o.toString() + "\n");
        return sb.toString();
    }
}
