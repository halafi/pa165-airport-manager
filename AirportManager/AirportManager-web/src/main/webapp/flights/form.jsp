<%-- 
    Document   : form
    Created on : 20.11.2013, 13:06:11
    Author     : Samo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" var="actionBean"/>--%>

<s:errors/>
<table>
    <tr>
        <th><s:label for="f1" name="flight.departureTime"/></th>
        <td><s:text id="f1" name="flight.departureTime"/></td>
    </tr>
    <tr>
        <th><s:label for="f2" name="flight.arrivalTime"/></th>
        <td><s:text id="f2" name="flight.arrivalTime"/></td>
    </tr>
    <tr>
        <th><s:label for="f3" name="flight.origin"/></th>
        <td><s:select id="f3" name="flight.origin">
                <s:option><f:message key="flight.chooseOne"/></s:option>
                <c:forEach items="${actionBean.desList}" var="destination">
                    <s:option value="destination">
                        ${destination.city}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
    <tr>
        <th><s:label for="f4" name="flight.target"/></th>
        <td><s:select id="f4" name="flight.target">
                <s:option><f:message key="flight.chooseOne"/></s:option>
                <c:forEach items="${actionBean.desList}" var="destination">
                    <s:option value="destination">
                        ${destination.city}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
    
    <tr>
        <th><s:label for="f5" name="flight.airplane"/></th>
        <td><s:select id="f5" name="flight.airplane">
                <s:option><f:message key="flight.chooseOne"/></s:option>
                <c:forEach items="${actionBean.airList}" var="airplane">
                    <s:option value="airplane">
                        ${airplane.name}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
    <tr>
        <th><s:label for="f6" name="flight.stewards"/></th>
        <td><s:select id="f6" name="flight.stewards">
                <s:option><f:message key="flight.chooseOne"/></s:option>
                <c:forEach items="${actionBean.stewList}" var="steward">
                    <s:option value="${steward}">
                        ${steward.firstName} ${steward.lastName}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
</table>
       
