<%--
  Created by IntelliJ IDEA.
  User: DJ
  Date: 2018-03-26
  Time: 18:42
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
    <div class="page-text">
        <%--Część odpowiedzialna za wyświetlanie treści strony--%>
        <h2>Lekarz został dodany pomyślnie.</h2>
        <br>
        <p>
            Imię: ${addedDoctor.firstName}<br>
            Nazwisko: ${addedDoctor.lastName}<br>
            Specjalizacja: ${addedDoctor.specialization.name}<br>
        </p>
    </div>

</div>
</body>
</html>
