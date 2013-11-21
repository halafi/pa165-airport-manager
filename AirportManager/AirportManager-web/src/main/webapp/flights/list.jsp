<%-- 
    Document   : list
    Created on : 20.11.2013, 13:06:01
    Author     : Samo
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" var="actionBean"/>

        <div class="text-content">
            <h1><f:message key="flight.list.allflights"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="flight.departureTime"/></th>
                    <th><f:message key="flight.arrivalTime"/></th>
                    <th><f:message key="flight.origin"/></th>
                    <th><f:message key="flight.target"/></th>
                    <th><f:message key="flight.airplane"/></th>
                    <th><f:message key="stewards"/></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.flights}" var="flights">
                    <tr>
                        <td>${flight.id}</td>
                        <td><c:out value="${flight.departureTime}"/></td>
                        <td><c:out value="${flight.arrivalTime}"/></td>
                        <td><c:out value="${flight.origin.city}"/></td>
                        <td><c:out value="${flight.target.city}"/></td>
                        <td><c:out value="${flight.airplane.name}"/></td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="edit">
                                <s:param name="flight.id" value="${flight.id}"/><f:message key="flight.stewards"/> <%--<f:message key="edit"/>--%>
                            </s:link>
                        </td>
                        <td>
                         <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="edit">
                             <s:param name="flight.id" value="${flight.id}"/>edit <%--<f:message key="edit"/>--%>
                         </s:link>
                        </td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="delete">
                             <s:param name="flight.id" value="${flight.id}"/>delete <%--<f:message key="edit"/>--%>
                         </s:link>
                        </td>
                    </tr>
                </c:forEach>
            </table>

                    <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="create">
                        <f:message key="create"/>
                    </s:link>
                    
                   <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="createTest">
                        <f:message key="createTest"/>
                    </s:link>
                
        </div>
        
    </s:layout-component>
</s:layout-render>