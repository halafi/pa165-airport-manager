<%-- 
    Document   : login
    Created on : 19.1.2014, 19:13:37
    Author     : Samo
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="login"/></legend>
            <form name="f" action="<s:url value='j_spring_security_check'/>" method="POST">
                <table>
                    <tr>
                        <td><f:message key="username"/></td>
                        <td><input type='text' name='j_username' /></td>
                    </tr>
                    <tr>
                        <td><f:message key="password"/></td>
                        <td><input type='password' name='j_password'></td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan='2'><input name="submit" type="submit">&nbsp;<input name="reset" type="reset"></td>
                    </tr>
                </table>
            </form>
        </fieldset>
    </s:layout-component>
</s:layout-render>


