<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 1. 로그인 세션 체크 --%>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/WEB-INF/views/login.jspiews/login.jsp" />
</c:if>

<%-- 2. 언어 설정 (에러 없는 태그 방식) --%>
<c:set var="currentLang" value="${empty sessionScope.lang ? 'ko' : sessionScope.lang}" />
<fmt:setLocale value="${currentLang}" scope="session" />
<fmt:setBundle basename="lang.messages" var="msg" />

<!DOCTYPE html>
<html lang="${currentLang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%-- 타이틀도 다국어 적용 --%>
    <title>Chef Master - <fmt:message key="menu.summary" bundle="${msg}" /></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="dark-mode">

<%@ include file="header.jsp" %>

<div class="background-blobs">
    <div class="blob1"></div>
    <div class="blob2"></div>
</div>

<div class="dashboard-container">

    <%-- [데스크톱 전용] 좌측 사이드바 --%>
    <aside class="sidebar glass">
        <div class="sidebar-header">
            <div class="user-info">
                <i class="fas fa-user-circle"></i>
                <div>
                    <p class="u-name">${sessionScope.user.userName}</p>
                    <p class="u-role">Master Admin</p>
                </div>
            </div>
        </div>

        <nav class="side-nav">
            <a href="${pageContext.request.contextPath}/WEB-INF/views/dashboard.jsp" class="nav-item active">
                <i class="fas fa-gauge-high"></i><span><fmt:message key="menu.summary" bundle="${msg}" /></span>
            </a>
            <a href="${pageContext.request.contextPath}/table" class="nav-item">
                <i class="fas fa-border-all"></i> <span><fmt:message key="menu.table" bundle="${msg}" /></span>
            </a>
            <a href="${pageContext.request.contextPath}/menu" class="nav-item">
                <i class="fas fa-utensils"></i> <span><fmt:message key="menu.manage" bundle="${msg}" /></span>
            </a>
            <a href="${pageContext.request.contextPath}/sales" class="nav-item">
                <i class="fas fa-chart-line"></i> <span><fmt:message key="menu.sales" bundle="${msg}" /></span>
            </a>
            <a href="${pageContext.request.contextPath}/stock" class="nav-item">
                <i class="fas fa-boxes-stacked"></i> <span><fmt:message key="menu.stock" bundle="${msg}" /></span>
            </a>
            <a href="${pageContext.request.contextPath}/customer" class="nav-item">
                <i class="fas fa-users"></i> <span><fmt:message key="menu.customer" bundle="${msg}" /></span>
            </a>
            <a href="${pageContext.request.contextPath}/auth" class="nav-item">
                <i class="fas fa-key"></i> <span><fmt:message key="menu.auth" bundle="${msg}" /></span>
            </a>
        </nav>
        <div class="sidebar-footer">© 2026 ReonQ</div>
    </aside>

    <%-- [모바일 전용] 상단 셀렉트 메뉴 --%>
    <div class="mobile-menu-wrapper">
        <div class="custom-dropdown glass" id="mobileDropdown">
            <div class="dropdown-selected">
                <i class="fas fa-bars-staggered"></i>
                <span id="selectedText"><fmt:message key="menu.summary" bundle="${msg}" /></span>
                <i class="fas fa-chevron-down arrow"></i>
            </div>
            <div class="dropdown-options glass">
                <div class="option active" data-value="${pageContext.request.contextPath}/dashboard.jsp"><fmt:message key="menu.summary" bundle="${msg}" /></div>
                <div class="option" data-value="${pageContext.request.contextPath}/table"><fmt:message key="menu.table" bundle="${msg}" /></div>
                <div class="option" data-value="${pageContext.request.contextPath}/sales"><fmt:message key="menu.sales" bundle="${msg}" /></div>
                <div class="option" data-value="${pageContext.request.contextPath}/menu"><fmt:message key="menu.manage" bundle="${msg}" /></div>
                <div class="option" data-value="${pageContext.request.contextPath}/stock"><fmt:message key="menu.stock" bundle="${msg}" /></div>
                <div class="option" data-value="${pageContext.request.contextPath}/customer"><fmt:message key="menu.customer" bundle="${msg}" /></div>
                <div class="option" data-value="${pageContext.request.contextPath}/auth"><fmt:message key="menu.auth" bundle="${msg}" /></div>
            </div>
        </div>
    </div>

    <%-- 메인 콘텐츠 영역 --%>
    <main class="main-content">
        <header class="content-header">
            <div class="header-info">
                <h2><fmt:message key="dash.main.title" bundle="${msg}" /></h2>
                <nav class="breadcrumb">
                    <i class="fas fa-home"></i> <span>/ <fmt:message key="menu.summary" bundle="${msg}" /></span>
                </nav>
            </div>
        </header>

        <div class="widget-grid grid-3">
            <div class="widget-card">
                <div class="card-header">
                    <span class="card-value">$9,568</span>
                    <span class="card-badge decrease"><i class="fas fa-arrow-down"></i> 8.6%</span>
                </div>
                <p class="card-label"><fmt:message key="dash.label.totalSales" bundle="${msg}" /></p>
                <div class="mock-chart chart-green"></div>
            </div>
            <div class="widget-card">
                <div class="card-header">
                    <span class="card-value">85,247</span>
                    <span class="card-badge increase"><i class="fas fa-arrow-up"></i> 23.7%</span>
                </div>
                <p class="card-label"><fmt:message key="dash.label.totalAccounts" bundle="${msg}" /></p>
                <div class="mock-chart chart-orange"></div>
            </div>
            <div class="widget-card">
                <div class="card-header">
                    <span class="card-value">$69,452</span>
                    <span class="card-badge decrease"><i class="fas fa-arrow-down"></i> 8.6%</span>
                </div>
                <p class="card-label"><fmt:message key="dash.label.weeklySales" bundle="${msg}" /></p>
                <div class="mock-chart chart-blue"></div>
            </div>
        </div>

        <div class="widget-grid grid-3">
            <div class="widget-card">
                <div class="card-header">
                    <span class="card-value">$65,129</span>
                    <span class="card-badge increase">24.7%</span>
                </div>
                <p class="card-label"><fmt:message key="dash.goal.year" bundle="${msg}" /></p>
                <div class="progress-wrapper">
                    <div class="progress-bar" style="width: 68%; background: var(--accent-blue);"></div>
                </div>
                <div class="card-footer-info">
                    <span><fmt:message key="dash.goal.left" bundle="${msg}" /></span>
                    <span>68%</span>
                </div>
            </div>
            <div class="widget-card">
                <div class="card-header">
                    <span class="card-value">$88,367</span>
                    <span class="card-badge increase">18.6%</span>
                </div>
                <p class="card-label"><fmt:message key="dash.goal.month" bundle="${msg}" /></p>
                <div class="progress-wrapper">
                    <div class="progress-bar" style="width: 78%; background: #e84393;"></div>
                </div>
                <div class="card-footer-info">
                    <span><fmt:message key="dash.goal.left" bundle="${msg}" /></span>
                    <span>78%</span>
                </div>
            </div>
            <div class="widget-card">
                <div class="card-header">
                    <span class="card-value">$55,674</span>
                    <span class="card-badge increase">42.6%</span>
                </div>
                <p class="card-label"><fmt:message key="dash.goal.week" bundle="${msg}" /></p>
                <div class="progress-wrapper">
                    <div class="progress-bar" style="width: 88%; background: #2ecc71;"></div>
                </div>
                <div class="card-footer-info">
                    <span><fmt:message key="dash.goal.left" bundle="${msg}" /></span>
                    <span>88%</span>
                </div>
            </div>
        </div>
    </main>
</div>

<script src="${pageContext.request.contextPath}/js/common.js"></script>
</body>
</html>