<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- 1. 다국어 메시지 번들 설정: root-context.xml에 설정한 경로와 일치해야 함 --%>
<fmt:setBundle basename="lang/messages" var="msg" scope="session" />

<%-- 2. 현재 언어 설정 확인 (세션에 없으면 기본값 en) --%>
<c:set var="currentLang" value="${sessionScope.currentLang}" />
<c:if test="${empty currentLang}">
    <c:set var="currentLang" value="en" />
</c:if>

<script>
    window.contextPath = '${pageContext.request.contextPath}';
    window.currentLang = '${currentLang}';
</script>

<!-- 외부 리소스 로드 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>

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
                <%-- data-value는 Controller에서 받는 lang 파라미터와 일치해야 함 --%>
                <div class="lang-opt ${currentLang eq 'en' ? 'active' : ''}" data-value="en">English</div>
                <div class="lang-opt ${currentLang eq 'ko' ? 'active' : ''}" data-value="ko">한국어</div>
                <div class="lang-opt ${currentLang eq 'sr' ? 'active' : ''}" data-value="sr">Српски</div>
            </div>
        </div>

        <%-- 테마 토글 (Dark/Light) --%>
        <div class="theme-switch-wrapper">
            <label class="theme-switch" for="darkModeToggle">
                <input type="checkbox" id="darkModeToggle">
                <span class="slider">
                    <i class="fas fa-sun icon-sun"></i>
                    <i class="fas fa-moon icon-moon"></i>
                </span>
            </label>
        </div>

        <%-- 사용자 정보 (필요 시 추가) --%>
        <c:if test="${not empty sessionScope.userInfo}">
            <span class="user-name-head">${sessionScope.userInfo.userName}</span>
        </c:if>

        <%-- 로그아웃 버튼 --%>
        <div class="logout-container">
            <a href="${pageContext.request.contextPath}/auth/logout" class="btn-logout-head" title="Logout">
                <i class="fas fa-power-off"></i>
            </a>
        </div>
    </div>
</header>