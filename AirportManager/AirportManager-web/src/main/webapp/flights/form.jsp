<%-- 
    Document   : form
    Created on : 20.11.2013, 13:06:11
    Author     : Samo
--%>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<table>
    <tr>
        <th><s:label for="d1" name="flight.departureTime"/></th>
        <td><s:text id="d1" name="flight.departureTime"/></td>
    </tr>
    <tr>
        <th><s:label for="d1" name="flight.arrivalTime"/></th>
        <td><s:text id="d1" name="flight.arrivalTime"/></td>
    </tr>
    <tr>
        <th><s:label for="d2" name="flight.origin"/></th>
        <td><s:text id="d2" name="flight.origin.id"/></td>
    </tr>
    <tr>
        <th><s:label for="d2" name="flight.target"/></th>
        <td><s:text id="d2" name="flight.target.id"/></td>
    </tr>
    <tr>
        <th><s:label for="d3" name="flight.airplane"/></th>
        <td><s:text id="d3" name="flight.airplane.id"/></td>
    </tr>
    <tr>
        <th><s:label for="d3" name="flight.stewards"/></th>
        <td><s:text id="d3" name="flight.stewards"/></td>
    </tr>
</table>
