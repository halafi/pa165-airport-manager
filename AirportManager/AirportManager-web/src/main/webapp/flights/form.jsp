<%-- 
    Document   : form
    Created on : 20.11.2013, 13:06:11
    Author     : Samo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" var="actionBean"/>

<s:errors/>
<table>
    <tr>
        <th><s:label for="d1" name="flight.departureTime"/></th>
        <td><s:text id="d1" name="flight.departureTime"/></td>
    </tr>
    <tr>
        <th><s:label for="d2" name="flight.arrivalTime"/></th>
        <td><s:text id="d2" name="flight.arrivalTime"/></td>
    </tr>
    <tr>
        <th><s:label for="d3" name="flight.origin"/></th>
        <td><s:select id="d3" name="flight.origin">
                <c:forEach items="${actionBean.desList}" var="destination">
                    <s:option value="${destination}">
                        ${destination.city}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
    <tr>
        <th><s:label for="d4" name="flight.target"/></th>
        <td><s:select id="d4" name="flight.target">
                <c:forEach items="${actionBean.desList}" var="destination">
                    <s:option value="${destination}">
                        ${destination.city}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
    
    <tr>
        <th><s:label for="d5" name="flight.airplane"/></th>
        <td><s:select id="d5" name="flight.airplane">
                <c:forEach items="${actionBean.airList}" var="airplane">
                    <s:option value="${airplane}">
                        ${airplane.name}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
    <tr>
        <th><s:label for="d6" name="flight.stewards"/></th>
        <td><s:select id="d6" name="flight.stewards">
                <c:forEach items="${actionBean.stewList}" var="steward">
                    <s:option value="${steward}">
                        ${steward.firstName} ${steward.lastName}
                    </s:option>
                </c:forEach>
             </s:select>    
        </td>
    </tr>
</table>
       
