<%-- 
    Document   : edit
    Created on : 20.11.2013, 12:21:04
    Author     : Chorke
--%>

<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="${pageContext.request.getParameter('formtitle')}">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                         var="actionBean"/>
        <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
            <s:hidden name="steward.id" />
            <c:set var="mess" 
                   value="${pageContext.request.getParameter('formtitle') == 'steward.edit.title' 
                            ? 'steward.edit.title' : 'steward.create.new'}" />
            <f:message key="${mess}"/>
            <%@include file="form.jsp" %>
            <c:set var="page" value="${pageContext.request.getParameter('formtitle')}" />
            <c:choose>
            <c:when test="${page == 'steward.edit.title'}">
                <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                        event="edit"><f:message key="steward.edit.save"/>
                </s:link>
            </c:when>
            <c:otherwise>
                <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                        event="add"><f:message key="steward.create.add"/>
                </s:link>
            </c:otherwise>
            </c:choose>
        </s:form>
    </s:layout-component>
</s:layout-render>