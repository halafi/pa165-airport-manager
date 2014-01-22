
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="registration.title">
    <s:layout-component name="body">
        <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.userservice.UserActionBean">
            <table>
                <tr>
                    <th><s:label for="un" name="username"/></th>
                    <th><s:text id="un" name="username"/></th>
                </tr>
                <tr>
                    <th><s:label for="psw" name="password"/></th>
                    <th><s:password id="psw" name="password"/></th>
                </tr>
                <tr>
                    <th><s:label for="pswc" name="passwordconf"/></th>
                    <th><s:password id="pswc" name="passwordconf"/></th>
                </tr>
            </table>
            <s:submit name="register">Ok</s:submit>
            <s:submit name="cancel"><f:message key="steward.cancel"/></s:submit>
        </s:form>
    
    </s:layout-component>
</s:layout-render>
