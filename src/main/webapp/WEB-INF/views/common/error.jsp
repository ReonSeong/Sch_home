<%-------------------------------------------------------------%>
<%-- File Name : error.jsp                                   --%>
<%-- Writer : ReonQ                                          --%>
<%-- Updated Date     Version     User        Log            --%>
<%-- 2026-05-01           0.1                 make 404 Error --%>
<%-- 2026-05-01           0.2                 Add 500 Error  --%>
<%--                                                         --%>
<%-- Now Version : 0.2                                       --%>
<%-------------------------------------------------------------%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${errorCode == '404'}">404 - Page Not Found</c:when>
            <c:when test="${errorCode == '500'}">500 - Internal Server Error</c:when>
            <c:otherwise>Error Occurred</c:otherwise>
        </c:choose>
    </title>

    <!-- 공통 리소스 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/error.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
</head>
<body>
<div class="error-page-container">
    <div class="error-card glass">
        <!-- 1. 에러 코드 출력 -->
        <h1 class="error-code">
            <c:choose>
                <c:when test="${errorCode == '404'}">404</c:when>
                <c:when test="${errorCode == '500'}">500</c:when>
                <c:otherwise>ERROR</c:otherwise>
            </c:choose>
        </h1>

        <!-- 2. 메인 메시지 (Title) -->
        <p class="error-text-main">
            <c:choose>
                <c:when test="${errorCode == '404'}">
                    <spring:message code="error.404.title" text="Page Not Found" />
                </c:when>
                <c:when test="${errorCode == '500'}">
                    <spring:message code="error.500.title" text="Internal Server Error" />
                </c:when>
                <c:otherwise>
                    <spring:message code="error.general.title" text="Something went wrong" />
                </c:otherwise>
            </c:choose>
        </p>

        <!-- 3. 상세 메시지 (Detail) -->
        <p class="error-text-sub">
            <c:choose>
                <c:when test="${errorCode == '404'}">
                    <spring:message code="error.404.detail" text="The page you are looking for does not exist or has been moved." />
                </c:when>
                <c:when test="${errorCode == '500'}">
                    <spring:message code="error.500.detail" text="The server encountered an error and could not complete your request." />
                </c:when>
                <c:otherwise>
                    <spring:message code="error.general.detail" text="An unexpected error occurred. Please try again later." />
                </c:otherwise>
            </c:choose>
        </p>

        <!-- 4. 홈으로 이동 버튼 -->
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn-modal primary">
                <i class="fas fa-home"></i>
                <spring:message code="button.goHome" text="Go Home" />
            </a>
        </div>
    </div>
</div>
</body>
</html>