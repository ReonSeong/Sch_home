<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 1. 공통 헤더/메타/스크립트/헤더UI 포함 (시작 태그 포함되어 있음) --%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard.css">

<body class="dark-mode">
<div class="dashboard-container">
    <%-- 2. 좌측 사이드바 포함 --%>
    <c:set var="activeMenu" value="dashboard" />
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <%-- 3. 메인 콘텐츠 영역 (사이드바 우측 데이터 파트) --%>
    <main class="main-content">
        <header class="content-header">
            <div class="header-info">
                <h2><fmt:message key="dash.main.title" bundle="${msg}" /></h2>
                <nav class="breadcrumb">
                    <i class="fas fa-home"></i> <span>/ <fmt:message key="menu.summary" bundle="${msg}" /></span>
                </nav>
            </div>
        </header>

        <%-- 위젯 그리드 (핵심 데이터) --%>
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

        <%-- 목표 달성률 영역 --%>
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
            <%-- ... 추가 위젯들 ... --%>
        </div>
    </main>
</div>
</body>
</html>