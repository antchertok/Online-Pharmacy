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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="locale"/>
    <fmt:message bundle="${locale}" key="msg.error" var="errorMsg"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/default.css">
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

            <div class="form-group">
                <form name="SeekDrugForm" action="/controller" id="seekDrug">
                    <input type="hidden" name="command" value="seek-drug"/>
                    <input type="hidden" name="pageNumber" value="1"/>
                    <input type="hidden" name="elements" value="7"/>
                    <input type="text" title = "Insert at least part of drug name" class="form-control" id="srch" placeholder="<fmt:message key = "label.search" bundle = "${locale}"/>" name="name" value="" />
                </form>
            </div>
            <button type="submit" class="btn " form="seekDrug" value="submit" id="start-search">
                <label><span class="glyphicon glyphicon-search"></span></label>
            </button>
            <ul class="nav navbar-nav" class="sidelist" style="float: right">

                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'pharmacist'}">
                        <li>
                            <a href="jsp/drug-alternate.jsp">
                                <label>
                                    <fmt:message key = "button.add-drug" bundle = "${locale}"/>
                                    <span class="glyphicon glyphicon-plus"></span>
                                </label>
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq 'doctor'}">
                        <li>
                            <a href="/controller?command=list-prescriptions">
                                <label>
                                    <fmt:message key = "button.requests" bundle = "${locale}"/>
                                    <span class="glyphicon glyphicon-bell"></span>
                                </label>
                            </a>
                        </li>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <li>
                            <a href="/controller?command=current-order">
                                <label>
                                    <fmt:message key = "button.order" bundle = "${locale}"/>
                                    <span class="glyphicon glyphicon-shopping-cart"></span>
                                    <span class="badge">${sessionScope.drugsOrdered}</span>
                                </label>
                            </a>
                        </li>

                        <li>
                            <a href="/controller?command=profile">
                                <label><fmt:message key = "label.profile" bundle = "${locale}"/></label>
                            </a>
                        </li>

                        <li>
                            <a href="/controller?command=logout">
                                <label><fmt:message key = "button.quit" bundle = "${locale}"/></label>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="login.jsp">
                                <label><fmt:message key = "button.login" bundle = "${locale}"/></label>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-sm-3"><!--THIS FOR ALIGN. DO SMTH BETTER!--></div>
            <div class="col-sm-6">
                <div class="jumbotron">
                    <h3 align="center"><label>${errorMsg}</label></h3>
                    <label class="error-msg">${sessionScope.errMsg}</label>
                </div>
            </div>
        </div>

        <form name=LocaleRUChanging" action="controller" id="toRU">
            <input type="hidden" name="currentUrl" value="jsp/error.jsp"/>
            <input type="hidden" name="command" value="change-locale"/>
            <input type="hidden" name="locale" value="ru"/>
        </form>
        <form name=LocaleENChanging" action="controller" id="toEN">
            <input type="hidden" name="currentUrl" value="jsp/error.jsp"/>
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
