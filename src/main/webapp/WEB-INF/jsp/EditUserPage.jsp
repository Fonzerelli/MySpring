<%--
  Created by IntelliJ IDEA.
  User: ivashyn
  Date: 19.03.15
  Time: 14:11
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>
<html>
<head>
    <title>Edit User</title>
    <link href="${pageContext.request.contextPath}/css/style.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="AdminHeaderPage.jsp"/>

<div class="userDiv">
    <c:url var="saveUser" value="/saveUser"/>
    <springForm:form action="${saveUser}" method="post"
                     commandName="editedUser">
        <input type="hidden" name="id" value="${editedUser.id}"/>

        <h1>Edit user</h1>
        <table>
            <tr>
                <td>Login</td>
                <td><springForm:input path="login" readonly="true"/>
                    </span></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><springForm:password path="password"/><sup
                        style="color: red"> *</sup></td>
                <td><springForm:errors path="password" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Password again</td>
                <td><springForm:password path="confirmPassword"/><sup
                        style="color: red"> *</sup></td>
                <td><springForm:errors path="confirmPassword"
                                       cssClass="error"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><springForm:input path="email"/><sup style="color: red">
                    *</sup></td>
                <td><springForm:errors path="email" cssClass="error"/></td>
            </tr>
            <tr>
                <td>First name</td>
                <td><springForm:input path="firstName"/><sup style="color: red">
                    *</sup></td>
                <td><springForm:errors path="firstName" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Last name</td>
                <td><springForm:input path="lastName"/><sup style="color: red">
                    *</sup></td>
                <td><springForm:errors path="lastName" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td><springForm:input path="birthday"/><sup style="color: red">
                    *</sup></td>
                <td><springForm:errors path="birthday" cssClass="error"/></td>
            </tr>
            <tr>
                <td>
                    Role
                </td>
                <td>
                    <select name="roleId" style="width: 90%;">
                        <c:forEach items="${roles}" var="roleForChoosing">
                            <option
                                    <c:if test="${roleForChoosing.id ==
                                    editedUser.roleId}">selected</c:if>
                                    value="${roleForChoosing.id}">
                                    ${roleForChoosing.name}</option>
                        </c:forEach>
                    </select><sup style="color: red"> *</sup>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="OK"/>
                    <a href="<c:url value="/editAllUsers"/>">
                        <input type="button" value="Cancel"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <c:out value="${error}"/>
                </td>
            </tr>
        </table>
    </springForm:form>
</div>
</body>
</html>
