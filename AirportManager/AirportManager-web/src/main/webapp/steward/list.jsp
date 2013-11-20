<%-- 
    Document   : list
    Created on : 20.11.2013, 12:21:21
    Author     : Chorke
--%>

<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
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
                    <th><f:message key="steward.edit"/></th>
                    <th><f:message key="steward.delete"/></th>
                    <th><f:message key="steward.flights"/></th>
                </tr>
                <c:forEach items="${actionBean.stewards}" var="stew">
                    <tr>
                        <th><c:out value="${stew.id}"/></th>
                        <th><c:out value="${stew.firstName}"/></th>
                        <th><c:out value="${stew.lastName}"/></th>
                        <th><s:link href="/steward/edit.jsp">
                                <s:param name="formtitle" value="steward.edit.title"/>
                                <f:message key="steward.edit"/></s:link></th>
                        <%--<th><s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                                event="edit"><s:param name="steward.id" value="${stew.id}"/>
<f:message key="steward.edit"/></s:link></th> --%>
                        <th><s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                                event="delete"><s:param name="steward.id" value="${stew.id}"/>
                                <f:message key="steward.delete"/></s:link></th>
                    </tr>
                </c:forEach>
            </table>
                    <s:link href="/steward/edit.jsp">
                        <s:param name="formtitle" value="steward.create.title" />
                        <f:message key="steward.create.new"/>
                    </s:link>
        </div>
    </s:layout-component>
</s:layout-render>
        
