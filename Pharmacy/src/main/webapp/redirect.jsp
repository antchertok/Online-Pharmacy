<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: --
  Date: 23.07.2018
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <fmt:setLocale value="en-EN"/>
    <c:redirect url="/controller">
        <c:param name="command" value="seek-drug"/>
        <c:param name="name" value=""/>
        <c:param name="pageNumber" value="1"/>
        <c:param name="elements" value="7"/>
    </c:redirect>
</body>
</html>
