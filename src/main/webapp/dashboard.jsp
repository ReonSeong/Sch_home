<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 로그인 세션 체크 --%>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/login.jsp" />
</c:if>

<fmt:setBundle basename="lang.messages" var="msg" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Matfia Admin - Dashboard</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="dark-mode">

<%-- 1. 공통 헤더 임포트 --%>
<%@ include file="header.jsp" %>

<%-- 2. 배경 효과 --%>
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
            <a href="${pageContext.request.contextPath}/table" class="nav-item active">
                <i class="fas fa-border-all"></i> <span>테이블현황</span>
            </a>
            <a href="${pageContext.request.contextPath}/sales" class="nav-item">
                <i class="fas fa-chart-line"></i> <span>매출현황</span>
            </a>
            <a href="${pageContext.request.contextPath}/stock" class="nav-item">
                <i class="fas fa-boxes-stacked"></i> <span>재고관리</span>
            </a>
            <a href="${pageContext.request.contextPath}/customer" class="nav-item">
                <i class="fas fa-users"></i> <span>손님관리</span>
            </a>
            <a href="${pageContext.request.contextPath}/auth" class="nav-item">
                <i class="fas fa-key"></i> <span>권한관리</span>
            </a>
        </nav>
        <div class="sidebar-footer">© 2026 Matfia Admin</div>
    </aside>

    <%-- [모바일 전용] 상단 셀렉트 메뉴 --%>
    <div class="mobile-menu-wrapper glass">
        <label for="mobileMenu">
            <i class="fas fa-bars-staggered"></i>
        </label>
        <select id="mobileMenu" onchange="location.href=this.value;">
            <option value="${pageContext.request.contextPath}/table" selected>테이블현황</option>
            <option value="${pageContext.request.contextPath}/sales">매출현황</option>
            <option value="${pageContext.request.contextPath}/stock">재고관리</option>
            <option value="${pageContext.request.contextPath}/customer">손님관리</option>
            <option value="${pageContext.request.contextPath}/auth">권한관리</option>
        </select>
    </div>

    <%-- 메인 콘텐츠 영역 --%>
    <main class="main-content">
        <header class="content-header">
            <h2>테이블 현황</h2>
            <p>실시간 매장 상태를 한눈에 관리하세요.</p>
        </header>

        <section class="content-body glass">
            <div class="placeholder-content">
                <i class="fas fa-utensils"></i>
                <p>테이블 데이터를 로딩 중입니다...</p>
            </div>
        </section>
    </main>
</div>

<script src="${pageContext.request.contextPath}/js/common.js"></script>
</body>
</html>