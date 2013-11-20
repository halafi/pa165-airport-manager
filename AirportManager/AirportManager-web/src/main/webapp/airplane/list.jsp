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
                    <th><f:message key="edit"/></th>
                    <th><f:message key="delete"/></th>
                </tr>
                <c:forEach items="${actionBean.airplanes}" var="airplane">
                    <tr>
                        <td>${airplane.id}</td>
                        <td><c:out value="${airplane.capacity}"/></td>
                        <td><c:out value="${airplane.name}"/></td>
                        <td><c:out value="${airplane.type}"/></td>
                        <td>
                         <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean" event="edit">
                             <s:param name="airplane.id" value="${airplane.id}"/>edit
                         </s:link>
                        </td>
                        <td>
                            <s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean" action="airplane/add">
                                <s:hidden name="airplane.id" value="${airplane.id}"/>
                                <s:submit name="delete">delete</s:submit>
                            </s:form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

<s:link href="/airplane/create.jsp">create</s:link>
        </div>
        
    </s:layout-component>
</s:layout-render>