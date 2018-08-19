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
    <fmt:setBundle basename="locale" var="locale"/>
    <fmt:message bundle="${locale}" key="button.change-locale.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="button.change-locale.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="label.profile" var="profile"/>
    <fmt:message bundle="${locale}" key="button.quit" var="log_out"/>
    <fmt:message bundle="${locale}" key="button.login" var="log_in"/>
    <fmt:message bundle="${locale}" key="label.search" var="search"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/default.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">

    <c:if test="${sessionScope.user eq null}">
        <c:redirect url="login.jsp"/>
    </c:if>

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

    <div class="row">
        <div class="col-sm-9" >
            <div class="jumbotron">

                <h4><fmt:message key = "label.prescription-requests" bundle = "${locale}"/></h4>
                <c:forEach var="prescription" items="${prescriptions}">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class = "row">
                                <div class="col-sm-1">${prescription.id}</div>
                                <div class="col-sm-2">${prescription.drugName}</div>
                                <div class="col-sm-2">${prescription.customerFirstName}</div>
                                <div class="col-sm-2">${prescription.customerLastName}</div>
                                <div class="col-sm-2">
                                    <form name="ApprovePrescription" action="/controller">
                                        <input type="hidden" name="command" value="approve-prescription"/>
                                        <input type="hidden" name="prescriptionId" value="${prescription.id}"/>
                                        <input type="submit" name="submit" value="<fmt:message key = "button.approve" bundle = "${locale}"/>"/>
                                    </form>
                                    <form name="DenyPrescription" action="/controller">
                                        <input type="hidden" name="command" value="deny-prescription"/>
                                        <input type="hidden" name="prescriptionId" value="${prescription.id}"/>
                                        <input type="submit" name="submit" value="<fmt:message key = "button.deny" bundle = "${locale}"/>"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="sideblock">
                <label>

                    <c:choose>
                        <c:when test="${sessionScope.user eq null}">
                            <fmt:message key = "msg.please-enter" bundle = "${locale}"/>
                        </c:when>

                        <c:otherwise>
                            <div class="user-info">
                                <span>
                                    <fmt:message key = "label.profile" bundle = "${locale}"/>: <fmt:message key = "label.${sessionScope.user.role}" bundle = "${locale}"/> <br/>
                                    ${sessionScope.user.firstName} ${sessionScope.user.lastName}<br/>
                                </span>
                            </div>
                            <c:choose>
                                <c:when test="${sessionScope.total eq 0}">
                                    <fmt:message key = "msg.empty-card" bundle = "${locale}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key = "label.ordered-items" bundle = "${locale}"/> ${sessionScope.drugsOrdered}<br/>
                                    <fmt:message key = "label.total" bundle = "${locale}"/>: ${sessionScope.total}
                                    <hr/>
                                    <form name="ApprovingOrder" action="/controller">
                                        <input type="hidden" name="command" value="approve-order" />
                                        <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
                                        <input type="submit" value="<fmt:message key = "button.approve-order" bundle = "${locale}"/>"/>
                                    </form>
                                    <form name="DenyOrder" action="/controller">
                                        <input type="hidden" name="command" value="deny-order"/>
                                        <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
                                        <input type="submit" value="<fmt:message key = "button.deny-order" bundle = "${locale}"/>"/>
                                    </form>
                                </c:otherwise>
                            </c:choose>

                            <c:if test="${sessionScope.user.doctorId eq 0}">
                                <hr/>
                                <fmt:message key = "msg.cant-request" bundle = "${locale}"/><br/><br/>
                                <c:choose>
                                    <c:when test="${requestScope.settingDoctor ne true}">
                                        <form name="ToSettingDoctor" action="/controller">
                                            <input type="hidden" name="command" value="to-setting-doctor"/>
                                            <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
                                            <input type="submit" value="<fmt:message key = "button.set-doctor" bundle = "${locale}"/>"/>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form name="SettingDoctor" action="/controller">
                                            <input type="hidden" name="command" value="set-doctor"/>
                                            <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
                                            <input type="text" name="doctorFirstName" value="" placeholder="First name"/>
                                            <input type="text" name="doctorLastName" value="" placeholder="Last name"/><br/><br/>
                                            <input type="submit" value="<fmt:message key = "button.set-doctor" bundle = "${locale}"/>"/>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <label>${requestScope.doctorMsg}</label>
                        </c:otherwise>
                    </c:choose>

                </label>
            </div>
        </div>

    </div>

    <form name=LocaleRUChanging" action="/controller" id="toRU">
        <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="ru"/>
    </form>
    <form name=LocaleENChanging" action="/controller" id="toEN">
        <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
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

</body>
</html>