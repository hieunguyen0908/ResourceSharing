<%-- 
    Document   : myRequests
    Created on : 1 thg 6, 2021, 11:59:28
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Request History</title>
    </head>
    <body>
        <form action="searchRequest" method="POST">
            <h1>Search</h1>
            <p>Name: <input type="text" name="txtName" value="${param.txtName}" maxlength="50"/></p>
            <p>Request Date <input type="date" id="datefield" name="txtRequestDate" value="${param.txtRequestDate}" 
                                   min="1900-01-01" max="2030-12-30"/></p> 
            <input type="submit" value="Search" />
        </form>
        <br/>
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
    <c:if test="${not empty requestScope.SEARCH_RESULT}">
        <table border='1'>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Resource Name</th>
                    <th>Request date</th>
                    <th>Quantity</th>

                    </c:if>
            <c:if test="${sessionScope.USER.role.name eq 'leader'}"> 
                <th></th>
            </c:if>
            <c:if test="${sessionScope.USER.role.name eq 'employee'}"> 
                <th></th>
            </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dto" items="${requestScope.SEARCH_RESULT}" varStatus="Counter">
                <c:if test="${empty sessionScope.USER or sessionScope.USER.role.name ne 'manager'}">
                    <tr>
                        <td>
                            ${Counter.count}
                        </td>

                        <td>
                            <input type="text" name="txtName" value="${dto.name} " readonly/>

                        </td>

                        <td>
                            <input type="date" name="txtUsingDate" value="${dto.usingDate}" readonly/>  
                        </td>

                        <td>
                            <input type="text" name="txtQuantity" value="${dto.quantity}" readonly />
                        </td>


                    <c:if test="${sessionScope.USER.role.name eq 'leader'}"> 
                        <form action="addToCart" method="POST">
                            <input type="hidden" name="id" value="${dto.id}"/>
                            <input type="hidden" name="name" value="${dto.name}"/>
                            <input type="hidden" name="txtName" value="${param.txtName}" />
                            <input type="hidden" name="txtUsingDate" value="${param.txtUsingDate}" />
                            <input type="hidden" name="categories" value="${param.categories}" />

                            <td><input type="submit" value="Booking"/></td>
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.USER.role.name eq 'employee'}"> 
                        <form action="addToCart" method="POST">
                            <input type="hidden" name="id" value="${dto.id}"/>
                            <input type="hidden" name="name" value="${dto.name}"/>
                            <input type="hidden" name="txtName" value="${param.txtName}" />
                            <input type="hidden" name="txtUsingDate" value="${param.txtUsingDate}" />
                            <input type="hidden" name="categories" value="${param.categories}" />

                            <td><input type="submit" value="Booking"/></td>
                        </form>
                    </c:if>


                    </tr>
                </c:if>

            </c:forEach>
            </tbody>
        </table>

    </c:if>  


    <c:if test="${empty requestScope.SEARCH_RESULT}">
        <font color="red">
        <h2>No Result</h2>
        </font>
    </c:if>

    <br/>
    <form action="logout" method="POST">
        <input type="submit" value="Logout"/>
    </form><br/>
</body>
</html>
