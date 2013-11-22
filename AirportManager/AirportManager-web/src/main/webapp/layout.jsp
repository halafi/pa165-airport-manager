<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
    <!DOCTYPE html>
    <html lang="${pageContext.request.locale}">
        <head>
            <title><f:message key="${titlekey}"/></title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
            <meta equiv="Content-Type" content="text/html; charset=UTF-8">
            <%--<s:layout-component name="header"/>--%>
        </head>
        <body>
            <div id="header">
                <div id="logo">
                    <s:link href="/index.jsp"><img src="${pageContext.request.contextPath}/images/logo.png" height="70" width="600"/></s:link>
                </div>
                <div id="logout">
                    <a href="#">logout</a><br/>
                    logged in as Admin
                </div>
            </div>
                <!--left navigation panel-->
            <div id="main">
                <div id="navigation">
                    <div class="user">
                        <ul id="navlist">
                            <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean"><f:message key="steward.find"/></s:link></li>
                            <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean"><f:message key="airplane.find"/></s:link></li>
                            <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean"><f:message key="flight.find"/></s:link></a></li>
                            <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">find destinations</s:link></li>
                        </ul>
                    </div>
                    <div class="admin">
                        <ul id="navlist">
                            <li><s:link class="navlink" href="/steward/edit.jsp">
                                    <f:message key="steward.create.add"/>
                                    <s:param name="createnew" value="true"/>
                                </s:link>
                            </li>
                            <li><s:link class="navlink" href="/airplane/create.jsp"><f:message key="airplane.create"/></s:link></li>
                            <li><s:link class="navlink" href="/flights/create.jsp"><f:message key="flight.create"/></s:link></li>
                            <li><s:link class="navlink" href="/destination/create.jsp"><f:message key="destination.create"/></s:link></li>
                        </ul>
                    </div>
                </div>
                <div id="wrapper">
                    <s:messages/>
                    <s:errors/>
                    <s:layout-component name="body"/>
                </div>
            </div>
            <div id="footer">
                <f:message key="page.authors"/>: Bc. Juraj Duráni (359185), Bc. Matúš Makový (374426),
                Bc. Samuel Peťovský (374591), Filip Halas (374137); <f:message key="page.fimu"/>;
                e-mail: (učo)@mail.muni.cz
            </div>
        </body>
    </html>
</s:layout-definition>