<%-- 
    Document   : search
    Created on : 24 thg 5, 2021, 15:35:04
    Author     : PC
--%>

<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>

    <script>
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }

        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById("datefield").setAttribute("max", today);
    </script>
    <body> 

        <c:if test="${empty sessionScope.USER}">
            <a href="login.jsp" style="text-decoration : none">Login</a>
        </c:if>

        <c:if test="${not empty sessionScope.USER}">
            <h2>
                Welcome, <font color="red">${sessionScope.USER.fullName}
                </font>(${sessionScope.USER.role.name}) 
                <a href="myRequests.jsp" style="text-decoration : none">History</a>
            </h2><br/>  
            <!--            <c:if test="${sessionScope.USER.role.name eq 'manager'}">
                            <a href="loadInsert" style="text-decoration : none">View requests</a><br/>
            </c:if>            -->
            <c:if test="${sessionScope.USER.role.name eq 'employee' and not empty sessionScope.CART}">
                <a href="viewCart.jsp" style="text-decoration : none">Your Requestes</a><br/>
            </c:if>
            <c:if test="${sessionScope.USER.role.name eq 'leader' and not empty sessionScope.CART}">
                <a href="viewCart.jsp" style="text-decoration : none">Your Requestes</a><br/>
            </c:if>

        </c:if>

        <br/>
        <form action="search" method="POST">
            <h1>Search</h1>
            <p>Name: <input type="text" name="txtName" value="${param.txtName}" maxlength="50"/></p>
            <p>Category:
                <select name="categories">
                    <option value="all">All</option>
                    <c:forEach var="cateDto" items="${requestScope.CATE_LIST}" >
                        <option value="${cateDto.name}" <c:if test="${param.categories eq cateDto.name}">selected</c:if>>${cateDto.name}</option>
                    </c:forEach>    
                </select></p>

            <p>Using date: <input type="date" id="datefield" name="txtUsingDate" value="${param.txtUsingName}" 
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
                        <th>Name</th>
                        <th>Using date</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Color</th>
                            <c:if test="${sessionScope.USER.role.name eq 'manager'}"> 
                            <th></th>
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
                                    <input type="text" name="txtCategory" value="${dto.category.name}" readonly />
                                </td>
                                <td>
                                    <input type="text" name="txtQuantity" value="${dto.quantity}" readonly />
                                </td>

                                <td>
                                    <input type="text" name="txtColor" value="${dto.color}" readonly/>
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
                <c:if test="${not empty sessionScope.USER and sessionScope.USER.role.name eq 'manager'}">
                    <tr>
                        <td>
                            ${Counter.count}
                        </td>

                        <td>
                            <input type="text" name="txtName" value="${dto.name} " readonly/>
                        </td>
                        <td>
                            <input type="date" name="txtUsingDate" value="${dto.usingDate}"  readonly/>
                        </td>
                        <td>
                            <input type="text" name="txtCategory" value="${dto.category.name}" readonly />
                        </td>
                        <td>
                            <input type="text" name="txtQuantity" value="${dto.quantity}" readonly />
                        </td>

                        <td>
                            <input type="text" name="txtColor" value="${dto.color}" readonly/>
                        </td>
                        <c:if test="${sessionScope.USER.role.name eq 'manager'}"> 
                        <form action="" method="POST">
                            <td><input type="submit" value="Requests"/></td>
                        </form>
                    </c:if>
                </form>

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
