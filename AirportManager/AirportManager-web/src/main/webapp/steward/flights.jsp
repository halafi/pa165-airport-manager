<%-- 
    Document   : flights
    Created on : 21.11.2013, 11:20:00
    Author     : Chorke
--%>

<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="steward.title" >
    <s:layout-component name="body" >
        <%@include file="flightslist.jsp" %>
        <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"
                event="cancel">
            <f:message key="steward.cancel"/>
        </s:link>
    </s:layout-component>
</s:layout-render>
