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
    (function() {
        // 1. theme apply
        const theme = localStorage.getItem('theme') || 'dark';
        if (theme === 'light') document.documentElement.classList.add('light-mode');

        // 2. lang sync
        const savedLang = localStorage.getItem('preferredLang');
        const sessionLang = "${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}";
        const isChanging = new URLSearchParams(window.location.search).has('lang');

        // force lang sync
        if (savedLang && savedLang !== sessionLang && !isChanging) {
            const cp = window.location.pathname.split('/')[1];
            const contextPath = cp && !cp.includes('.') ? ("/" + cp) : "";
            window.location.href = contextPath + "/changeLang?lang=" + savedLang + "&referer=" + encodeURIComponent(window.location.pathname + window.location.search);
        }
    })();
</script>

//favicon
<link rel="shortcut icon" href="/favicon.ico" type="image/png">
<link rel="apple-touch-icon" href="../../favicon.ico">

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
                <div class="lang-opt" data-value="en">English</div>
                <div class="lang-opt" data-value="ko">한국어</div>
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