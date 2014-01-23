<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:layout-definition>
    <!DOCTYPE html>
    <html lang="${pageContext.request.locale}">
        <head>
            <title><f:message key="${titlekey}"/></title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
            <meta equiv="Content-Type" content="text/html; charset=UTF-8">
            <s:layout-component name="header"/>
        </head>
        
        <body>
            <div id="header">
                <div id="logo">
                    <s:link href="/index.jsp"><img src="${pageContext.request.contextPath}/images/logo.png" height="70" width="600"/></s:link>
                    </div>
                    <div id="logout">
                    <sec:authorize access="isAuthenticated()">
                        Logged in as <%= request.getUserPrincipal().getName() %>. 
                        <a href="<c:url value="/j_spring_security_logout" />">logout</a>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <a href="${pageContext.request.contextPath}/login.jsp">login</a>
                    </sec:authorize>
                    </div>
                </div>
        
            <div id="main">
                <div id="navigation">
                    <sec:authorize url="/user">
                        <ul id="menu">
                            <li class="navlink"><f:message key="airplane"/>
                                <ul class="submenu">
                                    <sec:authorize url="/admin">
                                        <s:link href="/airplane/create.jsp">
                                            <li class="navlink"><f:message key="create"/></li>
                                        </s:link>
                                    </sec:authorize>
                                    <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean">
                                        <li class="navlink"><f:message key="list"/></li>
                                    </s:link>
                                </ul>
                            </li>
                            <li class="navlink"><f:message key="destination"/>
                                <ul class="submenu">
                                <sec:authorize url="/admin">
                                    <s:link href="/destination/create.jsp">
                                        <li class="navlink"><f:message key="create"/></li>
                                    </s:link>
                                </sec:authorize>
                                <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">
                                    <li class="navlink"><f:message key="list"/></li>
                                </s:link>
                                </ul>
                            </li>
                            <li class="navlink"><f:message key="flight"/>
                                <ul class="submenu">
                                    <sec:authorize url="/admin">
                                        <s:link href="/flight/create.jsp">
                                            <li class="navlink"><f:message key="create"/></li>
                                        </s:link>
                                    </sec:authorize>
                                    <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean">
                                        <li class="navlink"><f:message key="list"/></li>
                                    </s:link>
                                </ul>
                            </li>
                            <li class="navlink"><f:message key="steward"/>
                                <ul class="submenu">
                                    <sec:authorize url="/admin">
                                        <s:link href="/steward/edit.jsp">
                                            <li class="navlink"><f:message key="create"/>
                                                <s:param name="createnew" value="true"/>
                                            </li>
                                        </s:link>
                                    </sec:authorize>
                                    <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">
                                        <li class="navlink"><f:message key="list"/></li>
                                    </s:link>
                                </ul>
                            </li>
                        </ul>
                    </sec:authorize>
                </div>
                
                
                <div id="wrapper">
                    <s:messages/>
                    <s:errors/>
                    <s:layout-component name="body"/>
                 </div>
        
                <div id="footer">
                    <f:message key="page.authors"/>: Bc. Juraj Duráni (359185), Bc. Matúš Makový (374426),
                    Bc. Samuel Peťovský (374591), Filip Halas (374137); <f:message key="page.fimu"/>;
                    e-mail: (učo)@mail.muni.cz
                </div>
                
            </div>
        </body>
    </html>
</s:layout-definition>
