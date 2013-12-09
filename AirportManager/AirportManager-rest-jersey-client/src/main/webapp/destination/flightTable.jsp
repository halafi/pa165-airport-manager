<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<table class="basic">
    <tr>
        <th><f:message key="id"/></th>
        <th><f:message key="flight.departureTime"/></th>
        <th><f:message key="flight.arrivalTime"/></th>
        <th><f:message key="flight.origin"/></th>
        <th><f:message key="flight.target"/></th>
        <th><f:message key="flight.airplane"/></th>
    </tr>
    <c:forEach items="${actionBean.flights}" var="flight">
        <tr>
            <td>${flight.id}</td>
            <td><c:out value="${flight.departureTime}"/></td>
            <td><c:out value="${flight.arrivalTime}"/></td>
            <td><c:out value="${flight.origin.city}"/></td>
            <td><c:out value="${flight.target.city}"/></td>
            <td><c:out value="${flight.airplaneTO.name}"/></td>
        </tr>
    </c:forEach>
</table>