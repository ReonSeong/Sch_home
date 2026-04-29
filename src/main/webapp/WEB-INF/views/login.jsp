<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="currentLang" value="${empty sessionScope.currentLang ? pageContext.response.locale.language : sessionScope.currentLang}" />
<fmt:setLocale value="${currentLang}" scope="session"/>
<fmt:setBundle basename="lang.messages" var="msg" scope="request" />

<!DOCTYPE html>
<html lang="${currentLang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chef Master - Login</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login.css">


    <script>
        window.contextPath = "${pageContext.request.contextPath}";

        // 테마 즉시 적용 (깜빡임 방지)
        (function() {
            const theme = localStorage.getItem('theme') || 'dark';
            document.documentElement.classList.toggle('light-mode', theme === 'light');
        })();
    </script>

</head>

<body class="dark-mode">

<%@ include file="common/header.jsp" %>

<div class="background-blobs">
    <div class="blob1"></div>
    <div class="blob2"></div>
</div>

<div class="main-container">
    <div class="login-box glass">
        <h2><fmt:message key="login.title" bundle="${msg}" /></h2>
        <p class="subtitle"><fmt:message key="login.subtitle" bundle="${msg}" /></p>

        <c:if test="${param.error eq 'invalid'}">
            <p class="error-msg"><fmt:message key="login.error" bundle="${msg}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="input-group">
                <i class="fas fa-user icon"></i>
                <input type="text" name="userId" value="matfia" placeholder="<fmt:message key="login.placeholder.id" bundle="${msg}" />" required>
            </div>
            <div class="input-group">
                <i class="fas fa-lock icon"></i>
                <input type="password" name="userPw" id="userPw" value="1234" placeholder="<fmt:message key="login.placeholder.pw" bundle="${msg}" />" required>
                <i class="fas fa-eye-slash toggle-password" id="togglePassword"></i>
            </div>
            <button type="submit" class="login-btn"><fmt:message key="login.btn" bundle="${msg}" /></button>
        </form>

        <div class="footer-links" style="margin-top: 20px;">
            <fmt:message key="login.noaccount" bundle="${msg}" /> <a href="#"><fmt:message key="login.signup" bundle="${msg}" /></a>
        </div>
    </div>
</div>

<script>
    // login.jsp 전용: 비밀번호 보이기/숨기기
    document.getElementById('togglePassword').addEventListener('click', function() {
        const pwInput = document.getElementById('userPw');
        const isPw = pwInput.type === 'password';
        pwInput.type = isPw ? 'text' : 'password';
        this.classList.toggle('fa-eye-slash', !isPw);
        this.classList.toggle('fa-eye', isPw);
    });
</script>
</body>
</html>