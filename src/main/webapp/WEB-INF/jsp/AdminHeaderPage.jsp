<%--
  Created by IntelliJ IDEA.
  User: ivashyn
  Date: 19.03.15
  Time: 18:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<p align="right">
    Admin <c:out value="${currentSessionUser.lastName}"/> (<a href="<c:url value="/logout"/>">logout</a>)
</p>
<br/><br/>
</body>
</html>
