<!DOCTYPE jsp>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: --
  Date: 12.07.2018
  Time: 13:28
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
    <fmt:message bundle="${locale}" key="locale.logparameter" var="login"/>
    <fmt:message bundle="${locale}" key="locale.password" var="password"/>
    <fmt:message bundle="${locale}" key="locale.firstname" var="fname"/>
    <fmt:message bundle="${locale}" key="locale.lastname" var="lname"/>
    <fmt:message bundle="${locale}" key="locale.mail" var="mail"/>
    <fmt:message bundle="${locale}" key="locale.signupmsg" var="sign"/>
    <fmt:message bundle="${locale}" key="locale.logbutton" var="logbut"/>
    <fmt:message bundle="${locale}" key="locale.signupbutton" var="signbut"/>
    <link rel="stylesheet" href="../css/default.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<%--<hr>--%>
<%--<form action="params.jsp">--%>
<%--First name: <input type="text" name="firstname"><br/>--%>
<%--email 1: <input type="text" name="mail"><br/>--%>
<%--email 2: <input type="text" name="mail"><br/>--%>
<%--<input type="submit" name="submit" value="Подтвердить">--%>
<%--</form>--%>
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
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="jumbotron">
                    <h3 align="center"><label>${sign}</label></h3>
                    <div class="row">
                        <div class="col-sm-12" >
                            <form name="AltDrug"  action="controller" class="alt-drug">
                                <input type="hidden" name="command" <c:choose>
                                    <c:when test="${alternating eq 'true'}">
                                        value="update-drug"
                                    </c:when>
                                    <c:otherwise>
                                        value="add-drug"
                                    </c:otherwise>
                                </c:choose>/>
                                <input type="hidden" name="drugId" value="${drugId}"/>
                                <label><div>Name:</div>          <input type="text" name="name" required value="${name}"/></label><br/>
                                <label><div>Dose (mg): </div>     <input type="number" min="0" name="dose" required value="${dose}"/></label><br/>
                                <label><div>Price:</div>         <input type="number" min="1" name="price" required value="${price}"/></label><br/> <!--FLOAT?-->
                                <label><div>Prescription:</div>  <input type="number" min="0" max="1" name="prescription" value="${prescription}"/></label><br/>
                                <input type="submit" name="submit" <c:choose>
                                    <c:when test="${alternating eq 'true'}">
                                        value="Update"
                                    </c:when>
                                    <c:otherwise>
                                        value="Add"
                                    </c:otherwise>
                                </c:choose>/>
                                <%--<div id="deleting-drug">--%>
                                    <%--<c:if test="${alternating eq 'true'}">--%>
                                        <%--<form name="DeleteDrug" action="controller" >--%>
                                            <%--<input type="hidden" name="drugId" value="${drugId}"/>--%>
                                            <%--<input type="submit" name="delete" value="Delete this drug"/>--%>
                                        <%--</form>--%>
                                    <%--</c:if>--%>
                                <%--</div>--%>
                                <label>${infoMsg}</label>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <form name=LocaleRUChanging" action="controller" id="toRU">
            <input type="hidden" name="currentUrl" value="drug-alternate.jsp"/>
            <input type="hidden" name="command" value="change-locale"/>
            <input type="hidden" name="locale" value="ru"/>
        </form>
        <form name=LocaleENChanging" action="controller" id="toEN">
            <input type="hidden" name="currentUrl" value="drug-alternate.jsp"/>
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
