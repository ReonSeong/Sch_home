<%--
     File Name : table_status.jsp
     Writer : ReonQ
     Updated Date     Version     User        Log
     2026-04-19           0.1                 Published
     2026-05-01           0.2                 Add Multi-lang, add order history

     Now Version : 0.2

     Description:
     General restaurant table check page
     - table order check
     - table reservation check
     - CRUD tables
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="lang/messages" var="msg" scope="session" />

<!DOCTYPE html>
<html lang="${sessionScope.currentLang != null ? sessionScope.currentLang : 'en'}">
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/table_status.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modal.css">

<body class="dark-mode">

<script>
    /* JavaScript 전역 변수 설정 */
    window.contextPath = "${pageContext.request.contextPath}";

    /* table_status.js에서 사용할 데이터 객체 */
    window.i18nData = {
        emptyText: "<fmt:message key='table.status.empty' bundle='${msg}' />",
        editModeText: "<fmt:message key='table.mode.edit' bundle='${msg}' />",
        alertNewTable: "<fmt:message key='table.alert.newTable' bundle='${msg}' />",
        alertInputName: "<fmt:message key='table.alert.inputName' bundle='${msg}' />",
        alertSaveConfirm: "<fmt:message key='table.alert.saveConfirm' bundle='${msg}' />",
        alertError: "<fmt:message key='table.alert.error' bundle='${msg}' />",
        currency: "${sessionScope.currentLang == 'ko' ? '원' : (sessionScope.currentLang == 'sr' ? ' RSD' : ' $')}"
    };

    /* Menu list caching */
    window.posData = {
        // Parse Json String to JS obj
        menuList: ${menuListJson != null ? menuListJson : '[]'}
    };

    document.addEventListener("DOMContentLoaded", function() {
        if (window.posData.menuList && window.posData.menuList.length > 0) {
            // save to localstorage
            localStorage.setItem("POS_MENU_CACHE", JSON.stringify(window.posData.menuList));
            console.log("✅ Menu data cached. Count:", window.posData.menuList.length);
        }
    });
</script>

<div class="dashboard-container">
    <c:set var="activeMenu" value="table" />

    <main class="main-content">
        <header class="content-header">
            <div class="header-title">
                <h2><i class="fas fa-layer-group"></i> <fmt:message key="table.title" bundle="${msg}" /></h2>
                <p id="mode-text" class="text-sub"><fmt:message key="table.mode.view" bundle="${msg}" /></p>
            </div>
        </header>

        <div class="floor-plan-wrapper">
            <div id="floor-plan" class="glass">
                <c:forEach var="table" items="${tableList}">
                    <div class="table-item" id="table-${table.tableIdx}" data-idx="${table.tableIdx}" style="left: ${table.posX}px; top: ${table.posY}px;">
                        <div class="delete-btn" onclick="confirmDelete(event, 'table-${table.tableIdx}', '${table.tableName}')">
                            <i class="fas fa-times"></i>
                        </div>
                        <span class="table-name">
                <c:out value="${table.tableName}" />
            </span>

                        <span class="table-status-text">
                <span class="status-badge ${table.status == 'EMPTY' ? 'status-empty' : 'status-use'}">
                    <c:choose>
                        <c:when test="${table.status == 'EMPTY'}">
                            <fmt:message key="table.status.empty" bundle="${msg}" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="table.status.use" bundle="${msg}" />
                        </c:otherwise>
                    </c:choose>
                </span>
            </span>
                    </div>
                </c:forEach>
            </div>


            <div class="control-panel">
                <button id="btn-edit-start" class="btn-modal primary" onclick="toggleEditMode(true)">
                    <i class="fas fa-edit"></i> <span><fmt:message key="table.btn.editStart" bundle="${msg}" /></span>
                </button>
                <button id="btn-add" class="btn-modal secondary hidden" onclick="openModal('addModal')">
                    <i class="fas fa-plus-circle"></i> <span><fmt:message key="table.btn.add" bundle="${msg}" /></span>
                </button>
                <button id="btn-save" class="btn-modal primary hidden" onclick="savePositions()">
                    <i class="fas fa-check-double"></i> <span><fmt:message key="table.btn.save" bundle="${msg}" /></span>
                </button>
                <button id="btn-cancel" class="btn-modal secondary hidden" onclick="location.reload()">
                    <i class="fas fa-times"></i> <span><fmt:message key="table.btn.cancel" bundle="${msg}" /></span>
                </button>
            </div>
        </div>
    </main>
</div>

<!-- 모달 섹션 -->
<%@ include file="/WEB-INF/views/common/modal.jsp" %>

<!-- 외부 JS 파일 로드 -->
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table_status.js"></script>

</body>
</html>