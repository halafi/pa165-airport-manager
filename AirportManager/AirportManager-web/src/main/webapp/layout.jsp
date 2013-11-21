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
   <!--left navigation panel-->
        <div id="navigation">
            <div class="user">
                <ul id="navlist">
                <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.StewardsActionBean">find steward</s:link></li>
                <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.AirplaneActionBean">find airplane</s:link></li>
                <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean">find flight</s:link></a></li>
                <li><s:link class="navlink" beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">find destinations</s:link></li>
                </ul>
            </div>
            <div class="admin">
                <ul id="navlist">
                <li><s:link class="navlink" href="/steward/edit.jsp?formtitle=steward.create.new"><f:message key="steward.create.add"/></s:link><li/>
                <li><s:link class="navlink" href="/airplane/create.jsp"><f:message key="airplane.create"/></s:link><li/>
                <li><s:link class="navlink" href="/flights/create.jsp">create flight</s:link><li/>
                <li><s:link class="navlink" href="/destination/create.jsp"><f:message key="destination.create"/></s:link><li/>
                </ul>
            </div>
        </div>
    <!--header - logo + logout-->
    <div id="main-wrapper">
        <div class="logo">
            <s:link href="/index.jsp"><img src="${pageContext.request.contextPath}/images/logo.png" height="70" width="600"/></s:link>
        </div>
        <div class="logout">
            <a href="#">logout</a><br/>
            logged in as Admin<br/>
            <br/>
        </div>
        
        <s:messages/>
        <s:errors/>
        <s:layout-component name="body"/>
    </div>
</body>
</html>
</s:layout-definition>