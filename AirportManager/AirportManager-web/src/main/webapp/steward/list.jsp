<%-- 
    Document   : list
    Created on : 20.11.2013, 12:21:21
    Author     : Juraj Duráni
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="steward.title" >
    <s:layout-component name="body" >
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean" 
                         var="actionBean" />
        <div class="text-content" >
            <h1><f:message key="steward.title"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="steward.firstname"/></th>
                    <th><f:message key="steward.lastname"/></th>
                    <th colspan="3"><f:message key="operations"/></th>
                </tr>
                <c:forEach items="${actionBean.stewards}" var="steward">
                    <tr>
                        <th><c:out value="${steward.id}"/></th>
                        <th><c:out value="${steward.firstName}"/></th>
                        <th><c:out value="${steward.lastName}"/></th>
                        <th>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean" 
                                    event="flights">
                                <s:param name="steward.id" value="${steward.id}"/>
                                <s:param name="event" value="flights"/>
                                <f:message key="steward.flights"/>
                            </s:link>
                        </th>
                        <th><s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                                    event="editsteward">
                                    <s:param name="steward.id" value="${steward.id}"/>
                                    <img class="icon" src="${pageContext.request.contextPath}/images/edit.png"/>
                                
                            </s:link></th> 
                        <th><s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                                    event="deletesteward">
                                    <s:param name="steward.id" value="${steward.id}"/>
                                    <img class="icon" src="${pageContext.request.contextPath}/images/delete.png"/>
                                
                            </s:link>
                        </th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </s:layout-component>
</s:layout-render>