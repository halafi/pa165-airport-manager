<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp"  titlekey="airplane.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean" var="actionBean"/>

        <div class="text-content">
            <h1><f:message key="airplane.list.allairplanes"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="airplane.capacity"/></th>
                    <th><f:message key="airplane.name"/></th>
                    <th><f:message key="airplane.type"/></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.airplanes}" var="airplane">
                    <tr>
                        <td>${airplane.id}</td>
                        <td><c:out value="${airplane.capacity}"/></td>
                        <td><c:out value="${airplane.name}"/></td>
                        <td><c:out value="${airplane.type}"/></td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean" event="edit">
                                <s:param name="airplane.id" value="${airplane.id}"/>
                                <f:message key="edit"/>
                            </s:link>
                        </td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean" event="add">
                                <s:param name="airplane.id" value="${airplane.id}"/>
                                <f:message key="delete"/>
                                </s:link>
                            </td>
                        </tr>
                </c:forEach>
            </table>      
        </div>

    </s:layout-component>
</s:layout-render>