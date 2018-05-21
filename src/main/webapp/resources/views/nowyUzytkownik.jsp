<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Andrzej
  Date: 3/21/2018
  Time: 7:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rejestracja nowego uzytkownika</title>
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
    <div class="page-text">
        <%--Część odpowiedzialna za wyświetlanie treści strony--%>
        <h2>Dodaj nowe konto.</h2>
        <form:form action="nowyUzytkownik/zarejestruj" method="post" modelAttribute="newUser">
            <label>Imię:</label><br>
            <form:input type="text" path="firstName"/><br>
            <label>Nazwisko:</label><br>
            <form:input type="text" path="lastName"/><br>
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


</div>
</body>
</html>
