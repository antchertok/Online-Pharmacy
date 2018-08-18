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
    <fmt:setBundle basename="locale" var="locale"/>
    <fmt:message bundle="${locale}" key="button.change-locale.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="button.change-locale.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="label.login" var="login"/>
    <fmt:message bundle="${locale}" key="label.password" var="password"/>
    <fmt:message bundle="${locale}" key="label.first-name" var="fname"/>
    <fmt:message bundle="${locale}" key="label.last-name" var="lname"/>
    <fmt:message bundle="${locale}" key="label.mail" var="mail"/>
    <fmt:message bundle="${locale}" key="label.sign" var="sign"/>
    <fmt:message bundle="${locale}" key="button.sign-in" var="logbut"/>
    <fmt:message bundle="${locale}" key="button.sign-up" var="signbut"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/default.css">
    <script type="text/javascript" src="../js/validation.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

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

                        <div class="row" class="sign-form">

                            <div class="col-sm-6">
                                <div class="sign-in">
                                    <form name="LoginForm" method="POST" action="/controller" onsubmit="return validateSignIn()">
                                        <input type="hidden" name="command" value="login" />
                                        <label>${login}:    <br/></label><input type="text" name="login" class="user-info-input" required value=""/><br/>
                                        <span class="errorMsg" id="loginIn"></span><br/>
                                        <label>${password}: <br/></label><input type="password" name="password" class="user-info-input" required value=""/><br/>
                                        <span class="errorMsg" id="passwordIn"></span><br/>
                                        <input type="submit" value="${logbut}"/><br/>
                                        ${requestScope.accessDeniedMessage}
                                    </form>
                                </div>
                            </div>

                            <div class="col-sm-6" style="border-left-style: dotted">
                                <form name="SignUpForm"  action="/controller" onsubmit="return validateSignUp()">
                                    <input type="hidden" name="command" value="sign-up" />
                                    <label>${login}:    <br/></label><input type="text" name="login" class="user-info-input" required value=""/><br/>
                                    <span class="errorMsg" id="loginUp"></span><br/>
                                    <label>${password}: <br/></label><input type="password" name="password" class="user-info-input" required value=""/><br/>
                                    <span class="errorMsg" id="passwordUp"></span><br/>
                                    <label>${fname}:    <br/></label><input type="text" name="firstName" class="user-info-input" required value=""/><br/>
                                    <span class="errorMsg" id="firstNameUp"></span><br/>
                                    <label>${lname}: <br/></label><input type="text" name="lastName" class="user-info-input" required value=""/><br/>
                                    <span class="errorMsg" id="lastNameUp"></span><br/>
                                    <label>${mail}:    <br/></label><input type="text" name="email" class="user-info-input" required value=""/><br/>
                                    <span class="errorMsg" id="mailUp"></span><br/>
                                    <input type="submit" name="submit" value="${signbut}"/>
                                    ${requestScope.errorRegMsg}
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <form name=LocaleRUChanging" action="/controller" id="toRU">
                <input type="hidden" name="currentUrl" value="jsp/login.jsp"/>
                <input type="hidden" name="command" value="change-locale"/>
                <input type="hidden" name="locale" value="ru"/>
            </form>
            <form name=LocaleENChanging" action="/controller" id="toEN">
                <input type="hidden" name="currentUrl" value="jsp/login.jsp"/>
                <input type="hidden" name="command" value="change-locale"/>
                <input type="hidden" name="locale" value="en"/>
            </form>
            <div class="btn-group">
                <button type="submit" class="btn " form="toEN" value="submit">
                    <fmt:message key = "button.change-locale.en" bundle = "${locale}"/>
                </button>
                <button type="submit" class="btn " form="toRU" value="submit">
                    <fmt:message key = "button.change-locale.ru" bundle = "${locale}"/>
                </button>
            </div>

        </div>
    </div>
</body>
</html>
