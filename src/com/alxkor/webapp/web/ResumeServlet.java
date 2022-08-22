package com.alxkor.webapp.web;

import com.alxkor.webapp.Config;
import com.alxkor.webapp.model.*;
import com.alxkor.webapp.storage.Storage;
import com.alxkor.webapp.util.DateUtil;
import com.alxkor.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = Resume.EMPTY;
                storage.save(r);
                break;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextContent.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListContent.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            ListOrganization orgSection = (ListOrganization) r.getSection(type);
                            List<Organization> firstEmptyOrganizations = new ArrayList<>();
                            firstEmptyOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getItems()) {
                                    List<Organization.Position> firstEmptyPositions = new ArrayList<>();
                                    firstEmptyPositions.add(Organization.Position.EMPTY);
                                    firstEmptyPositions.addAll(org.getPositions());
                                    firstEmptyOrganizations.add(new Organization(org.getHomepage(), firstEmptyPositions));
                                }
                            }
                            section = new ListOrganization(firstEmptyOrganizations);
                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException(action + " is not supported action");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!HtmlUtil.isEmpty(value)) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.setSection(type, new TextContent(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSection(type, new ListContent(value.trim().split("\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String title = values[i];
                            List<Organization.Position> pos = new ArrayList<>();
                            if (!HtmlUtil.isEmpty(title)) {
                                String pfx = type.name() + i;
                                String[] positions = request.getParameterValues(pfx + "position");
                                String[] fromDates = request.getParameterValues(pfx + "from");
                                String[] toDates = request.getParameterValues(pfx + "to");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < positions.length; j++) {
                                    if (!HtmlUtil.isEmpty(positions[j])) {
                                        pos.add(new Organization.Position(DateUtil.parse(fromDates[j]), DateUtil.parse(toDates[j]), positions[j], descriptions[j]));
                                    }
                                }
                                orgs.add(new Organization(new Link(title, urls[i]), pos));
                            }
                        }
                        r.setSection(type, new ListOrganization(orgs));
                        break;
                }
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }
}