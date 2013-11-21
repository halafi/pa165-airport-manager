<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="steward.flights.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean" 
                         var="actionBean"/>
        <div class="text-content">
            <h1><f:message key="steward.flights.title"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="flight.departureTime"/></th>
                    <th><f:message key="flight.arrivalTime"/></th>
                    <th><f:message key="flight.origin"/></th>
                    <th><f:message key="flight.target"/></th>
                    <th><f:message key="flight.airplane"/></th>
                    <th><f:message key="steward.flights.take"/></th>
                </tr>
                <c:forEach items="${actionBean.flights}" var="flight">
                    <tr>
                        <td>${flight.id}</td>
                        <td><c:out value="${flight.departureTime}"/></td>
                        <td><c:out value="${flight.arrivalTime}"/></td>
                        <td><c:out value="${flight.origin.city}"/></td>
                        <td><c:out value="${flight.target.city}"/></td>
                        <td><c:out value="${flight.airplaneTO.name}"/></td>
                        <td>
                            <s:submit name="removeflight">
                                <s:param name="flight.id" value="${flight.id}"/>
                                <f:message key="steward.flights.take"/>
                            </s:submit>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                    event="cancel">
                <f:message key="steward.cancel"/>
            </s:link>
        </div>
        
    </s:layout-component>
</s:layout-render>