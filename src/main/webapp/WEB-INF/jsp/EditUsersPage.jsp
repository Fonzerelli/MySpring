<%--
  Created by IntelliJ IDEA.
  User: ivashyn
  Date: 18.03.15
  Time: 18:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="custom" uri="/WEB-INF/tag/customTag.tld" %>
<html>
<head>
    <title>Edit Users</title>
</head>
<body>
<jsp:include page="AdminHeaderPage.jsp" />

<a href="<c:url value="/getAddUserPage"/>">Add new user</a>
<br/>
<a href="<c:url value="/getLoginAuditPage"/>">Login Audit</a>
<br/><br/>
<custom:EditUsersTag usersList="${usersList}"/>
</body>
</html>
