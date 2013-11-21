<%-- 
    Document   : edit
    Created on : 20.11.2013, 12:21:04
    Author     : Chorke
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="${pageContext.request.getParameter('formtitle')}">
    <s:layout-component name="body">
        <div class="text-content">
            <c:set var="mess" 
                   value="${pageContext.request.getParameter('formtitle') == 'steward.edit.title' 
                            ? 'steward.edit.title' : 'steward.create.new'}" />
            <c:set var="page" value="${pageContext.request.getParameter('formtitle')}" />
            <fieldset><legend><f:message key="${mess}"/></legend>
                <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
                    <s:hidden name="steward.id" />
                    <%@include file="form.jsp" %>
                    <c:choose>
                        <c:when test="${page == 'steward.edit.title'}">
                            <s:submit name="savesteward"><f:message key="steward.edit.save"/>
                            </s:submit>
                        </c:when>
                        <c:otherwise>
                            <s:submit name="addsteward"><f:message key="steward.create.add"/>
                            </s:submit>
                        </c:otherwise>
                    </c:choose>
                    <s:submit name="cancelsteward"><f:message key="steward.cancel"/>
                    </s:submit>
                </s:form>
            </fieldset>
        </div>
        <c:if test="${page == 'steward.edit.title'}">
            <%@include file="flightslist.jsp" %>
        </c:if>
    </s:layout-component>
</s:layout-render>