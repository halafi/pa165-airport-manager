<%-- 
    Document   : edit
    Created on : 20.11.2013, 13:06:20
    Author     : Samo
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <div class="text-content">
            <fieldset><legend><f:message key="edit"/></legend>
                <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean">
                    <s:hidden name="flight.id"/>
                    <%@include file="form.jsp"%>
                    <s:submit name="saveflight">
                        <f:message key="save"/>
                    </s:submit>
                    <s:submit name="cancelflight">
                        <f:message key="cancel"/>
                    </s:submit>
                </s:form>
            </fieldset>
        </div>

    </s:layout-component>
</s:layout-render>