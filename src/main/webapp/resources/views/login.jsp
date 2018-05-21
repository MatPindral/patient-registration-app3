<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: barto
  Date: 26.03.2018
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logowanie</title>
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
        <c:url value="/login" var="loginUrl"/>
        <form action="${loginUrl}" method="post">
            <c:if test="${param.error != null}">
                <h2>
                    Nieprawidłowy Login lub Hasło!!<br>
                    Spróbuj ponownie..
                </h2>
            </c:if>
            <c:if test="${param.logout != null}">

                <h2>Wylogowanie przebiegło pomyślnie</h2>

            </c:if>
            <br><br>
            <p>
                <label for="username">Login</label>
                <input type="text" id="username" name="username"/>
            </p>
            <br>
            <p>
                <label for="password">Hasło</label>
                <input type="password" id="password" name="password"/>
            </p>

            <input type="submit" value="Zaloguj">
        </form>
    </div>


</div>
</body>
</html>
