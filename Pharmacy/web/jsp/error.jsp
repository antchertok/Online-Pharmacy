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
    <title>Alternating Drugs</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="main.resources.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="locale.errmsg" var="errorMsg"/>
    <link rel="stylesheet" href="../css/default.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
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
    <%--.form-group{--%>
        <%--display: inline-block;--%>
    <%--}--%>
    <%--.form-control {--%>
        <%--margin-left: auto;--%>
        <%--margin-right: auto;--%>
        <%--vertical-align: sub;--%>
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
    <%--.error-msg{--%>
        <%--align-self: center;--%>
    <%--}--%>
    <%--/*.alt-drug{*/--%>
    <%--/*align-content: center;*/--%>
    <%--/*}*/--%>

<%--</style>--%>
<body>
<div class="container">
    <nav class="navbar navbar-default" class="flex-container">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp">Online Pharmacy</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-sm-3"><!--THIS FOR ALIGN. DO SMTH BETTER!--></div>
            <div class="col-sm-6">
                <div class="jumbotron">
                    <h3 align="center"><label>${errorMsg}</label></h3>
                    <label class="error-msg">${errMsg}<label>
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
