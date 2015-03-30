<%--
  Created by IntelliJ IDEA.
  User: ivashyn
  Date: 18.03.15
  Time: 16:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <span style="text-align: center">
    <h1 style="margin-top: 5%">Hello, ${currentSessionUser.firstName} !</h1>
    <h6>Click <a href="logout">here</a> to logout</h6>
</span>
</body>
</html>
