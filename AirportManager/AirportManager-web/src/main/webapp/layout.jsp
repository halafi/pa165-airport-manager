<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
  <title>Airport Manager</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
  <%--<s:layout-component name="header"/>--%>
</head>
<body>
   <!--left navigation panel-->
        <div id="navigation">
            <div class="user">
                <a href="#">find steward</a><br/>
                <a href="#">find airplane</a><br/>
                <a href="#">find flight</a><br/>
                <a href="#">find destination</a><br/>
            </div>
            <div class="admin">
                <a href="#">create steward</a><br/>
                <a href="#">create airplane</a><br/>
                <a href="#">create flight</a><br/>
                <a href="#">create destination</a><br/>
            </div>
        </div>
<!--header - logo + logout-->
    <div id="main-wrapper">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png" height="70" width="600"/>
        </div>
        <div class="logout">
            <a href="#">logout</a><br/>
            logged in as Admin<br/>
            <br/>
        </div>
        
        <s:messages/>
        <s:layout-component name="body"/>
    </div>

</body>
</html>
</s:layout-definition>