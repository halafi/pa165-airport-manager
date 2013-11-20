<%-- 
    Document   : form
    Created on : 20.11.2013, 12:21:12
    Author     : Chorke
--%>

<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:errors/>

<table>
    <tr>
        <th><s:label for="fn" name="steward.firstname"/></th>
        <th><s:text id="fn" name="steward.firstname"/></th>
    </tr>
    <tr>
        <th><s:label for="ln" name="steward.lastname"/></th>
        <th><s:text id="ln" name="steward.lastname"/></th>
    </tr>
</table>
