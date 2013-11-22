<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean" 
                 var="actionBean"/>
<div class="text-content">
    <h1><f:message key="steward.flights.title"/></h1>
    <c:set var="add" value="${pageContext.request.getParameter('add')}" />
    <table class="basic">
        <tr>
            <th><f:message key="id"/></th>
            <th><f:message key="flight.departureTime"/></th>
            <th><f:message key="flight.arrivalTime"/></th>
            <th><f:message key="flight.origin"/></th>
            <th><f:message key="flight.target"/></th>
            <th><f:message key="flight.airplane"/></th>
            <th>
                <c:choose>
                    <c:when test="${add == true}">
                        <f:message key="steward.flights.add"/>
                    </c:when>
                    <c:otherwise>
                        <f:message key="steward.flights.take"/>
                    </c:otherwise> 
                </c:choose>
            </th>
        </tr>
        <c:forEach items="${actionBean.flights}" var="flight">
            <tr>
                <td>${flight.id}</td>
                <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${flight.departureTime}"/></td>
                <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${flight.arrivalTime}"/></td>
                <td><c:out value="${flight.origin.city}"/></td>
                <td><c:out value="${flight.target.city}"/></td>
                <td><c:out value="${flight.airplaneTO.name}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${add != true}">
                            <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
                                <s:submit name="removeflight">
                                    <s:param name="flight.id" value="${flight.id}"/>
                                    <s:param name="steward.id" value="${pageContext.request.getParameter('steward.id')}"/>
                                    <s:param name="createnew" value="${pageContext.request.getParameter('createnew')}"/>
                                    <s:param name="event" value="${pageContext.request.getParameter('event')}"/>
                                    <f:message key="steward.flights.take"/>
                                </s:submit>
                            </s:form>
                        </c:when>
                        <c:otherwise>
                            <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
                                <s:submit name="addflighttolist">
                                    <s:param name="flight.id" value="${flight.id}"/>
                                    <s:param name="steward.id" value="${pageContext.request.getParameter('steward.id')}"/>
                                    <s:param name="createnew" value="${pageContext.request.getParameter('createnew')}"/>
                                    <f:message key="steward.flights.add"/>
                                </s:submit>
                            </s:form>
                        </c:otherwise> 
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>

    <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
        <c:if test="${add != true}">
            <s:submit name="addflight">
                <s:param name="createnew" value="${pageContext.request.getParameter('createnew')}"/>
                <s:param name="steward.id" value="${pageContext.request.getParameter('steward.id')}"/>
                <f:message key="steward.flights.add"/>
            </s:submit>
        </c:if>
        <s:submit name="cancelsteward">Ok</s:submit>
    </s:form>

</div>