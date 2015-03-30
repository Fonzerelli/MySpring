<%--
  Created by IntelliJ IDEA.
  User: ivashyn
  Date: 18.03.15
  Time: 16:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Sign In Page</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<a href="<c:url value="/signUp"/>">Sign Up</a>
<form action="<c:url value="/j_spring_security_check"/>" method="post" >
    <center>
        <table style="margin-top: 5%" width="30%" border="1" cellspacing="0" cellpadding="4" align="center">
            <tbody>
            <tr>
                <td><label for="j_username">Login</label></td>
                <td><input id="j_username" name="j_username" type="text" /></td>
            </tr>
            <tr>
                <td><label for="j_password">Password</label></td>
                <td><input id="j_password" name="j_password" type="password" /></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Sign in"/></td>
            </tr>

            <h3><c:out value="${error}"/></h3>
            </tbody>
        </table>
    </center>
</form>
</body>
</html>
