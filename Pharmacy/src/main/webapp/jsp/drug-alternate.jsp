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
    <fmt:setBundle basename="locale" var="locale"/>
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
            <ul class="nav navbar-nav" class="sidelist">

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
                            <a href="../jsp/login.jsp">
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
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="jumbotron">
                    <h3 align="center">
                        <label>
                            <c:choose>
                                <c:when test="${requestScope.alternating eq true}">
                                    <fmt:message key = "label.drug-alternating" bundle = "${locale}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key = "label.drug-adding" bundle = "${locale}"/>
                                </c:otherwise>
                            </c:choose>
                        </label>
                    </h3>
                    <div class="row">
                        <div class="col-sm-12" >
                            <form name="AltDrug"  action="/controller" class="alt-drug">
                                <input type="hidden" name="command" <c:choose>
                                    <c:when test="${requestScope.alternating eq true}">
                                        value="update-drug"
                                    </c:when>
                                    <c:otherwise>
                                        value="add-drug"
                                    </c:otherwise>
                                </c:choose>/>
                                <input type="hidden" name="drugId" value="${requestScope.drugId}"/>
                                <label><fmt:message key = "label.name" bundle = "${locale}"/><br/><input type="text" name="name" required value="${requestScope.name}"/></label><br/>
                                <label><fmt:message key = "label.dose" bundle = "${locale}"/><br/><input type="number" min="0" name="dose" required value="${requestScope.dose}"/></label><br/>
                                <label><fmt:message key = "label.price" bundle = "${locale}"/><br/><input type="number" min="0.01" step="0.01" name="price" required value="${requestScope.price}"/></label><br/> <!--FLOAT?-->
                                <label><fmt:message key = "label.prescription" bundle = "${locale}"/><br/><input type="number" min="0" max="1" name="prescription" value="${requestScope.prescription}"/></label><br/>
                                <input type="submit" name="submit" <c:choose>
                                    <c:when test="${requestScope.alternating eq true}">
                                        value="<fmt:message key = "button.update" bundle = "${locale}"/>"
                                    </c:when>
                                    <c:otherwise>
                                        value="<fmt:message key = "button.add" bundle = "${locale}"/>"
                                    </c:otherwise>
                                </c:choose>/>
                                <label>${infoMsg}</label>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <form name=LocaleRUChanging" action="/controller" id="toRU">
            <input type="hidden" name="currentUrl" value="jsp/drug-alternate.jsp"/>
            <input type="hidden" name="command" value="change-locale"/>
            <input type="hidden" name="locale" value="ru"/>
        </form>
        <form name=LocaleENChanging" action="/controller" id="toEN">
            <input type="hidden" name="currentUrl" value="jsp/drug-alternate.jsp"/>
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
