<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alxkor.webapp.model.TextContent" %>
<%@ page import="com.alxkor.webapp.model.ListContent" %>
<%@ page import="com.alxkor.webapp.model.ListOrganization" %>
<%@ page import="com.alxkor.webapp.util.HtmlUtil" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="com.alxkor.webapp.model.Resume" scope="request"/>
    <title>${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <h3>Контакты:</h3>
    <c:forEach items="${resume.contacts}" var="contactEntry">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.alxkor.webapp.model.ContactType, java.lang.String>"/>
        <p>${contactEntry.key.toHtml(contactEntry.value)}</p>
    </c:forEach>
</section>
<hr>
<section>
    <table cellpadding="2">
        <c:forEach items="${resume.sections}" var="sectionEntry">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.alxkor.webapp.model.SectionType, com.alxkor.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="type" type="com.alxkor.webapp.model.SectionType"/>
            <jsp:useBean id="section" type="com.alxkor.webapp.model.Section"/>
            <tr>
                <td colspan="2"><h2>${type.title}</h2>
                </td>
            </tr>
            <c:choose>
                <c:when test="${type eq 'PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((TextContent) section).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type eq 'OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((TextContent) section).getContent()%>
                            </h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type eq 'ACHIEVEMENT' || type eq 'QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListContent) section).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type eq 'EXPERIENCE' || type eq 'EDUCATION'}">
                    <c:forEach var="organization" items="<%=((ListOrganization) section).getItems()%>">
                        <jsp:useBean id="organization" type="com.alxkor.webapp.model.Organization"/>
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty organization.homepage.url}">
                                        <h3>${organization.homepage.title}</h3>
                                    </c:when>
                                    <c:otherwise><h3><a
                                            href="${organization.homepage.url}">${organization.homepage.title}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach items="${organization.positions}" var="position">
                            <tr>
                                <td width="15%" style="vertical-align: top">${HtmlUtil.formatDates(position)}</td>
                                <td style="vertical-align: top">
                                    <div><b>${position.position}</b></div>
                                    <div>${position.description}</div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>