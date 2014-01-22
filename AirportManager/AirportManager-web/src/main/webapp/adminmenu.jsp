<%-- 
    Document   : adminmenu
    Created on : 19.1.2014, 16:57:46
    Author     : Samo
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
    
    <div id="main">
        <div id="navigation">
            <ul id="menu">
                <li class="navlink"><f:message key="airplane"/>
                    <ul class="submenu">

                        <sec:authorize ifAnyGranted="ROLE_ADMIN">
                        <s:link href="/airplane/create.jsp"><li class="navlink"><f:message key="create"/></li></s:link>
                        </sec:authorize>
                        <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean"><li class="navlink"><f:message key="list"/></li></s:link>
                    </ul>
                </li>
                <li class="navlink"><f:message key="destination"/>
                    <ul class="submenu">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">

                        <s:link href="/destination/create.jsp"><li class="navlink"><f:message key="create"/></li></s:link>
                    </sec:authorize>
                        <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean"><li class="navlink"><f:message key="list"/></li></s:link>
                    </ul>
                </li>
                <li class="navlink"><f:message key="flight"/>
                    <ul class="submenu">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="navlink"><s:link href="/flight/create.jsp"><f:message key="create"/></s:link></li>
                        </sec:authorize>
                        <li class="navlink"><s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean"><f:message key="list"/></s:link></li>             
                    </ul>
                </li>
                <li class="navlink"><f:message key="steward"/>
                    <ul class="submenu">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <s:link href="/steward/edit.jsp"><li class="navlink"><f:message key="create"/><s:param name="createnew" value="true"/></li></s:link>
                        </sec:authorize>
                        <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
                            <li class="navlink">
                                <f:message key="list"/>
                            </li>
                        </s:link>
                    </ul>
                </li>
            </ul>
        </div>
        <div id="wrapper">
            <s:messages/>
            <s:errors/>
            <s:layout-component name="body"/>
        </div>
    </div>