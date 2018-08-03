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
    <title>Start Page</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="main.resources.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="locale.changelocbutton.ru" var="ru_button"/>
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
    <%--.sideblock {--%>
        <%--background-color: #F8F8F8;--%>
        <%--padding: 5px;--%>
        <%--text-align: center;--%>
    <%--}--%>

<%--</style>--%>
<body>
<div class="container">

    <!--  <h1>Hello World!</h1>
      <p>Resize the browser window to see the effect.</p> -->
    <nav class="navbar navbar-default" class="flex-container">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp">Online Pharmacy</a>
            </div>
            <ul class="nav navbar-nav" class="sidelist">
                <!--<li class="active"><a href="#">Home</a></li>-->
                <li>
                    <form>
                        <div class="form-group">
                            <input type="text" class="form-control" id="srch" placeholder="Search">
                        </div>
                    </form>
                </li>
                <c:if test="${sessionScope.user.role eq 'customer'}">
                    <li>
                        <a href="#">
                            <label>Order
                                <span class="glyphicon glyphicon-shopping-cart"></span>
                                <span class="badge">${sessionScope.drugsOrdered}</span>
                            </label>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="profile.jsp">
                        <label>Profile</label>
                    </a>
                </li>
                <%--<li><button type="submit" class="btn " form="toEN" value="submit"><label>${en_button}</label></button></li>--%>
                <li><a href="#"><label>Quit</label></a></li>
            </ul>
        </div>
    </nav>

    <p>The columns will automatically stack on top of each other when the screen is less than 768px wide.</p>
    <div class="row">
        <div class="col-sm-9" >

            <div class="jumbotron">
                <%--</hr>--%>
                <%--</hr>--%>
                <%--<form name="ToHomePage" method="GET" action="controller">--%>
                    <%--<input type="hidden" name="command" value="home-page" />--%>
                    <%--<input type="submit" name="submit" value="Submit" />--%>
                <%--</form>--%>
                <%--</hr>--%>
                <%--</hr>--%>
                <%--<form name="AddToCard" method="GET" action="controller">--%>
                    <%--<input type="hidden" name="command" value="add-to-card" />--%>
                    <%--<label>DrugId:    <input type="text" name="drugId" value="" /></label><br/><br/>--%>
                    <%--<label>Amount:    <input type="text" name="amount" value="" /></label><br/><br/>--%>
                    <%--${infoCardMsg}--%>
                    <%--<input type="submit" name="submit" value="Add Drug" />--%>
                <%--</form>--%>
                <%--</hr>--%>             <div>
                    <label>User data</label>
                    <c:choose>
                        <c:when test="${alternating != 'true'}">
                            <div>
                                <label>Login: ${sessionScope.user.login}</label><br/>
                                <label>First name: ${sessionScope.user.firstName}</label><br/>
                                <label>Last name: ${sessionScope.user.lastName}</label><br/>
                                <label>email: ${sessionScope.user.mail}</label><br/>
                                <label>Role: ${sessionScope.user.role}</label><br/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form name="UpdatingProfile" action="controller">
                                <input type="hidden" name="command" value="update-user">
                                <label>Login: <input type="text" name="login" value="${sessionScope.user.login}"></label><br/>
                                <label>First name: <input type="text" name="firstName" value="${sessionScope.user.firstName}"></label><br/>
                                <label>Last name: <input type="text" name="lastName" value="${sessionScope.user.lastName}"></label><br/>
                                <label>email: <input type="text" name="email" value="${sessionScope.user.mail}"></label><br/>
                                <label>Role: ${sessionScope.user.role}</label><br/>
                                <input type="submit" name="submit" value="Update">
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${alternating eq 'true'}">
                        <form name="ChangeData" action="controller">
                            <input type="hidden" name="command" value="alternate-profile" />
                            <input type="submit" name="submit" value="Update account" />
                        </form>
                    </c:if>
                </div>
                </hr>
                <form name="CurrentOrder" action="controller">
                    <input type="hidden" name="command" value="current-order" />
                    <input type="submit" name="submit" value="Show" />
                </form>
                </hr>
                    <c:forEach var="drugEntry" items="${currentOrder}">
                        <%--Drug: ${drugEntry.key}, Amount: ${drugEntry.value}--%>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class = "row">
                                    <div class="col-sm-3" style="display: inline-block">
                                            ${drugEntry.key.name}
                                    </div>
                                    <div class="col-sm-3" style="display: inline-block">
                                        <label>${drugEntry.key.price} б.р.</label>
                                    </div>
                                    <div class="col-sm-3" style="display: inline-block">
                                        <label>Amount: ${drugEntry.value}</label>
                                    </div>
                                    <div class="col-sm-3" style="display: inline-block" id="to-card-button">
                                        <form name="RemoveFromCartForm" action="controller">
                                            <input type="hidden" name="command" value="remove-from-cart"/>
                                            <input type="hidden" name="price" value="${drugEntry.key.price}"/>
                                            <input type="hidden" name="drugId" value="${drugEntry.key.id}"/>
                                            <input type="hidden" name="amount" value="${drugEntry.value}" />
                                            <input type="submit" name="submit" value="Remove"/>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </hr>
                <form name="ApprovingOrder" method="GET" action="controller">
                    <input type="hidden" name="command" value="approve-order" />
                    <input type="submit" name="submit" value="Approve" />
                    ${infoOrder}
                </form>
                </hr>
                </hr>
                <form name="ListOrders" method="GET" action="controller">
                    <input type="hidden" name="command" value="list-orders" />
                    <input type="submit" name="submit" value="Show My Orders" />
                    ${errorMsg}
                    <c:forEach var="order" items="${orders}">
                        <%--<br/><c:out value="${drugEntry.key}"/>--%>
                        Order: date: ${order.orderDate}, customer: ${order.customerId}
                    </c:forEach>
                </form>

            </div>

            <%--<div class="col-sm-2">.col-sm-4</div>--%>
        </div>

        <div class="col-sm-3">
            <div class="sideblock">
                <label>
                    Здесь тоже должно быть что-нибудь прикольное
                </label>
            </div>
        </div>

    </div>

    <form name=LocaleRUChanging" action="controller" id="toRU">
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="ru"/>
    </form>
    <form name=LocaleENChanging" action="controller" id="toEN">
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
