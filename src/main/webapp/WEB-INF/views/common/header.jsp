<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 다국어 번들 설정 --%>
<c:set var="currentLang" value="${empty sessionScope.currentLang ? pageContext.response.locale.language : sessionScope.currentLang}" />
<fmt:setLocale value="${currentLang}" scope="session" />
<fmt:setBundle basename="lang.messages" var="msg" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<header class="main-header">
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/dashboard" style="text-decoration: none;">
            <h1 class="com-name">Chef Master</h1>
        </a>
    </div>

    <div class="header-right">
        <%-- 언어 선택 드롭다운 --%>
        <div class="lang-dropdown-wrapper" id="langDropdown">
            <div class="lang-selected">
                <i class="fas fa-globe"></i>
                <span id="currentLangText">
                    <c:choose>
                        <c:when test="${currentLang eq 'ko'}">KO</c:when>
                        <c:when test="${currentLang eq 'sr'}">SR</c:when>
                        <c:otherwise>EN</c:otherwise>
                    </c:choose>
                </span>
                <i class="fas fa-chevron-down arrow-small"></i>
            </div>
            <div class="lang-options glass">
                <div class="lang-opt" data-value="en">English</div>
                <div class="lang-opt" data-value="ko">한국어</div>
                <div class="lang-opt" data-value="sr">Српски</div>
            </div>
        </div>

        <%-- 테마 토글 --%>
        <div class="theme-switch-wrapper">
            <label class="theme-switch" for="darkModeToggle">
                <input type="checkbox" id="darkModeToggle">
                <span class="slider">
                    <i class="fas fa-sun icon-sun"></i>
                    <i class="fas fa-moon icon-moon"></i>
                </span>
            </label>
        </div>

        <%-- 로그아웃 버튼 (세션 정보 확인) --%>
        <c:if test="${not empty sessionScope.user}">
            <div class="logout-container">
                <a href="${pageContext.request.contextPath}/auth/logout" class="btn-logout-head" title="Logout">
                    <i class="fas fa-power-off"></i>
                </a>
            </div>
        </c:if>
    </div>
</header>