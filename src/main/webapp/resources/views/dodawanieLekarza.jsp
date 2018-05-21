<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DJ
  Date: 2018-03-26
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dodawanie lekarza</title>
    <link href="${pageContext.servletContext.contextPath}/resources/css/app.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <div class="page-header">
        <%--Header strony--%>
        <h1>TwojeZdrowie</h1>
    </div>

    <div class="page-menu">
        <jsp:include page="menu.jsp"/>
    </div>

</div>
<div class="page-text">
    <%--Część odpowiedzialna za wyświetlanie treści strony--%>
    <h2>Dodaj nowe konto.</h2>
    <form:form action="dodajLekarza" method="post" modelAttribute="newDoctor">
        <label>Imię:</label><br>
        <form:input type="text" path="firstName"/><br>
        <label>Nazwisko:</label><br>
        <form:input type="text" path="lastName"/><br>
        <label>Specjalizacja:</label><br>
        <select title="specialization" name="specType">
            <c:forEach items="${docSpecEnum}" var="spec">
                <option value="${spec}">${spec}</option>
            </c:forEach>
        </select><br>
        <label>E-mail:</label><br>
        <form:input path="email"/><br>
        <label>Login:</label><br>
        <form:input path="login"/><br>
        <label>Hasło(minimum 7 znaków):</label><br>
        <form:input type="password" path="password"/><br>
        <label>Powtórz hasło:</label><br>
        <form:input type="password" path="matchingPassword"/><br>
        <input type="submit" value="Wyślij"><br>
    </form:form>


</div>
</body>
</html>
