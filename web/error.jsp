<%-- 
    Document   : error
    Created on : 18 thg 4, 2021, 11:24:41
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <font color="red">
        ${requestScope.ERROR}
        </font>
        <br/>
        <a href="login">Back</a>
    </body>
</html>
