<%--
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 6:26
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/chef-hat.png">
<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/images/chef-hat.png">

<header class="main-header">
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/dashboard" style="text-decoration: none;">
            <h1 class="com-name">Chef Master</h1>
        </a>
    </div>

    <div class="header-right">
        <div class="lang-dropdown-wrapper" id="langDropdown">
            <div class="lang-selected">
                <i class="fas fa-globe"></i>
                <span id="currentLangText">KO</span>
                <i class="fas fa-chevron-down arrow-small"></i>
            </div>
            <div class="lang-options glass">
                <div class="lang-opt" data-value="ko">한국어</div>
                <div class="lang-opt" data-value="en">English</div>
                <div class="lang-opt" data-value="sr">Српски</div>
            </div>
        </div>

        <div class="theme-switch-wrapper">
            <label class="theme-switch" for="darkModeToggle">
                <input type="checkbox" id="darkModeToggle">
                <span class="slider">
                    <i class="fas fa-sun icon-sun"></i>
                    <i class="fas fa-moon icon-moon"></i>
                </span>
            </label>
        </div>

        <c:if test="${not empty sessionScope.user and !fn:contains(pageContext.request.requestURI, 'login')}">
            <div class="logout-container">
                <a href="${pageContext.request.contextPath}/auth/logout" class="btn-logout-head" title="Logout">
                    <i class="fas fa-power-off"></i>
                </a>
            </div>
        </c:if>
    </div>
</header>