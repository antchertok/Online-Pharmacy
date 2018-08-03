<%--
  Created by IntelliJ IDEA.
  User: --
  Date: 12.07.2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE jsp>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: --
  Date: 01.07.2018
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="main.resources.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="locale.logparameter" var="login"/>
    <fmt:message bundle="${locale}" key="locale.password" var="password"/>
    <fmt:message bundle="${locale}" key="locale.firstname" var="fname"/>
    <fmt:message bundle="${locale}" key="locale.lastname" var="lname"/>
    <fmt:message bundle="${locale}" key="locale.mail" var="mail"/>
    <fmt:message bundle="${locale}" key="locale.signupmsg" var="sign"/>
    <fmt:message bundle="${locale}" key="locale.logbutton" var="logbut"/>
    <fmt:message bundle="${locale}" key="locale.signupbutton" var="signbut"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/default.css">
    <script type="text/javascript" src="../js/validation.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<%--<style>--%>
    <%--.btn-group {--%>
        <%--float: right;--%>
        <%--position: fixed;--%>
        <%--bottom: 0;--%>
        <%--right: 10%;--%>
    <%--}--%>
    <%--h3 {--%>
        <%--horiz-align: center;--%>
        <%--justify-content: center;--%>
    <%--}--%>
    <%--.nav navbar-nav {--%>
        <%--float: right;--%>
    <%--}--%>
    <%--.sign-in {--%>
        <%--vertical-align: middle;--%>
    <%--}--%>
    <%--.errorMsg{--%>
        <%--style: "color:red";--%>
    <%--}--%>

<%--</style>--%>
<body>
    <div class="container">
    <nav class="navbar navbar-default" class="flex-container">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/controller?command=home-page">Online Pharmacy</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="jumbotron">
                    <h3 align="center"><label>${sign}</label></h3>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="sign-in">
                                <form name="LoginForm" method="POST" action="/controller" onsubmit="return validateSignIn()">
                                    <input type="hidden" name="command" value="login" />
                                    <label>${login}:    <input type="text" name="login" required value=""/></label><br/>
                                    <span class="errorMsg" id="loginIn"></span><br/>
                                    <label>${password}: <input type="password" name="password" required value=""/></label><br/>
                                    <span class="errorMsg" id="passwordIn"></span><br/>
                                    <input type="submit" name="submit" value="${logbut}"/>
                                    ${accessDeniedMessage}
                                </form>
                            </div>
                        </div>
                        <div class="col-sm-6" style="border-left-style: dotted">
                            <form name="SignUpForm"  action="/controller" onsubmit="return validateSignUp()">
                                <input type="hidden" name="command" value="sign-up" />
                                <label>${login}:    <input type="text" name="login" required value=""/></label><br/>
                                <span class="errorMsg" id="loginUp"></span><br/>
                                <label>${password}: <input type="password" name="password" required value=""/></label><br/>
                                <span class="errorMsg" id="passwordUp"></span><br/>
                                <label>${fname}:    <input type="text" name="firstName" required value=""/></label><br/>
                                <span class="errorMsg" id="firstNameUp"></span><br/>
                                <label>${lname}: <input type="text" name="lastName" required value=""/></label><br/>
                                <span class="errorMsg" id="lastNameUp"></span><br/>
                                <label>${mail}:    <input type="text" name="email" required value=""/></label><br/>
                                <span class="errorMsg" id="mailUp"></span><br/>
                                <input type="submit" name="submit" value="${signbut}"/>
                                ${errorRegMsg}
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <form name=LocaleRUChanging" action="controller" id="toRU">
            <input type="hidden" name="currentUrl" value="login.jsp"/>
            <input type="hidden" name="command" value="change-locale"/>
            <input type="hidden" name="locale" value="ru"/>
        </form>
        <form name=LocaleENChanging" action="controller" id="toEN">
            <input type="hidden" name="currentUrl" value="login.jsp"/>
            <input type="hidden" name="command" value="change-locale"/>
            <input type="hidden" name="locale" value="en"/>
        </form>
        <div class="btn-group">
            <button type="submit" class="btn " form="toEN" value="submit">${en_button}</button>
            <button type="submit" class="btn " form="toRU" value="submit">${ru_button}</button>
        </div>

    </div>
    </div>
</body>
</html>
