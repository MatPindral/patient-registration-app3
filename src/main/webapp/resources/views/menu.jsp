<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: barto
  Date: 21.03.2018
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<ul class="m">
    <li class="m"><a href="${pageContext.servletContext.contextPath}/main">Strona główna</a></li>


    <sec:authorize access="isAnonymous()">
        <li class="m"><a href="${pageContext.servletContext.contextPath}/nowyUzytkownik">Załóż konto</a></li>
        <li class="m"><a href="${pageContext.servletContext.contextPath}/login">Logowanie</a></li>
    </sec:authorize>

    <li class="m"><a href="${pageContext.servletContext.contextPath}/rejestracja">Rejestracja Wizyty</a></li>

    <sec:authorize access="hasRole('PATIENT')">
        <li class="m"><a href="${pageContext.servletContext.contextPath}/wizytyPacjenta">Historia Wizyt</a></li>
    </sec:authorize>

    <li class="m"><a href="${pageContext.servletContext.contextPath}/doktorzy">Lekarze</a></li>

    <sec:authorize access="isAuthenticated()">
        <li class="m"><a href="<c:url value="/logout" />">Wyloguj</a></li>
    </sec:authorize>


    <sec:authorize access="hasRole('ADMIN')">
        <li class="m"><a href="${pageContext.servletContext.contextPath}/admin/dodajLekarza">Dodaj lekarza</a></li>
    </sec:authorize>


</ul>
