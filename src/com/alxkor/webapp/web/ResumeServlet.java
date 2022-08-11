package com.alxkor.webapp.web;

import com.alxkor.webapp.Config;
import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.ContactType;
import com.alxkor.webapp.model.Resume;
import com.alxkor.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage dataBase;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dataBase = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resumes", dataBase.getAllSorted());
        request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest quest, HttpServletResponse response) throws ServletException, IOException {

    }
}
