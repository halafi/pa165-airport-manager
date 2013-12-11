<%-- 
    Document   : list
    Created on : 20.11.2013, 13:06:01
    Author     : Samo
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <div class="text-content">
            <h1><f:message key="flight.list.allflights"/></h1>
            <%@include file="table.jsp"%>
        </div>
        
    </s:layout-component>
</s:layout-render>