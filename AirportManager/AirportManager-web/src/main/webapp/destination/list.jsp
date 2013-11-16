<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="destination.list.code">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans" var="actionBean"/>

        <p><f:message key="book.list.allbooks"/></p>

        <table class="basic">
            <tr>
                <th>id</th>
                <th><f:message key="destination.country"/></th>
                <th><f:message key="destination.city"/></th>
                <th><f:message key="destination.code"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${actionBean.destinations}" var="destination">
                <tr>
                    <td>${destination.id}</td>
                    <td><c:out value="${destination.country}"/></td>
                    <td><c:out value="${destination.city}"/></td>
                    <td><c:out value="${destination.code}"/></td>
                    <td><f:message key="Book.Color.${book.color}"/></td>
                    <td>
                     <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationActionBean" event="edit"><s:param name="destination.id" value="${book.id}"/>edit</s:link>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationActionBean">
                            <s:hidden name="destination.id" value="${destination.id}"/>
                            <s:submit name="delete"><f:message key="destination.list.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationActionBean">
            <fieldset><legend><f:message key="destination.list.newdestination"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add">Vytvořit novou destinaci</s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>