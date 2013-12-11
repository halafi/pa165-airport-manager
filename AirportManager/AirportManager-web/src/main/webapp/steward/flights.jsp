<%-- 
    Document   : flights
    Created on : 21.11.2013, 11:20:00
    Author     : Juraj Duráni
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="steward.title" >
    <s:layout-component name="body" >
        <%@include file="flightslist.jsp" %>
    </s:layout-component>
</s:layout-render>
