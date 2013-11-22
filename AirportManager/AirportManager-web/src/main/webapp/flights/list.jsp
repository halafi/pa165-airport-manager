<%-- 
    Document   : list
    Created on : 20.11.2013, 13:06:01
    Author     : Samo
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



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
                    <th><f:message key="flight.stewards"/></th>
                    <th></th>
                    <th></th>
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
                        <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean">
                            <c:choose>
                                <c:when test="${fn:length(flight.stewList) gt 0}">
                                    <s:select id="stews" name="flight.stewards">
                                        <c:forEach items="${flight.stewList}" var="steward">
                                            <s:option value="steward">
                                                ${steward.firstName} ${steward.lastName}
                                            </s:option>
                                        </c:forEach>
                                    </s:select>   
                                </c:when>
                                <c:otherwise>
                                    <f:message key="flight.noStewards"/>
                                </c:otherwise>
                            </c:choose>   
                        </s:form>
                            
                        </td>
                        
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="edit">
                                <s:param name="flight.id" value="${flight.id}"/>
                                <img src="../images/edit.png" width="24" height="24"/>
                            </s:link>
                        </td>
                        
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="delete">
                                <s:param name="flight.id" value="${flight.id}"/>
                                <img src="../images/delete.png" width="24" height="24"/>
                            </s:link>
                        </td>
<!--                    </tr>-->
<!--                    <tr>-->
                        
                        
                    </tr>
                </c:forEach>
            </table>

                    <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="create">
                        <f:message key="create"/>
                    </s:link>
                    
                   <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="createTest">
                        <f:message key="createTest"/>
                    </s:link>
                    
                    <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" event="updateTest">
                        <f:message key="updateTest"/>
                    </s:link>
                
        </div>
        
    </s:layout-component>
</s:layout-render>