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
            <%@include file="table.jsp"%>
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