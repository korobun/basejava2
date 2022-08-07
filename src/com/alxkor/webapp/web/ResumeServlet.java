package com.alxkor.webapp.web;

import com.alxkor.webapp.Config;
import com.alxkor.webapp.exception.StorageException;
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e);
        }

        Writer writer = response.getWriter();
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <style></style>\n" +
                "    <title>Список резюме</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>Список резюме</h3>\n" +
                "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                "    <tr>\n" +
                "        <th>uuid</th>\n" +
                "        <th>full name</th>\n" +
                "    </tr>");

        for (Resume r : dataBase.getAllSorted()) {
            writer.write("<tr>\n" +
                    "        <td>" + r.getUuid() + "</td>\n" +
                    "        <td>" + r.getFullName() + "</td>\n" +
                    "    </tr>");
        }

        writer.write("</table>\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest quest, HttpServletResponse response) throws ServletException, IOException {

    }
}
