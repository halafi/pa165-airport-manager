<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="airplane.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.rest.client.AirplaneClientActionBean" var="actionBean"/>

        <div class="text-content">

            <h1><f:message key="airplane.list.allairplanes"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="airplane.capacity"/></th>
                    <th><f:message key="airplane.name"/></th>
                    <th><f:message key="airplane.type"/></th>
                    <th colspan="3"><f:message key="operations"/></th>
                </tr>
                <c:forEach items="${actionBean.airplanes}" var="airplane">
                    <tr>
                        <td>${airplane.id}</td>
                        <td><c:out value="${airplane.capacity}"/></td>
                        <td><c:out value="${airplane.name}"/></td>
                        <td><c:out value="${airplane.type}"/></td>
                        <td><s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.AirplaneClientActionBean" event="flightsOfPlane">
                                <s:param name="airplane.id" value="${airplane.id}"/>
                                <f:message key="listFlights"/>
                            </s:link></td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.AirplaneClientActionBean" event="edit">
                                <s:param name="airplane.id" value="${airplane.id}"/>
                                <img class="icon" src="${pageContext.request.contextPath}/images/edit.png"/>
                            </s:link>
                        </td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.AirplaneClientActionBean" event="delete">
                                <s:param name="airplane.id" value="${airplane.id}"/>
                                <img class="icon" src="${pageContext.request.contextPath}/images/delete.png"/>
                            </s:link>
                        </td>
                    </tr>
                </c:forEach>
            </table>      

            <h1><f:message key="flights.of.airplane"/> ${actionBean.airplane.id}</h1>
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
        </div>

    </s:layout-component>
</s:layout-render>