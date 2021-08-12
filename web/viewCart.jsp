<%-- 
    Document   : viewcart.jsp
    Created on : 24 thg 4, 2021, 20:13:33
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resource Page</title>
    </head>
    <body>

        <c:if test="${empty sessionScope.USER or sessionScope.USER.role.name ne 'leader'} or ${empty sessionScope.USER or sessionScope.USER.role.name ne 'employee'}">
            <c:redirect url="search"/>
        </c:if>


        <c:if test="${not empty MSG_ERROR}">
            <font color="red">
            ${MSG_ERROR}
            </font>
        </c:if>
        <c:if test="${not empty MSG_SUCCESS}">
            <font color="green">
            ${MSG_SUCCESS}
            </font>
        </c:if>
        <br>
        <c:if test="${not empty sessionScope.CART}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="resource" items="${sessionScope.CART}" varStatus="Counter">
                        <tr>
                            <td>${Counter.count}</td>
                            <td>${resource.value.name}</td>
                    <form action="updateCart" method="POST">
                        <td><input type="number" name="quantity" value="${resource.value.quantity}"/> </td>
                        <input type="hidden" name="id" value="${resource.key}"/>
                        <td><input type="submit" value="Update"/></td>
                    </form>
                    <form action="deleteCart" method="POST">
                        <input type="hidden" name="id" value="${resource.key}"/>
                        <td><input type="submit" value="Delete"/></td>
                    </form>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br/>
    <form action="ConfirmCart" method="POST">
        <input type="submit" value="Confirm"/>    
    </form>
</c:if>
<c:if test="${empty sessionScope.CART}">
    <font color="red">No result</font>
</c:if>
<br/>
<a href="search">Back</a><br>

</body>
</html>
