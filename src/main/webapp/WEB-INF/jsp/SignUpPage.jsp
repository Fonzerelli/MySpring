<%--
  Created by ickis
  Date: 3/30/15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<html>
<head>
    <title>Sign Up Page</title>
</head>
<body>
<a href="<c:url value="/signIn"/>">Sign In</a>

<div class="userDiv">
    <c:url var="signUp" value="/signUp"/>
    <springForm:form action="${signUp}" method="post"
                     commandName="newUser">

        <h1>Sign Up</h1>
        <table>
            <tr>
                <td>Login</td>
                <td><springForm:input path="login"/><sup
                        style="color: red"> *</sup></td>
                <td><springForm:errors path="login" cssClass="error"/></td>
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
                                    newUser.roleId}">selected</c:if>
                                    value="${roleForChoosing.id}">
                                    ${roleForChoosing.name}</option>
                        </c:forEach>
                    </select><sup style="color: red"> *</sup>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="OK"/>
                    <a href="<c:url value="/signIn"/>">
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
            <%
                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcY9gQTAAAAADBwkIV4tpdtHfhnQWahN2dCFU4r", "6LcY9gQTAAAAABhSm-ktg0sdm0Dqcv4aZDCQLRJD", false);
                out.print(c.createRecaptchaHtml(null, null));
            %>
            <h3><c:out value="${captchaError}"/></h3>
        </table>
    </springForm:form>
</div>
</body>
</html>
