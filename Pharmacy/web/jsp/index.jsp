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
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Start Page</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="locale"/>
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

            <ul class="nav navbar-nav" id="sidelist">

                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'pharmacist'}">
                        <li>
                            <a href="/controller?command=to-add-drug">
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
                            <a href="../jsp/login.jsp">
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
                <h4><fmt:message key = "label.product-range" bundle = "${locale}"/></h4>
                ${emptyResultMsg}
                <c:if test="${requestScope.infoPrescriptionMsg ne null}">
                    <label>${requestScope.infoPrescriptionMsg}</label><br/>
                    <c:if test="${sessionScope.user.doctorId eq 0}">
                        <label class="no-doctor-msg"><fmt:message key = "msg.no-doctor" bundle = "${locale}"/></label>
                    </c:if>
                    <form name="AskPrescription" action="/controller">
                        <input type="hidden" name="command" value="request-prescription"/>
                        <input type="hidden" name="drugId" value="${requestScope.drugIdForPrescription}"/>
                        <input type="submit" value="<fmt:message key = "button.send-request" bundle = "${locale}"/>" <c:if test="${sessionScope.user.doctorId eq 0}">disabled</c:if>/>
                    </form>
                </c:if>

                <c:forEach var="drug" items="${sessionScope.drugList}">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row" >
                                <div class="col-sm-4">
                                    <label>${drug.name}, ${drug.dose}мг <c:if test="${drug.prescription eq 1}">
                                        <span class="glyphicon glyphicon-exclamation-sign" title="requires prescription"></span>
                                    </c:if> </label>
                                </div>
                                <div class="col-sm-2">
                                    <label>${drug.price} <fmt:message bundle="${locale}" key="label.currency"/> </label>
                                </div>
                                <div class="col-sm-4" id="to-card-button">
                                    <form name="AddToCardForm" action="/controller">
                                        <input type="hidden" name="command" value="add-to-card"/>
                                        <input type="hidden" name="price" value="${drug.price}"/>
                                        <input type="hidden" name="drugId" value="${drug.id}"/>
                                        <input type="number" id="amount" class="amount-of-drug"
                                               required min="1" name="amount" value=""
                                               placeholder="<fmt:message bundle="${locale}" key="label.amount"/>"
                                               <c:if test="${sessionScope.user eq null}">disabled</c:if>/>
                                        <input type="submit" name="submit" class="submit-amount"
                                               value="<fmt:message bundle="${locale}" key="button.add-to-cart"/>"
                                               <c:if test="${sessionScope.user eq null}">disabled</c:if>/>
                                    </form>
                                </div>
                                <div class="col-sm-2">
                                    <form name="AlternateDrug" action="/controller">
                                        <input type="hidden" name="command" value="to-alternating-drug"/>
                                        <input type="hidden" name="drugId" value="${drug.id}"/>
                                        <input type="hidden" name="name" value="${drug.name}"/>
                                        <input type="hidden" name="price" value="${drug.price}"/>
                                        <input type="hidden" name="prescription" value="${drug.prescription}"/>
                                        <input type="hidden" name="dose" value="${drug.dose}"/>
                                        <input type="submit" name="submit"
                                               value="<fmt:message bundle="${locale}" key="locale.alternate"/>"
                                               <c:if test="${sessionScope.user eq null}">disabled placeholder="Only for pharmacists"</c:if>/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <hr/>

                <div class="back-forward">
                    <form name="Back" class="page-control" action="/controller">
                        <input type="hidden" name="command" value="seek-drug"/>
                        <input type="hidden" name="name" value="${sessionScope.name}"/>
                        <input type="hidden" name="pageNumber" value="${sessionScope.pageNumber - 1}"/>
                        <input type="hidden" name="elements" value="7"/>
                        <input type="submit" name="back" value="<fmt:message bundle="${locale}" key="button.previous-page"/>"
                               <c:if test="${sessionScope.pageNumber eq 1}">disabled </c:if>/>
                    </form>
                    <span><label> ${sessionScope.pageNumber} </label></span>
                    <form name="Forward" class="page-control" action="/controller">
                        <input type="hidden" name="command" value="seek-drug"/>
                        <input type="hidden" name="name" value="${sessionScope.name}"/>
                        <input type="hidden" name="pageNumber" value="${sessionScope.pageNumber + 1}"/>
                        <input type="hidden" name="elements" value="7"/>
                        <input type="submit" name="next" value="<fmt:message bundle="${locale}" key="button.next-page"/>"
                               <c:if test="${sessionScope.pageNumber eq sessionScope.amountOfPages}">disabled </c:if>/>
                    </form>
                </div>
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
                                    <fmt:message key = "label.profile" bundle = "${locale}"/>: <fmt:message key = "label.${sessionScope.user.role}" bundle = "${locale}"/><br/>
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
                            <br/><label>${requestScope.doctorMsg}</label>
                        </c:otherwise>
                    </c:choose>

                </label>
            </div>
        </div>

    </div>

    <form name=LocaleRUChanging" action="/controller" id="toRU">
        <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="ru-RU"/>
    </form>
    <form name=LocaleENChanging" action="/controller" id="toEN">
        <input type="hidden" name="currentUrl" value="jsp/index.jsp"/>
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="en-EN"/>
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


