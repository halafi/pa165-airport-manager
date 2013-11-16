<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<html>
    <head>
        <title>Airport Manager</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" type="text/css" href="style.css"/>
    </head>
    <body>
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" var="actionBean"/>

        <p>blabla</p>

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
    </body>
</html>