<%-- 
    Document   : login
    Created on : 12 thg 5, 2021, 12:26:27
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://www.google.com/recaptcha/api.js?hl=vi" async defer></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" 
              content="140924803404-oup5d41ub6i3gteb8ihreh5d591tce7b.apps.googleusercontent.com
              ">
        <script>
            function onSignIn(googleUser) {
                var profile = googleUser.getBasicProfile();
                console.log('ID: ' + profile.getId());
                console.log('Name: ' + profile.getName());
                console.log('Image URL: ' + profile.getImageUrl());
                console.log('Email: ' + profile.getEmail());
                console.log('id_token: ' + googleUser.getAuthResponse().id_token);
                var redirectUrl = 'login';
                //using jquery to post data dynamically
                var form = $('<form action="' + redirectUrl + '" method="post">' +
                        '<input type="text" name="id_token" value="' +
                        googleUser.getAuthResponse().id_token + '" />' +
                        '</form>');
                $('body').append(form);
                form.submit();
            }

        </script>
        <script>
            var recaptchachecked = false;
            function recaptchaCallback() {
                recaptchachecked = true;
            }


            function isreCaptchaChecked()
            {
                return recaptchachecked;
            }

        </script>

    </head>
    <body>

        <h1>Login</h1>
        <c:if test="${not empty MSG_ERROR}">

        </c:if>
        <form action="login" method="POST" onsubmit="return isreCaptchaChecked()"> 
            Username: <input type="text" name="txtUsername" value="" required=""/><br/><br/>
            Password: <input type="password" name="txtPassword" value="" required=""/><br/>
            <br/>
            <div class="g-recaptcha" data-sitekey="6LcTUdYaAAAAANzlMdFL-Tw5aQDNOC42xfXm_fDw" name="recaptcha" data-callback="recaptchaCallback"></div>
            <br/>
            <input type="submit" value="Login"/>
            <input type="reset" value="Reset"/>
        </form> 

        <font color="red">
        ${MSG_ERROR}
        </font>

        <br/>
<!--        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/ResourceSharing/login-google&response_type=code
           &client_id=140924803404-oup5d41ub6i3gteb8ihreh5d591tce7b.apps.googleusercontent.com
           &approval_prompt=force">Login With Google</a>  -->
        <!--        <div class="g-signin2" data-onsuccess="onSignIn"></div>-->
        <br/><a href="search" style="text-decoration : none">Search page</a> 
        <br/><a href="register.jsp" style="text-decoration : none">Sign in</a><br/> 
    </body>
</html>
