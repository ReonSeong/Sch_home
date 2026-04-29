<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/table_status.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modal.css">


<body class="dark-mode">

<div class="dashboard-container">
    <c:set var="activeMenu" value="table" />

    <main class="main-content">
        <header class="content-header">
            <div class="header-title">
                <h2><i class="fas fa-layer-group"></i> 매장 테이블 배치 관리</h2>
                <p id="mode-text" class="text-sub">조회 모드: 테이블을 클릭하여 주문 현황을 확인하세요.</p>
            </div>
        </header>

        <div class="floor-plan-wrapper">
            <div id="floor-plan" class="glass">
                <c:forEach var="table" items="${tableList}">
                    <div class="table-item" id="table-${table.tableIdx}" data-idx="${table.tableIdx}"
                         style="left: ${table.posX}px; top: ${table.posY}px;">

                        <div class="delete-btn" onclick="confirmDelete(event, 'table-${table.tableIdx}', '${table.tableName}')">
                            <i class="fas fa-times"></i>
                        </div>

                        <span class="table-name">${table.tableName}</span>
                        <span class="table-status-text">
                            <span class="status-badge ${table.status == 'EMPTY' ? 'status-empty' : 'status-use'}">
                                    ${table.status == 'EMPTY' ? '빈 테이블' : '사용 중'}
                            </span>
                        </span>
                    </div>
                </c:forEach>
            </div>

            <div class="control-panel">
                <button id="btn-edit-start" class="btn-modal primary" onclick="toggleEditMode(true)">
                    <i class="fas fa-edit"></i> <span>배치 수정하기</span>
                </button>
                <button id="btn-add" class="btn-modal secondary hidden" onclick="openModal('addModal')">
                    <i class="fas fa-plus-circle"></i> <span>테이블 추가</span>
                </button>
                <button id="btn-save" class="btn-modal primary hidden" onclick="savePositions()">
                    <i class="fas fa-check-double"></i> <span>변경사항 저장</span>
                </button>
                <button id="btn-cancel" class="btn-modal secondary hidden" onclick="location.reload()">
                    <i class="fas fa-times"></i> <span>취소</span>
                </button>
            </div>
        </div>
    </main>
</div>

<!-- [신규] 테이블 상세 조회 모달 (샘플 데이터용) -->
<div id="orderDetailModal" class="modal-overlay hidden">
    <div class="modal-content"> <!-- modal.css에서 background 등을 제어함 -->
        <div class="modal-header">
            <h3><i class="fas fa-receipt" style="margin-right: 10px; color: var(--accent-blue);"></i> <span id="info-table-name"></span>번 테이블 현황</h3>
            <button class="btn-close" onclick="closeModal('orderDetailModal')">&times;</button>
        </div>
        <div class="modal-body">
            <div class="order-list-container">
                <table class="order-item-list">
                    <thead>
                    <tr>
                        <th>메뉴명</th>
                        <th style="text-align: center;">수량</th>
                        <th style="text-align: right;">금액</th>
                    </tr>
                    </thead>
                    <tbody id="order-item-body">
                    <!-- 샘플 데이터 삽입 영역 -->
                    </tbody>
                </table>
            </div>

            <div class="total-price-area">
                <span class="label">총 결제 예정 금액</span>
                <span class="amount"><span id="info-total-price">0</span>원</span>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn-modal secondary" onclick="closeModal('orderDetailModal')">닫기</button>
            <button class="btn-modal primary">주문 추가</button>
        </div>
    </div>
</div>

<!-- 테이블 추가 모달 -->
<div id="addModal" class="modal-overlay hidden">
    <div class="modal-content glass">
        <div class="modal-header">
            <h3><i class="fas fa-plus-circle"></i> 새 테이블 추가</h3>
            <button class="btn-close" onclick="closeModal('addModal')">&times;</button>
        </div>
        <div class="modal-body">
            <label for="newTableName">테이블 이름</label>
            <input type="text" id="newTableName" class="form-input" placeholder="예: 창가 1번">
        </div>
        <div class="modal-footer">
            <button class="btn-modal secondary" onclick="closeModal('addModal')">닫기</button>
            <button class="btn-modal primary" onclick="confirmAddTable()">생성하기</button>
        </div>
    </div>
</div>

<!-- 삭제 확인 모달 -->
<div id="deleteConfirmModal" class="modal-overlay hidden">
    <div class="modal-content glass" style="max-width: 350px; text-align: center;">
        <div class="modal-body">
            <p><strong id="deleteTargetName"></strong> 테이블을 삭제하시겠습니까?</p>
        </div>
        <div class="modal-footer" style="justify-content: center;">
            <button class="btn-modal secondary" onclick="closeModal('deleteConfirmModal')">아니오</button>
            <button class="btn-modal primary" style="background: #e74c3c;" onclick="executeDelete()">예</button>
        </div>
    </div>
</div>

