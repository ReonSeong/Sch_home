<%--
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 6:26
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    // before loading check local for theme
    (function() {
        const theme = localStorage.getItem('theme');
        if (theme === 'light') {
            document.body.classList.add('light-mode');
            document.body.classList.remove('dark-mode');
        } else {
            document.body.classList.add('dark-mode');
            document.body.classList.remove('light-mode');
        }
    })();
</script>

<header class="main-header">
    <div class="header-left">
        <h1 class="com-name">Chef Master</h1>
    </div>

    <div class="header-right">
        <div class="lang-selector">
            <a href="?lang=ko" class="${(empty param.lang or param.lang eq 'ko') ? 'active' : ''}">
                <span class="flag">🇰🇷</span> KO
            </a>
            <a href="?lang=en" class="${param.lang eq 'en' ? 'active' : ''}">
                <span class="flag">🇺🇸</span> EN
            </a>
            <a href="?lang=sr" class="${param.lang eq 'sr' ? 'active' : ''}">
                <span class="flag">🇷🇸</span> SR
            </a>
        </div>

        <div class="theme-switch-wrapper">
            <label class="theme-switch" for="darkModeToggle">
                <input type="checkbox" id="darkModeToggle" />
                <div class="slider">
                    <i class="fas fa-sun icon-sun"></i>
                    <i class="fas fa-moon icon-moon"></i>
                </div>
            </label>
        </div>

        <c:if test="${not empty sessionScope.user and !pageContext.request.requestURI.contains('login')}">
            <div class="logout-wrapper">
                <a href="${pageContext.request.contextPath}/auth/logout" class="btn-logout-head" title="Logout">
                    <i class="fas fa-power-off"></i>
                </a>
            </div>
        </c:if>
    </div>
</header>