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
                    <s:link href="/index.jsp">
                        <img src="${pageContext.request.contextPath}/images/logo.png" height="70" width="600"/>
                    </s:link>
                </div>
                <div id="logout">
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                        <a href="<c:url value="/j_spring_security_logout" />">logout</a>
                    </sec:authorize>
                    <sec:authorize access="!hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                        <a href="${pageContext.request.contextPath}/login.jsp">login</a>
                    </sec:authorize>
                </div>
            </div>

            <%@include file="/adminmenu.jsp"%>

            <div id="footer">
                <f:message key="page.authors"/>: Bc. Juraj Duráni (359185), Bc. Matúš Makový (374426),
                Bc. Samuel Peťovský (374591), Filip Halas (374137); <f:message key="page.fimu"/>;
                e-mail: (učo)@mail.muni.cz
            </div>
        </body>
    </html>
</s:layout-definition>
