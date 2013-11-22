<%-- 
    Document   : form
    Created on : 20.11.2013, 13:06:11
    Author     : Samo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.FlightsActionBean" var="actionBean"/>--%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="../datetime/pickadate.js-3.3.0/lib/themes/classic.css" />
<link rel="stylesheet" href="../datetime/pickadate.js-3.3.0/lib/themes/classic.date.css" />
<link rel="stylesheet" href="../datetime/pickadate.js-3.3.0/lib/themes/classic.time.css" />
<!--<script src="http://code.jquery.com/jquery-1.9.1.js"></script>-->
<!--<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>-->
<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="../datetime/pickadate.js-3.3.0/lib/picker.js"></script>
<script src="../datetime/pickadate.js-3.3.0/lib/picker.date.js"></script>
<script src="../datetime/pickadate.js-3.3.0/lib/picker.time.js"></script>
<s:errors/>
<table>
    <tr>
        <th><s:label for="f1date" name="flight.departureTime"/></th>
        <td><s:text id="f1date" name="flight.departureTime" /></td>
    </tr>
    <tr>
        <th><s:label for="f1time" name="flight.time"/></th>
        <td><s:text id="f1time" name="flight.departureTime"/></td>
    </tr>
    <tr>
        <th><s:label for="f2date" name="flight.arrivalTime"/></th>
        <td><s:text id="f2date" name="flight.arrivalTime"/></td>
    </tr>
    <tr>
        <th><s:label for="f2time" name="flight.time"/></th>
        <td><s:text id="f2time" name="flight.arrivalTime"/></td>
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

<script>
    $(function() {
        if(window.navigator.language == "Sk"){
            $("#f1date, #f2date").pickadate({
                monthsFull: [ 'január', 'február', 'marec', 'apríl', 'máj', 'jún', 'júl', 'august', 'september', 'október', 'november', 'december' ],
                monthsShort: [ 'I', 'II', 'III', 'IV', 'V', 'VI', 'VII', 'VIII', 'IX', 'X', 'XI', 'XII' ],
                weekdaysFull: [ 'nede?a', 'pondelok', 'utorok', 'streda', '?tvrtok', 'piatok', 'sobota' ],
                weekdaysShort: [ 'Ne', 'Po', 'Ut', 'St', '?t', 'Pi', 'So' ],
                today: 'dnes',
                clear: 'vymaza?',
                firstDay: 1,
                format: 'd. mmmm yyyy',
                formatSubmit: 'yyyy/mm/dd'
            });
        } else {
            $("#f1date, #f2date").pickadate();
        }
    });
</script>
<script>
    $(function() {
        $("#f1time, #f2time").pickatime();
    });
</script>