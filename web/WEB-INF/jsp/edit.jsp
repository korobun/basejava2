<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alxkor.webapp.model.ContactType" %>
<%@ page import="com.alxkor.webapp.model.SectionType" %>
<%@ page import="com.alxkor.webapp.util.DateUtil" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="com.alxkor.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section>
    <h2>${resume.fullName}</h2>
    <form action="resume" method="post" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd>
                <input type="text" name="fullName" size=50 value="${resume.fullName}">
            </dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="${ContactType.values()}">
            <jsp:useBean id="contactType" type="com.alxkor.webapp.model.ContactType"/>
            <dl>
                <dt>${contactType.title}:</dt>
                <dd><input type="text" name="${contactType}" size=30 value="${resume.getContact(contactType)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <jsp:useBean id="sectionType" type="com.alxkor.webapp.model.SectionType"/>
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
<%--            <jsp:useBean id="section" type="com.alxkor.webapp.model.Section"/>--%>
            <h3>${sectionType.title}:</h3>
            <c:choose>
                <c:when test="${sectionType eq 'OBJECTIVE' || sectionType eq 'PERSONAL'}">
                    <c:set var="textContent" value="${section}"/>
                    <jsp:useBean id="textContent" class="com.alxkor.webapp.model.TextContent"/>
                    <c:if test="${sectionType eq 'OBJECTIVE'}">
                        <input type="text" name="${sectionType.name()}" size=30
                               value="<%=textContent.getContent()%>">
                    </c:if>
                    <c:if test="${sectionType eq 'PERSONAL'}">
                        <textarea name="${sectionType.name()}" cols="75"
                                  rows="5"><%=textContent.getContent()%>
                        </textarea>
                    </c:if>
                </c:when>
                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                    <c:set var="listContent" value="${section}"/>
                    <jsp:useBean id="listContent" class="com.alxkor.webapp.model.ListContent"/>
                    <textarea name="${sectionType.name()}" cols="75" rows="5">
                                <%=String.join("\n", listContent.getItems())%>
                            </textarea>
                </c:when>
                <c:when test="${sectionType eq'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <c:set var="listOrg" value="${section}"/>
                    <jsp:useBean id="listOrg" class="com.alxkor.webapp.model.ListOrganization"/>
                    <c:forEach items="<%=listOrg.getItems()%>" var="organization"
                               varStatus="counter">
                        <jsp:useBean id="organization" type="com.alxkor.webapp.model.Organization"/>
                        <dl>
                            <dt>Наименование организации</dt>
                            <dd><input type="text" name="${sectionType.name()}" size="75"
                                       value="${organization.homepage.title}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт организации</dt>
                            <dd><input type="text" name="${sectionType.name()}url" size="75"
                                       value="${organization.homepage.url}"></dd>
                        </dl>
                        <div style="margin-left: 20px">
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position" type="com.alxkor.webapp.model.Organization.Position"/>
                                <dl>
                                    <dt>С:</dt>
                                    <dd><input type="text" name="${sectionType.name()}${counter.index}from" size="75"
                                               value="${DateUtil.format(position.from)}"
                                               placeholder="MM/yyyy"></dd>
                                </dl>
                                <dl>
                                    <dt>По:</dt>
                                    <dd><input type="text" name="${sectionType.name()}${counter.index}to" size="75"
                                               value="${DateUtil.format(position.to)}"
                                               placeholder="MM/yyyy"></dd>
                                </dl>
                                <dl>
                                    <dt><b>Должность</b></dt>
                                    <dd><input type="text" name="${sectionType.name()}${counter.index}position"
                                               size="75" value="${position.position}"></dd>
                                </dl>
                                m
                                <dl>
                                    <dt>Описание</dt>
                                    <dd><textarea name="${sectionType.name()}${counter.index}description" cols="75"
                                                  rows="5">${position.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>