<script>
    let isEditMode = false;
    let tempIdCounter = 0;
    let targetElementFullId = null;
    const contextPath = "${pageContext.request.contextPath}";

    // 모달 제어
    function openModal(id) { $('#' + id).removeClass('hidden'); }
    function closeModal(id) { $('#' + id).addClass('hidden'); }

    // [1] 테이블 조회 클릭 이벤트 (샘플 데이터 삽입 로직 포함)
    $(document).on('click', '.table-item', function(e) {
        if (isEditMode || $(e.target).closest('.delete-btn').length > 0) return;

        const tableIdx = $(this).attr('data-idx');
        const tableName = $(this).find('.table-name').text().trim();

        if (tableIdx === "0") {
            alert("신규 추가된 테이블은 저장 후 조회 가능합니다.");
            return;
        }

        // 샘플 데이터 생성
        const sampleOrders = [
            { name: "프리미엄 숙성 삼겹살", count: 2, price: 32000 },
            { name: "된장찌개", count: 1, price: 5000 },
            { name: "공기밥", count: 2, price: 2000 },
            { name: "소주 (참이슬)", count: 2, price: 10000 }
        ];

        // 모달 데이터 채우기
        $('#info-table-name').text(tableName);
        let html = '';
        let total = 0;

        sampleOrders.forEach(item => {
            html += '<tr>' +
                '<td>' + item.name + '</td>' +
                '<td>' + item.count + '</td>' +
                '<td>' + item.price.toLocaleString() + '원</td>' +
                '</tr>';
            total += item.price;
        });

        $('#order-item-body').html(html);
        $('#info-total-price').text(total.toLocaleString());

        openModal('orderDetailModal');
    });

    // [2] 테이블 추가
    function confirmAddTable() {
        const nameInput = $('#newTableName');
        const name = nameInput.val().trim();

        if (!name) {
            alert('테이블 이름을 입력해주세요.');
            return;
        }

        const $floorPlan = $('#floor-plan');
        const tempId = 'new-' + (tempIdCounter++);

        const html =
            '<div class="table-item" id="' + tempId + '" data-idx="0" data-name="' + name + '" ' +
            '     style="left: 100px; top: 100px; z-index: 100; position: absolute;">' +
            '    <div class="delete-btn" onclick="confirmDelete(event, \'' + tempId + '\', \'' + name + '\')">' +
            '        <i class="fas fa-times"></i>' +
            '    </div>' +
            '    <span class="table-name">' + name + '</span>' +
            '    <span class="table-status-text"><span class="status-badge status-empty">빈 테이블</span></span>' +
            '</div>';

        $floorPlan.append(html);
        initDraggable();
        nameInput.val('');
        closeModal('addModal');
    }

    // [3] 삭제 확인
    function confirmDelete(e, elementId, name) {
        if (e) { e.preventDefault(); e.stopPropagation(); }
        let finalId = elementId || $(e.currentTarget).closest('.table-item').attr('id');
        targetElementFullId = finalId;
        $('#deleteTargetName').text(name || "신규");
        openModal('deleteConfirmModal');
    }

    // [4] 실제 삭제 실행
    function executeDelete() {
        if (!targetElementFullId) return;
        const $target = $(document.getElementById(targetElementFullId));
        if ($target.length > 0) $target.fadeOut(150, function() { $(this).remove(); });
        closeModal('deleteConfirmModal');
        targetElementFullId = null;
    }

    // [5] 서버 저장
    function savePositions() {
        const data = [];
        $('.table-item').each(function() {
            const $this = $(this);
            let name = $this.find('.table-name').text().trim() || $this.attr('data-name');
            data.push({
                tableIdx: parseInt($this.attr('data-idx')) || 0,
                tableName: name,
                posX: parseInt($this.css('left')) || 0,
                posY: parseInt($this.css('top')) || 0,
                status: 'EMPTY'
            });
        });

        if(!confirm("저장하시겠습니까?")) return;

        $.ajax({
            url: contextPath + "/table/saveLayout",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: (res) => { if(res.trim() === 'success') location.reload(); },
            error: () => alert('통신 오류')
        });
    }

    // [6] 편집 모드 토글
    function toggleEditMode(enable) {
        isEditMode = enable;
        if (enable) {
            $('#floor-plan').addClass('edit-mode');
            $('#mode-text').text('편집 모드: 테이블을 드래그하거나 삭제하세요.').css('color', '#3498db');
            $('#btn-edit-start').addClass('hidden');
            $('#btn-add, #btn-save, #btn-cancel').removeClass('hidden');
            initDraggable();
        }
    }

    // [7] 드래그 앤 드롭
    function initDraggable() {
        let dragging = false, target = null, offset = {x:0, y:0};
        $(document).off('mousedown', '.table-item').on('mousedown', '.table-item', function(e) {
            if(!isEditMode || $(e.target).closest('.delete-btn').length) return;
            dragging = true; target = $(this);
            offset.x = e.pageX - target.position().left;
            offset.y = e.pageY - target.position().top;
        });
        $(document).on('mousemove', function(e) {
            if(!dragging || !target) return;
            let x = Math.max(0, Math.min(e.pageX - offset.x, $('#floor-plan').width() - target.outerWidth()));
            let y = Math.max(0, Math.min(e.pageY - offset.y, $('#floor-plan').height() - target.outerHeight()));
            target.css({left: x, top: y});
        }).on('mouseup', () => { dragging = false; target = null; });
    }
</script>
</body>
</html>