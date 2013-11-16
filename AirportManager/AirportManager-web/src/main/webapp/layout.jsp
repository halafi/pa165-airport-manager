<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
  <title><f:message key="${titlekey}"/></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
  <s:layout-component name="header"/>
</head>
<body>
   <h1><f:message key="${titlekey}"/></h1>
   <div id="navigation">
     <ul>
       <li><s:link href="/index.jsp"><f:message key="navigation.index"/></s:link></li>
       <li><s:link href="/praha.jsp"><f:message key="navigation.praha"/></s:link></li>
       <li><s:link href="/podoli.jsp"><f:message key="navigation.podoli"/></s:link></li>
       <li><s:link href="/pharmacy.jsp"><f:message key="navigation.pharmacy"/></s:link></li>
     </ul>
   </div>
   <div id="content">
       <s:messages/>
       <s:layout-component name="body"/>
    </div>
</body>
</html>
</s:layout-definition>