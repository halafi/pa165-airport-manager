<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean" var="actionBean"/>
        
        <div class="text-content">
            <s:form beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean">
                <s:hidden name="destination.id"/>
                <fieldset>
                    <legend><f:message key="edit"/></legend>
                    <%@include file="form.jsp"%>
                    <s:submit name="save">
                        <f:message key="save"/>
                    </s:submit>
                    <s:submit name="cancel">
                        <f:message key="cancel"/>
                    </s:submit>
                </fieldset>
            </s:form>
        </div>

    </s:layout-component>
</s:layout-render>