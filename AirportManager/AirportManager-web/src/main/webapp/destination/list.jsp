<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" var="actionBean"/>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">

        <div class="text-content">
            <h1>List destinations</h1>
            <table class="basic">
                <tr>
                    <th>id</th>
                    <th>country</th>
                    <th>city</th>
                    <th>code</th>
                </tr>
                <c:forEach items="${actionBean.destinations}" var="destination">
                    <tr>
                        <td>${destination.id}</td>
                        <td><c:out value="${destination.country}"/></td>
                        <td><c:out value="${destination.city}"/></td>
                        <td><c:out value="${destination.code}"/></td>
                        <td>
                         <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" event="edit"><s:param name="destination.id" value="${book.id}"/>edit</s:link>
                        </td>
                        <td>
                            <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">
                                <s:hidden name="destination.id" value="${destination.id}"/>
                                <s:submit name="delete"></s:submit>
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