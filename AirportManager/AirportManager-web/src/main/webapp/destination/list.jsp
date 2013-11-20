<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" var="actionBean"/>

        <div class="text-content">
            <h1><f:message key="destination.list.alldestinations"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="destination.country"/></th>
                    <th><f:message key="destination.city"/></th>
                    <th><f:message key="destination.code"/></th>
                    <th><f:message key="edit"/></th>
                    <th><f:message key="delete"/></th>
                </tr>
                <c:forEach items="${actionBean.destinations}" var="destination">
                    <tr>
                        <td>${destination.id}</td>
                        <td><c:out value="${destination.country}"/></td>
                        <td><c:out value="${destination.city}"/></td>
                        <td><c:out value="${destination.code}"/></td>
                        <td>
                         <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" event="edit">
                             <s:param name="destination.id" value="${destination.id}"/><f:message key="edit"/>
                         </s:link>
                        </td>
                        <td>
                            <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">
                                <s:hidden name="destination.id" value="${destination.id}"/>
                                <s:submit name="delete"><f:message key="delete"/></s:submit>
                            </s:form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">
                <fieldset><legend></legend>
                    <s:submit name="add">Vytvořit novou destinaci</s:submit>
                </fieldset>
            </s:form>
        </div>
        
    </s:layout-component>
</s:layout-render>