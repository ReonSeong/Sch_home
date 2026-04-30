<%@ page contentType="text/html;charset=UTF-8" %>
<%-- 다국어 번들이 설정되지 않았을 경우를 대비해 기본 번들 설정 (없으면 생략 가능하지만 에러 방지용) --%>
<%--<fmt:setBundle basename="messages" var="msg" scope="session"/>--%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">

<aside class="sidebar glass">
    <div class="sidebar-header">
        <div class="user-info">
            <i class="fas fa-user-circle"></i>
            <div>
                <p class="u-name">
                    <%-- 세션 객체명이 userInfo인지 user인지 확인 필요. 여기서는 둘 다 대응하도록 수정 --%>
                    <c:out value="${not empty sessionScope.user ? sessionScope.user.userName : sessionScope.userInfo.userName}" default="Admin User" />
                </p>
                <p class="u-role">Master Admin</p>
            </div>
        </div>
    </div>

    <nav class="side-nav">
        <a href="${pageContext.request.contextPath}/dashboard" class="nav-item ${activeMenu == 'dashboard' ? 'active' : ''}">
            <i class="fas fa-gauge-high"></i>
            <span>메뉴 요약</span> <%-- 번들 에러 방지를 위해 직접 텍스트를 넣거나 fmt 태그 사용 --%>
        </a>

        <a href="${pageContext.request.contextPath}/table_status" class="nav-item ${activeMenu == 'table' ? 'active' : ''}">
            <i class="fas fa-border-all"></i>
            <span>테이블 관리</span>
        </a>

        <a href="${pageContext.request.contextPath}/menu_manage" class="nav-item ${activeMenu == 'menu' ? 'active' : ''}">
            <i class="fas fa-utensils"></i>
            <span>메뉴 관리</span>
        </a>

        <a href="${pageContext.request.contextPath}/sales/status" class="nav-item ${activeMenu == 'sales' ? 'active' : ''}">
            <i class="fas fa-chart-line"></i>
            <span>매출 현황</span>
        </a>

        <a href="${pageContext.request.contextPath}/stock/manage" class="nav-item ${activeMenu == 'stock' ? 'active' : ''}">
            <i class="fas fa-boxes-stacked"></i>
            <span>재고 관리</span>
        </a>

        <a href="${pageContext.request.contextPath}/customer/list" class="nav-item ${activeMenu == 'customer' ? 'active' : ''}">
            <i class="fas fa-users"></i>
            <span>고객 관리</span>
        </a>

        <a href="${pageContext.request.contextPath}/auth/manage" class="nav-item ${activeMenu == 'auth' ? 'active' : ''}">
            <i class="fas fa-key"></i>
            <span>권한 관리</span>
        </a>
    </nav>

    <div class="sidebar-footer">
        <p>© 2026 Chef Master</p>
        <small>v1.0.4 - ReonQ</small>
    </div>
</aside>

<%-- 모바일 전용 --%>
<div class="mobile-menu-wrapper">
    <div class="custom-dropdown glass" id="mobileDropdown">
        <div class="dropdown-selected">
            <i class="fas fa-bars-staggered"></i>
            <span id="selectedText">
                <c:choose>
                    <c:when test="${activeMenu == 'table'}">테이블 관리</c:when>
                    <c:when test="${activeMenu == 'menu'}">메뉴 관리</c:when>
                    <c:when test="${activeMenu == 'sales'}">매출 현황</c:when>
                    <c:when test="${activeMenu == 'stock'}">재고 관리</c:when>
                    <c:when test="${activeMenu == 'customer'}">고객 관리</c:when>
                    <c:when test="${activeMenu == 'auth'}">권한 관리</c:when>
                    <c:otherwise>메뉴 요약</c:otherwise>
                </c:choose>
            </span>
            <i class="fas fa-chevron-down arrow"></i>
        </div>
    </div>
</div>