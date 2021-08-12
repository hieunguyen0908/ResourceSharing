<%-- 
    Document   : signUp
    Created on : 20 thg 5, 2021, 20:00:19
    Author     : PC
--%>

<%@page import="hieunnm.dtos.UserErrDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign in</title>
    </head>
    <body>
        <%
            UserErrDTO userError = (UserErrDTO) request.getAttribute("MSG_ERROR");
            if (userError == null) {
                userError = new UserErrDTO("", "", "", "", "", "", "");
            }
        %>
        <div style="width: 50%; margin-left: 25%">
            <form action="register" method="POST">

                <!--                User ID<input type="text" name="userID" required="true"/><br/>
                                <font color="red"><%=userError.getUserIDError()%></font><br/>
                                Name<input type="text" name="userName" required="true"/><br/>
                                <font color="red"><%=userError.getUserNameError()%></font><br/>
                                Password<input type="password" name="password" required="true"/> <br/>
                                <font color="red"> <%=userError.getPasswordError()%><br/>
                                Confirm<input type="password" name="confirmPassword" required="true"/><br/>
                                <font color="red"><%=userError.getConfirmPasswordError()%></font><br/>
                                Phone<input type="tel" name="phone" required="true"/><br/>    
                                <font color="red"><%=userError.getPhoneError()%></font><br/>
                                Address<input type="text" name="address" required="true" maxlength="200"/><br/>    
                                <font color="red"><%=userError.getAddressError()%></font><br/>
                                Email<input type="email" name="email" required="true" maxlength="64"/><br/>    
                                <font color="red"><%=userError.getEmailError()%></font><br/>-->


                <table border="1" style="min-width: 80%">
                    <tbody style="min-width: 80%">
                        <tr>
                            <td>UserID</td>
                            <td><input type="text" name="userID" required="true"/>
                                <font color="red"><%=userError.getUserIDError()%></font></td>
                        </tr>

                        <tr>
                            <td>Password</td>
                            <td><input type="password" name="password" required="true"/>
                                <font color="red"> <%=userError.getPasswordError()%></td>
                        </tr>
                        <tr>
                            <td>Confirm</td>
                            <td><input type="password" name="confirmPassword" required="true"/>
                                <font color="red"><%=userError.getConfirmPasswordError()%></td>
                        </tr>
                        <tr>
                            <td>Name</td>
                            <td><input type="text" name="userName" required="true"/>
                                <font color="red"><%=userError.getUserNameError()%></font></td>
                        </tr>

                        <tr>
                            <td>Phone</td>
                            <td><input type="tel" name="phone" required="true"/>
                                <font color="red"><%=userError.getPhoneError()%></td>
                        </tr>
                        <tr>
                            <td>Address</td>
                            <td><input type="text" name="address" required="true" maxlength="200"/>
                                <font color="red"><%=userError.getAddressError()%></font></td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td><input type="email" name="email" required="true" maxlength="64"/>
                                <font color="red"><%=userError.getEmailError()%></font></td>

                        </tr>
                    </tbody>

                </table>
                <br/>
                <input type="submit" value="Register" />
                <input type="reset" value="Reset" />
            </form>
            <br/><a href="login">Back</a>

        </div>

    </body>
</html>
