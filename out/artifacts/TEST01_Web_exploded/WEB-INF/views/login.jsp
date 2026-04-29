<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 다국어 처리를 위한 설정 (언어 선택 기능) --%>
<c:choose>
    <%-- URL에 lang 파라미터가 있으면 최우선 적용 --%>
    <c:when test="${not empty param.lang}">
        <fmt:setLocale value="${param.lang}" scope="session"/>
    </c:when>
    <%-- 파라미터는 없지만 세션에 이미 저장된 언어가 있다면 그것을 사용 --%>
    <c:when test="${not empty sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}">
        <fmt:setLocale value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}" scope="session"/>
    </c:when>
    <%-- 둘 다 없으면 기본값 en --%>
    <c:otherwise>
        <fmt:setLocale value="en" scope="session"/>
    </c:otherwise>
</c:choose>

<%-- 2. 번들 로드 (scope를 session이나 request로 명확히 지정) --%>
<fmt:setBundle basename="lang.messages" var="msg" scope="request" />

<link rel="stylesheet" href="../../css/style.css">
<link rel="stylesheet" href="../../css/login.css">
<script src="${pageContext.request.contextPath}/js/common.js"></script>

<!DOCTYPE html>
<html lang="${not empty param.lang ? param.lang : 'en'}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Matfia Admin - Login</title>

    <%-- CSS 파일 연결 --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

    <%-- Font Awesome (아이콘용) --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="dark-mode">

<%-- 공통 헤더 포함 (회사명, 언어설정, 다크모드) --%>
<%@ include file="header.jsp" %>

<div class="background-blobs">
    <div class="blob1"></div>
    <div class="blob2"></div>
</div>

<div class="main-container">
    <%-- login box --%>
    <div class="login-box glass">
        <h2><fmt:message key="login.title" bundle="${msg}" /></h2>
        <p class="subtitle"><fmt:message key="login.subtitle" bundle="${msg}" /></p>

        <%-- ERR MSG --%>
        <c:if test="${param.error eq 'invalid'}">
            <p class="error-msg"><fmt:message key="login.error" bundle="${msg}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="input-group">
                <i class="fas fa-user icon"></i>
                <input type="text" name="userId" placeholder="<fmt:message key="login.placeholder.id" bundle="${msg}" />" required>
            </div>
            <div class="input-group">
                <i class="fas fa-lock icon"></i>
                <input type="password" name="userPw" id="userPw" placeholder="<fmt:message key="login.placeholder.pw" bundle="${msg}" />" required>
                <i class="fas fa-eye-slash toggle-password" id="togglePassword"></i>
            </div>

            <button type="submit" class="login-btn"><fmt:message key="login.btn" bundle="${msg}" /></button>
        </form>

        <%-- SOCIAL LOGIN EXAMPLE --%>
        <div class="social-login">
            <div class="social-btn"><i class="fab fa-facebook-f"></i> Facebook</div>
            <div class="social-btn"><i class="fab fa-google"></i> Google</div>
            <div class="social-btn"><i class="fab fa-apple"></i> Apple</div>
        </div>

        <div class="footer-links">
            <fmt:message key="login.noaccount" bundle="${msg}" /> <a href="#"><fmt:message key="login.signup" bundle="${msg}" /></a>
        </div>
    </div>
</div>

<script>
    // 1. Theme change logic
    const themeToggle = document.querySelector('#darkModeToggle');
    const body = document.body;

    // Check localStorage
    const currentTheme = localStorage.getItem('theme');
    if (currentTheme === 'light') {
        body.classList.remove('dark-mode');
        body.classList.add('light-mode');
        if(themeToggle) themeToggle.checked = false;
    } else {
        body.classList.add('dark-mode');
        if(themeToggle) themeToggle.checked = true;
    }

    if(themeToggle) {
        themeToggle.addEventListener('change', (e) => {
            if (e.target.checked) {
                body.classList.replace('light-mode', 'dark-mode');
                localStorage.setItem('theme', 'dark');
            } else {
                body.classList.replace('dark-mode', 'light-mode');
                localStorage.setItem('theme', 'light');
            }
        });
    }

    // 2. PW show option
    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('userPw');

    togglePassword.addEventListener('click', () => {
        const isPassword = passwordInput.type === 'password';
        passwordInput.type = isPassword ? 'text' : 'password';
        togglePassword.classList.toggle('fa-eye-slash', !isPassword);
        togglePassword.classList.toggle('fa-eye', isPassword);
    });
</script>
</body>
</html>