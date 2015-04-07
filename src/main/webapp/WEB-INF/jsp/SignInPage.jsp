<%--
  Created by IntelliJ IDEA.
  User: ivashyn
  Date: 18.03.15
  Time: 16:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Sign In Page</title>
    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>--%>
    <script type="text/javascript">
        function setFocus() {
            document.getElementById("j_username").value = '';
            document.getElementById("j_password").value = '';
            document.getElementById("j_username").focus();
        }

        function checkBrowserInfo() {
            var ua = navigator.userAgent, tem,
                    M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*([\d\.]+)/i) || [];
            if (/trident/i.test(M[1])) {
                tem = /\brv[ :]+(\d+(\.\d+)?)/g.exec(ua) || [];
                return 'IE ' + (tem[1] || '');
            }
            M = M[2] ? [M[1], M[2]] : [navigator.appName, navigator.appVersion, '-?'];
            if ((tem = ua.match(/version\/([\.\d]+)/i)) != null) M[2] = tem[1];
            return M.join(' ');
        }

        function initBrowserVersion(){
            document.getElementById("browserVersionId").value = checkBrowserInfo();
        }
    </script>
</head>
<body onload="setFocus();initBrowserVersion();">
<%--<spring:eval expression="@recaptchaProperties.getProperty('public.key')"  var="publicKey"/>--%>

<a href="<c:url value="/signUp"/>">Sign Up</a>

<form action="<c:url value="/j_spring_security_check"/>" method="post" >
    <center>
        <%
//            boolean captchaIsNecessary = (Boolean) request.getSession().getAttribute("captchaIsNecessary");
//            if (captchaIsNecessary) {
//                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LfxnAQTAAAAACfEPgLLgcbUl2L_JoGeEyGO_1OZ", "6LfxnAQTAAAAAG4fAaHObKANQlC2ahQTJxlNCDiC", false);
//                out.print(c.createRecaptchaHtml(null, null));
//            }
        %>
        <input type="hidden" value="" id="browserVersionId" name="browserVersion"/>
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
                <td><input type="submit" value="Sign in"/>
                </td>
            </tr>

            <h3><c:out value="${error}"/></h3>
            <%--<h3><c:out value="${recaptchaProperties['public.key']}"/></h3>--%>
            <h3><c:out value="${captcha_error}"/></h3>
            </tbody>
        </table>
    </center>
</form>
</body>
</html>
