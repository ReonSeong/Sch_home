<%--
     File Name : sales_status.jsp
     Writer : ReonQ
     Updated Date     Version     User        Log
     2026-05-07           0.1                 Published

     Now Version : 0.2

     Description:
     General sales info

--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 1. 공통 헤더/메타/스크립트 포함 --%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%-- 매출 관리 전용 CSS (기존 dashboard.css 활용 및 필요시 추가) --%>
<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">--%>

<style>
    /* 매출 관리 페이지 전용 추가 스타일 */
    .clickable-row { cursor: pointer; transition: background 0.2s; }
    .clickable-row:hover { background: rgba(255, 255, 255, 0.05); }
    .chart-container { position: relative; height: 300px; width: 100%; }
    .sales-table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    .sales-table th { text-align: left; padding: 12px; border-bottom: 1px solid var(--border-color); color: var(--text-sub); }
    .sales-table td { padding: 12px; border-bottom: 1px solid var(--border-color); }
    .empty-msg { text-align: center; padding: 40px; color: var(--text-sub); }
</style>

<body class="dark-mode">
<div class="dashboard-container">
    <%-- 2. 좌측 사이드바 포함 (활성 메뉴를 sales로 설정) --%>
    <c:set var="activeMenu" value="sales" />
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <%-- 3. 메인 콘텐츠 영역 --%>
    <main class="main-content">
        <header class="content-header">
            <div class="header-info">
                <h2><i class="fas fa-chart-bar"></i> <fmt:message key="menu.sales.manage" bundle="${msg}" /></h2>
                <nav class="breadcrumb">
                    <i class="fas fa-home"></i> <span>/ <fmt:message key="menu.sales" bundle="${msg}" /></span>
                </nav>
            </div>
            <div class="header-tools">
                <input type="month" id="salesMonth" class="date-picker-input" value="2026-05" onchange="loadDailySales()">
            </div>
        </header>

        <%-- 상단: 일자별 매출 그래프 위젯 --%>
        <div class="widget-grid grid-1">
            <div class="widget-card">
                <div class="card-header">
                    <h3 class="card-label">일자별 매출 추이</h3>
                    <span id="totalMonthAmount" class="card-value" style="font-size: 1.2rem; color: var(--accent-green);"></span>
                </div>
                <div class="chart-container">
                    <canvas id="dailySalesChart"></canvas>
                </div>
            </div>
        </div>

        <%-- 하단: 시간대별 요약 및 메뉴 상세 내역 (2분할) --%>
        <div class="widget-grid grid-2">
            <%-- 시간대별 리스트 --%>
            <div class="widget-card">
                <div class="card-header">
                    <h3 class="card-label"><span id="selectedDateText">날짜를 선택하세요</span> - 시간대별 매출</h3>
                </div>
                <div class="table-wrapper">
                    <table class="sales-table">
                        <thead>
                        <tr>
                            <th>시간</th>
                            <th>결제건수</th>
                            <th style="text-align: right;">매출액</th>
                        </tr>
                        </thead>
                        <tbody id="hourlySalesBody">
                        <tr><td colspan="3" class="empty-msg">그래프의 막대를 클릭해 주세요.</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <%-- 메뉴별 상세 내역 --%>
            <div class="widget-card">
                <div class="card-header">
                    <h3 class="card-label">메뉴별 판매 상세</h3>
                </div>
                <div class="table-wrapper">
                    <table class="sales-table">
                        <thead>
                        <tr>
                            <th>메뉴명</th>
                            <th>수량</th>
                            <th style="text-align: right;">합계</th>
                        </tr>
                        </thead>
                        <tbody id="menuDetailBody">
                        <tr><td colspan="3" class="empty-msg">시간대 행을 클릭해 주세요.</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
</div>

<%-- Chart.js 라이브러리 로드 --%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<%-- 전용 스크립트 파일 분리 권장 --%>
<script src="${pageContext.request.contextPath}/resources/js/sales_manage.js"></script>
</body>
</html>
