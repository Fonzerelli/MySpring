<%--
  Created by ickis
  Date: 4/7/15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="custom" uri="/WEB-INF/tag/customTag.tld" %>
<html>
<head>
    <title>Login Audit</title>
</head>
<body>
<jsp:include page="AdminHeaderPage.jsp" />

<a href="<c:url value="/editAllUsers"/>">Edit Users</a>
<br/><br/>
<table width="80%" border="1" cellspacing="0" cellpadding="4" align="center">
    <tr>
        <td><b>Remote IP</b></td>
        <td><b>Login Time</b></td>
        <td><b>Logout Time</b></td>
        <td><b>Session Id</b></td>
        <td><b>Username</b></td>
        <td><b>Browser Info</b></td>
        <td></td>
    </tr>
    <c:forEach var="loginAudit" items="${loginAuditList}">
        <tr>
            <td>${loginAudit.remoteIP}</td>
            <td>${loginAudit.loginTime}</td>
            <td>${loginAudit.logoutTime}</td>
            <td>${loginAudit.sessionId}</td>
            <td>${loginAudit.username}</td>
            <td>${loginAudit.browserInfo}</td>
            <td><a href="deleteLoginAudit?id=${loginAudit.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
