<%@ page import="com.alxkor.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alxkor.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"></jsp:include>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>e-mail</th>
        </tr>

        <%
            for (Resume r : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><a href="resume?uuid=<%=r.getUuid()%>"><%=r.getFullName()%></a></td>
            <td><%=r.getContacts().get(ContactType.EMAIL)%></td>
        </tr>
        <%
            }
        %>
    </table>
</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>
