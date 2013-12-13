<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean" var="actionBean"/>

        <div class="text-content">
            <h1><f:message key="destination.list.alldestinations"/></h1>
            <%@include file="destinationTable.jsp"%>
            <h1><f:message key="destination.leavingFlights"/> ${actionBean.destination.city}</h1>
            <%@include file="flightTable.jsp"%>
        </div>
        
    </s:layout-component>
</s:layout-render>


