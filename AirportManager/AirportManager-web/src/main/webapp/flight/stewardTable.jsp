<%-- 
    Document   : stewardTable
    Created on : 22.11.2013, 14:45:38
    Author     : Samo
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="text-content" >
            <h1><f:message key="steward.title"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="steward.firstname"/></th>
                    <th><f:message key="steward.lastname"/></th>
                    <sec:authorize url="/admin">
                        <th><f:message key="flight.removeSteward"/></th>
                    </sec:authorize>
                </tr>
                <c:forEach items="${actionBean.flight.stewList}" var="steward">
                    <tr>
                        <th><c:out value="${steward.id}"/></th>
                        <th><c:out value="${steward.firstName}"/></th>
                        <th><c:out value="${steward.lastName}"/></th>
                        <sec:authorize url="/admin">
                            <th><s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean">
                                    <s:submit name="flights">
                                        <s:param name="steward.id" value="${steward.id}"/>
                                        <s:param name="event" value="removeSteward"/>
                                        <f:message key="steward.flights"/>
                                    </s:submit>
                                </s:form>
                            </th>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </table>
