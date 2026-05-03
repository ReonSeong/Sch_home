<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">

<aside class="sidebar glass">
    <div class="sidebar-header">
        <div class="user-info">
            <i class="fas fa-user-circle"></i>
            <div>
                <p class="u-name">
                    <c:out value="${not empty sessionScope.user ? sessionScope.user.userName : sessionScope.userInfo.userName}" default="Admin User" />
                </p>
                <p class="u-role">Master Admin</p>
            </div>
        </div>
    </div>

    <nav class="side-nav">
        <a href="${pageContext.request.contextPath}/dashboard" class="nav-item ${activeMenu == 'dashboard' ? 'active' : ''}">
            <i class="fas fa-gauge-high"></i>
            <span><fmt:message key="menu.summary" bundle="${msg}" /></span>
        </a>

        <a href="${pageContext.request.contextPath}/table_status" class="nav-item ${activeMenu == 'table' ? 'active' : ''}">
            <i class="fas fa-border-all"></i>
            <span><fmt:message key="menu.table" bundle="${msg}" /></span>
        </a>

        <a href="${pageContext.request.contextPath}/menu_manage" class="nav-item ${activeMenu == 'menu' ? 'active' : ''}">
            <i class="fas fa-utensils"></i>
            <span><fmt:message key="menu.manage" bundle="${msg}" /></span>
        </a>

        <a href="${pageContext.request.contextPath}/sales/status" class="nav-item ${activeMenu == 'sales' ? 'active' : ''}">
            <i class="fas fa-chart-line"></i>
            <span><fmt:message key="menu.sales" bundle="${msg}" /></span>
        </a>

        <a href="${pageContext.request.contextPath}/stock/manage" class="nav-item ${activeMenu == 'stock' ? 'active' : ''}">
            <i class="fas fa-boxes-stacked"></i>
            <span><fmt:message key="menu.stock" bundle="${msg}" /></span>
        </a>

        <a href="${pageContext.request.contextPath}/customer/list" class="nav-item ${activeMenu == 'customer' ? 'active' : ''}">
            <i class="fas fa-users"></i>
            <span><fmt:message key="menu.customer" bundle="${msg}" /></span>
        </a>

        <a href="${pageContext.request.contextPath}/auth/manage" class="nav-item ${activeMenu == 'auth' ? 'active' : ''}">
            <i class="fas fa-key"></i>
            <span><fmt:message key="menu.auth" bundle="${msg}" /></span>
        </a>
    </nav>

    <div class="sidebar-footer">
        <p>© 2026 Chef Master</p>
        <small>v1.0.4 - ReonQ</small>
    </div>
</aside>

<%-- 모바일 전용 드롭다운도 동일하게 수정 --%>
<div class="mobile-menu-wrapper">
    <div class="custom-dropdown glass" id="mobileDropdown">
        <div class="dropdown-selected">
            <i class="fas fa-bars-staggered"></i>
            <span id="selectedText">
                <c:choose>
                    <c:when test="${activeMenu == 'table'}"><fmt:message key="menu.table" bundle="${msg}" /></c:when>
                    <c:when test="${activeMenu == 'menu'}"><fmt:message key="menu.manage" bundle="${msg}" /></c:when>
                    <c:when test="${activeMenu == 'sales'}"><fmt:message key="menu.sales" bundle="${msg}" /></c:when>
                    <c:when test="${activeMenu == 'stock'}"><fmt:message key="menu.stock" bundle="${msg}" /></c:when>
                    <c:when test="${activeMenu == 'customer'}"><fmt:message key="menu.customer" bundle="${msg}" /></c:when>
                    <c:when test="${activeMenu == 'auth'}"><fmt:message key="menu.auth" bundle="${msg}" /></c:when>
                    <c:otherwise><fmt:message key="menu.summary" bundle="${msg}" /></c:otherwise>
                </c:choose>
            </span>
            <i class="fas fa-chevron-down arrow"></i>
        </div>
    </div>
</div>