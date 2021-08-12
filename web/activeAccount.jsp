<%-- 
    Document   : activeAccount
    Created on : 20-May-2021, 10:06:09
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Page</title>
    </head>
    <body>
        <div style="width: 50%; margin-left: 25%">
            <c:url var="Active" value="ActiveAccount">
            </c:url>
            <a href="${Active}"><p style="text-align: center">Send Active Code</p></a>
            <div style="margin-left: 25%">
                <form action="activeAccount" >
                    Code: <input type="text" name="txtActiveCode"/>
                    <input type="submit" value="Confirm code"/>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
