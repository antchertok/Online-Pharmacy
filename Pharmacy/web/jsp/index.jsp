<!DOCTYPE jsp>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="pharmacytags" %>
<%--
  Created by IntelliJ IDEA.
  User: --
  Date: 01.07.2018
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Start Page</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="main.resources.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="locale.profile" var="profile"/>
    <fmt:message bundle="${locale}" key="locale.quit" var="log_out"/>
    <fmt:message bundle="${locale}" key="locale.login" var="log_in"/>
    <fmt:message bundle="${locale}" key="locale.search" var="search"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/default.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">

    <!--  <h1>Hello World!</h1>
      <p>Resize the browser window to see the effect.</p> -->
    <nav class="navbar navbar-default" class="flex-container">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/controller?command=home-page">Online Pharmacy</a>
            </div>

            <ul class="nav navbar-nav" class="sidelist" style="float: right">
                <li>
                    <div class="form-group">
                        <form name="SeekDrugForm" action="controller" id="seekDrug">
                                <input type="hidden" name="command" value="seek-drug"/>
                                <input type="hidden" name="pageNumber" value="1"/>
                                <input type="hidden" name="elements" value="7"/>
                                <input type="text" title = "Insert at least part of drug name" class="form-control" id="srch" placeholder="Search" name="name" value="" style="display: inline-block" />
                        </form>
                    </div>
                </li>
                <li>
                    <button type="submit" class="btn " form="seekDrug" value="submit" id="start-search"><label ><span class="glyphicon glyphicon-search"></span></label></button>
                </li>
                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'pharmacist'}">
                        <li>
                            <a href="drug-alternate.jsp">
                                <label>
                                    Add Drug
                                    <span class="glyphicon glyphicon-plus"></span>
                                </label>
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq 'doctor'}">
                        <li>
                            <a href="/controller?command=list-prescriptions">
                                <label>
                                    Requests
                                    <span class="glyphicon glyphicon-bell"></span>
                                </label>
                            </a>
                        </li>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <li>
                            <a href="/controller?command=current-order"><!--УТОЧНИТЬ-->
                                <label>Order
                                    <span class="glyphicon glyphicon-shopping-cart"></span>
                                    <span class="badge">${sessionScope.drugsOrdered}</span>
                                </label>
                            </a>
                        </li>


                        <li>
                            <a href="profile.jsp">
                                <label>${profile}</label>
                            </a>
                        </li>
                        <li>
                            <a href="controller?command=logout"><!--MAKE IT!!!!-->
                                <label>${log_out}</label>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="../jsp/login.jsp">
                                <label>${log_in}</label>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>
    <div class="row">
        <div class="col-sm-9" >

            <div class="jumbotron">
                ${infoOrderMsg}

                <ctg:page-listing list="${drugList}" elementsOnPage="7" pageNumber="${pageNumber}"/>

                <hr/>
                ${emptyResultMsg}

            </div>
        </div>

        <div class="col-sm-3">
            <div class="sideblock">
                <label>

                    <c:choose>
                        <c:when test="${sessionScope.user eq null}">
                            Please enter the system to use it properly
                        </c:when>
                        <c:when test="${total eq 0}">
                            <div class="user-info">
                                <label>
                                    <span>
                                        Profile :${sessionScope.user.role} <br/>
                                        ${sessionScope.user.firstName} ${sessionScope.user.lastName}<br/>
                                    </span>
                                </label>
                            </div>
                            Your cart is empty at the moment
                        </c:when>
                        <c:otherwise>
                            <div class="user-info">
                                <label>
                                    <span>
                                        Profile: ${sessionScope.user.role} <br/>
                                        ${sessionScope.user.firstName} ${sessionScope.user.lastName}<br/>
                                    </span>
                                </label>
                            </div>
                            You have ordered ${sessionScope.drugsOrdered} items.<br/>
                            Total price: ${total}
                        </c:otherwise>
                    </c:choose>

                </label>
            </div>
        </div>

    </div>

    <form name=LocaleRUChanging" action="controller" id="toRU">
        <input type="hidden" name="currentUrl" value="index.jsp"/>
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="ru"/>
    </form>
    <form name=LocaleENChanging" action="controller" id="toEN">
        <input type="hidden" name="currentUrl" value="index.jsp"/>
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="en"/>
    </form>
    <div class="btn-group">
        <button type="submit" class="btn " form="toEN" value="submit">${en_button}</button>
        <button type="submit" class="btn " form="toRU" value="submit">${ru_button}</button>
    </div>
</div>

</body>
</html>


