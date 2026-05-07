<%--
     File Name : modal.jsp
     Updated Date     Version     User        Log
     2026-05-01           0.1                 Published
     2026-05-01           0.2                 Add Delete, Update UI
     2026-05-01           0.3                 Add Order (Menu Selection) Modal

     Now Version : 0.3

     Description:
     General Modal box control
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modal.css">

<!-- [Common Section] Error Modal -->
<div id="errorModal" class="modal-overlay hidden">
    <div class="modal-content error-mode">
        <button class="modal-close-x" onclick="closeModal('errorModal')">&times;</button>
        <div class="error-icon">
            <i class="fas fa-exclamation-triangle"></i>
        </div>
        <div class="error-message-title" id="errorTitle">Error</div>
        <div class="error-message-detail" id="errorDetail">Description of the error...</div>
        <div class="modal-footer">
            <button class="btn-modal primary" onclick="closeModal('errorModal')">Confirm</button>
        </div>
    </div>
</div>

<!-- Confirm/Info Modal -->
<div id="infoModal" class="modal-overlay hidden">
    <div class="modal-content info-mode">
        <button class="modal-close-x" onclick="closeModal('infoModal')">&times;</button>
        <div class="info-icon" id="infoIcon">
            <i class="fas fa-check-circle"></i> </div>
        <div class="info-message-title" id="infoTitle">Success</div>
        <div class="info-message-detail" id="infoDetail">Description...</div>
        <div class="modal-footer">
            <button class="btn-modal primary" onclick="handleInfoConfirm()">확인</button>
        </div>
    </div>
</div>

<!-- [table_status Section] -->

<!-- 1. Order Detail Modal (주문 상세 정보) -->
<div id="orderDetailModal" class="modal-overlay hidden">
    <div class="modal-content glass detail-mode">
        <div class="modal-header">
            <h3>
                <i class="fas fa-receipt"></i>
                <span id="info-table-name"></span> <fmt:message key="table.modal.orderTitle" bundle="${msg}" />
            </h3>
            <button class="modal-close-x" onclick="closeTableModal('orderDetailModal')">&times;</button>
        </div>
        <div class="modal-body">
            <div class="order-list-container">
                <table class="order-item-list">
                    <thead>
                    <tr>
                        <th><fmt:message key="table.modal.menuName" bundle="${msg}" /></th>
                        <th style="text-align: center;"><fmt:message key="table.modal.count" bundle="${msg}" /></th>
                        <th style="text-align: right;"><fmt:message key="table.modal.price" bundle="${msg}" /></th>
                    </tr>
                    </thead>
                    <tbody id="order-item-body"></tbody>
                </table>
            </div>

            <div id="discount-info-area" class="discount-display" style="display:none; text-align: right; color: #e74c3c; padding: 5px 20px;">
                <span>할인 금액: -<span id="display-discount-amount">0</span>원</span>
            </div>

            <div class="total-price-area">
                <span class="label"><fmt:message key="table.modal.totalLabel" bundle="${msg}" /></span>
                <span class="amount">
                    <span id="info-total-price">0</span>
                    <c:choose>
                        <c:when test="${sessionScope.currentLang == 'ko'}">원</c:when>
                        <c:when test="${sessionScope.currentLang == 'sr'}">RSD</c:when>
                        <c:otherwise> $</c:otherwise>
                    </c:choose>
                </span>
            </div>
        </div>

        <div class="modal-footer split">
            <div class="footer-left">
                <button class="btn-modal primary" onclick="showMenuSelection()">
                    <i class="fas fa-plus"></i> <fmt:message key="table.btn.addOrder" bundle="${msg}" />
                </button>
                <button class="btn-modal warning" onclick="applyDiscount()">
                    <i class="fas fa-tag"></i> 할인
                </button>
            </div>
            <div class="footer-right">
                <button class="btn-modal success" onclick="processPayment('CARD')">
                    <i class="fas fa-credit-card"></i> 카드
                </button>
                <button class="btn-modal cash" onclick="processPayment('CASH')" style="background-color: #27ae60; color: white;">
                    <i class="fas fa-money-bill-wave"></i> 현금
                </button>
            </div>
        </div>
    </div>
</div>

<!-- 1-2. Add Order (Menu Selection) Modal (메뉴 추가 선택 모달) -->
<div id="addOrderModal" class="modal-overlay hidden">
    <div class="modal-content glass menu-mode" style="width: 700px; max-width: 95vw;">
        <div class="modal-header">
            <h3><i class="fas fa-utensils"></i> <fmt:message key="table.modal.addOrderTitle" bundle="${msg}" /></h3>
            <!-- X 버튼 클릭 시 상세 모달로 돌아감 -->
            <button class="modal-close-x" onclick="closeTableModal('addOrderModal'); openTableModal('orderDetailModal');">&times;</button>
        </div>
        <div class="modal-body">
            <div id="order-selection-area"></div>
        </div>
    </div>
</div>

<!-- 2. Add New Table Modal (테이블 레이아웃 추가) -->
<div id="addModal" class="modal-overlay hidden">
    <div class="modal-content table-mode">
        <div class="modal-header">
            <h3><i class="fas fa-plus-circle"></i> <fmt:message key="table.modal.addTitle" bundle="${msg}" /></h3>
            <button class="modal-close-x" onclick="closeModal('addModal')">&times;</button>
        </div>
        <div class="input-group-wrapper">
            <div class="input-group">
                <label for="newTableName"><fmt:message key="table.modal.labelName" bundle="${msg}" /></label>
                <input type="text" id="newTableName" class="capsule-input"
                       placeholder="<fmt:message key='table.modal.placeholderName' bundle='${msg}' />">
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn-modal primary" onclick="confirmAddTable()">
                <fmt:message key="table.btn.create" bundle="${msg}" />
            </button>
        </div>
    </div>
</div>

<!-- 3. Delete Confirm Modal (삭제 확인) -->
<div id="deleteTableConfirmModal" class="modal-overlay hidden">
    <div class="modal-content table-mode">
        <button class="modal-close-x" onclick="closeModal('deleteTableConfirmModal')">&times;</button>
        <input type="hidden" id="delete-target-id" value="">

        <div class="delete-confirm-container">
            <span class="delete-target-name" id="delete-display-name">Table Name</span>
            <p class="delete-question"><fmt:message key="table.modal.deleteMsg" bundle="${msg}" /></p>
        </div>
        <div class="modal-footer">
            <button class="btn-modal secondary" onclick="closeModal('deleteTableConfirmModal')">NO</button>
            <button class="btn-modal danger" onclick="confirmDeleteTable()">YES</button>
        </div>
    </div>
</div>