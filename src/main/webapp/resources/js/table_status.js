/**
 * File Name : table_status.js
 * Updated Date     Version     User        Change Log
 * 2026-05-01           0.1     ReonQ       Published
 * 2026-05-01           0.2     ReonQ       Menu Cache & Order Logic Integration
 * 2026-05-07           0.3     ReonQ       Align JSON payload keys with SalesDTO
 *
 * Now Version : 0.3
 *
 * Description:
 * - Modal Control
 * - Order Management (Local Storage Cache)
 * - Table Layout (Drag & Drop)
 */

let isEditMode = false;
let tempIdCounter = 0;
let targetElementFullId = null;
let currentDiscount = 0;

// 현재 테이블들의 임시 주문 내역 (Key: tableIdx)
let currentOrders = {};

$(document).ready(function() {
    // 다국어 및 초기 데이터 설정
    window.i18n = window.i18nData || {};

    // [로컬스토리지 데이터 복원]
    const savedOrders = localStorage.getItem("POS_CURRENT_ORDERS");
    if (savedOrders) {
        currentOrders = JSON.parse(savedOrders);
        // 저장된 데이터를 바탕으로 모든 테이블 UI 업데이트 (금액 표시)
        Object.keys(currentOrders).forEach(tableIdx => {
            updateTableUI(tableIdx);
        });
    }

    // [1] 테이블 클릭 이벤트 (주문 상세 보기)
    $(document).on('click', '.table-item', function(e) {
        if (isEditMode || $(e.target).closest('.delete-btn').length > 0) return;

        const $this = $(this);
        const tableIdx = $this.attr('data-idx');
        const tableName = $this.find('.table-name').text().trim();

        if (tableIdx === "0") {
            alert(window.i18n.alertNewTable || "먼저 배치를 저장해주세요.");
            return;
        }

        // 모달 헤더에 정보 세팅
        $('#info-table-name').text(tableName).attr('data-current-idx', tableIdx);

        // 해당 테이블의 주문 내역 렌더링
        renderOrderList(tableIdx);

        // 상세 모달 열기
        openTableModal('orderDetailModal');
    });
});

/**
 * [2] 주문 리스트 렌더링
 */
function renderOrderList(tableIdx) {
    const orders = currentOrders[tableIdx] || [];
    let html = '';
    let total = 0;

    if (orders.length === 0) {
        html = `<tr><td colspan="3" style="text-align:center; padding:20px; color:var(--text-sub);">주문 내역이 없습니다.</td></tr>`;
    } else {
        orders.forEach((item) => {
            html += `
                <tr>
                    <td>${item.menuName}</td>
                    <td style="text-align: center;">${item.quantity}</td>
                    <td style="text-align: right;">${(item.price * item.quantity).toLocaleString()}${window.i18n.currency}</td>
                </tr>`;
            total += (item.price * item.quantity);
        });
    }

    $('#order-item-body').html(html);
    $('#info-total-price').text(total.toLocaleString());
}

/**
 * [3] 메뉴 선택 모달 표시 (탭 인터페이스)
 */
function showMenuSelection() {
    const menuList = JSON.parse(localStorage.getItem("POS_MENU_CACHE")) || window.posData.menuList || [];

    if (menuList.length === 0) {
        alert("등록된 메뉴가 없습니다.");
        return;
    }

    const categories = [...new Set(menuList.map(menu => menu.category || '기타'))];

    let tabHtml = `<div class="menu-tabs">`;
    tabHtml += `<button class="tab-btn active" onclick="filterMenu('all')">전체</button>`;
    categories.forEach(cat => {
        tabHtml += `<button class="tab-btn" onclick="filterMenu('${cat}')">${cat}</button>`;
    });
    tabHtml += `</div>`;

    let menuHtml = `<div id="menu-item-container" class="menu-grid">`;
    menuList.forEach(menu => {
        menuHtml += `
            <div class="menu-card" data-category="${menu.category || '기타'}" 
                 onclick="addMenuToOrder(${menu.menuIdx}, '${menu.menuName}', ${menu.price})">
                <p class="menu-card-name">${menu.menuName}</p>
                <p class="menu-card-price">${menu.price.toLocaleString()}${window.i18n.currency}</p>
            </div>`;
    });
    menuHtml += `</div>`;

    const $selectionArea = $('#order-selection-area');
    $selectionArea.html(tabHtml + menuHtml).show();

    closeTableModal('orderDetailModal');
    openTableModal('addOrderModal');
}

/**
 * 탭 클릭 시 메뉴 필터링
 */
function filterMenu(category) {
    $('.tab-btn').removeClass('active');
    $(event.target).addClass('active');

    if (category === 'all') {
        $('.menu-card').show();
    } else {
        $('.menu-card').hide();
        $(`.menu-card[data-category="${category}"]`).show();
    }
}

/**
 * [4] 주문 리스트에 메뉴 추가
 */
function addMenuToOrder(menuIdx, menuName, price) {
    const tableIdx = $('#info-table-name').attr('data-current-idx');

    if (!currentOrders[tableIdx]) currentOrders[tableIdx] = [];

    const existingItem = currentOrders[tableIdx].find(item => item.menuIdx === menuIdx);
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        currentOrders[tableIdx].push({
            menuIdx: menuIdx,
            menuName: menuName,
            price: price,
            quantity: 1
        });
    }

    // UI 배지 업데이트 및 로컬스토리지 저장
    updateTableUI(tableIdx);

    closeTableModal('addOrderModal');
    renderOrderList(tableIdx);
    openTableModal('orderDetailModal');
}

/**
 * 할인 적용 함수
 */
function applyDiscount() {
    const totalRaw = parseInt($('#info-total-price').text().replace(/,/g, '')) + currentDiscount;
    const discountInput = prompt("할인할 금액을 입력하세요 (숫자만)", currentDiscount);

    if (discountInput === null) return; // 취소 시

    const discountVal = parseInt(discountInput) || 0;

    if (discountVal > totalRaw) {
        alert("할인 금액이 총액보다 클 수 없습니다.");
        return;
    }

    currentDiscount = discountVal;

    // UI 업데이트
    if (currentDiscount > 0) {
        $('#discount-info-area').show();
        $('#display-discount-amount').text(currentDiscount.toLocaleString());
    } else {
        $('#discount-info-area').hide();
    }

    // 최종 가격 재계산 표시
    const finalPrice = totalRaw - currentDiscount;
    $('#info-total-price').text(finalPrice.toLocaleString());
}

/**
 * 결제 처리 (수정 버전)
 * @param {string} payMethod 'CARD' 또는 'CASH'
 */
function processPayment(payMethod) {
    const tableIdx = $('#info-table-name').attr('data-current-idx');
    const orders = currentOrders[tableIdx] || [];

    if (orders.length === 0) {
        alert("결제할 항목이 없습니다.");
        return;
    }

    const finalPriceText = $('#info-total-price').text();
    const finalPrice = parseInt(finalPriceText.replace(/,/g, ''));
    const totalPrice = finalPrice + currentDiscount; // 할인 전 원금

    if (!confirm(`[${payMethod}] ${finalPriceText}${window.i18n.currency} 결제를 진행하시겠습니까?`)) {
        return;
    }

    const mappedItemList = orders.map(item => ({
        menuIdx: item.menuIdx,
        menuName: item.menuName,
        unitPrice: item.price,
        quantity: item.quantity
    }));

    const paymentData = {
        tableIdx: parseInt(tableIdx),
        totalPrice: totalPrice,      // 할인 전 금액
        finalPrice: finalPrice,      // 실제 결제 금액
        payMethod: payMethod,        // CARD or CASH
        att1: currentDiscount.toString(), // 할인금액을 비고란(att1)에 기록 (선택사항)
        itemList: mappedItemList
    };

    $.ajax({
        url: window.contextPath + "/order/payment",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(paymentData),
        success: function(res) {
            if (res.trim() === "success") {
                showAlert(
                    "결제 완료",
                    `${payMethod} 결제가 정상적으로 처리되었습니다.`,
                    "success",
                    function() {
                        // 확인 버튼을 누른 후 실행될 로직들
                        currentDiscount = 0;
                        $('#discount-info-area').hide();
                        delete currentOrders[tableIdx];
                        localStorage.setItem("POS_CURRENT_ORDERS", JSON.stringify(currentOrders));

                        updateTableUI(tableIdx);
                        closeTableModal('orderDetailModal');
                    }
                );
            } else {
                // 실패 시에도 사용 가능
                showAlert("결제 실패", res, "warning");
            }
        },
        error: function() {
            showError("통신 오류 발생");
        }
    });
}

/**
 * 테이블 배지 UI 업데이트 공용 함수
 */
function updateTableUI(tableIdx) {
    const orders = currentOrders[tableIdx] || [];
    const $tableItem = $(`#table-${tableIdx}`);
    if ($tableItem.length === 0) return;

    const $statusBadge = $tableItem.find('.status-badge');

    if (orders.length > 0) {
        const total = orders.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        $statusBadge.removeClass('status-empty').addClass('status-use')
            .text(total.toLocaleString() + window.i18n.currency);
        $tableItem.addClass('has-order');
    } else {
        $statusBadge.removeClass('status-use').addClass('status-empty')
            .text(window.i18n.emptyText);
        $tableItem.removeClass('has-order');
    }

    // 로컬스토리지 실시간 동기화
    localStorage.setItem("POS_CURRENT_ORDERS", JSON.stringify(currentOrders));
}

// --- 모달 및 드래그 관련 유틸리티 ---

function openTableModal(id) {
    const $modal = $('#' + id);
    if ($modal.length > 0) {
        $modal.removeClass('hidden').addClass('active').css('display', 'flex');
    }
}

function closeTableModal(id) {
    const $modal = $('#' + id);
    if ($modal.length > 0) {
        $modal.removeClass('active').addClass('hidden').css('display', 'none');
    }
}

function confirmAddTable() {
    const nameInput = $('#newTableName');
    const name = nameInput.val().trim();
    if (!name) return alert(window.i18n.alertInputName);

    const tempId = 'new-' + (tempIdCounter++);
    const html = `
        <div class="table-item" id="${tempId}" data-idx="0" style="left: 50px; top: 50px;">
            <div class="delete-btn" onclick="confirmDelete(event, '${tempId}', '${name}')"><i class="fas fa-times"></i></div>
            <span class="table-name">${name}</span>
            <span class="table-status-text">
                <span class="status-badge status-empty">${window.i18n.emptyText}</span>
            </span>
        </div>`;
    $('#floor-plan').append(html);
    initDraggable();
    nameInput.val('');
    closeTableModal('addModal');
}

function confirmDelete(e, elementId, name) {
    if (e) { e.preventDefault(); e.stopPropagation(); }
    targetElementFullId = elementId;
    $('#delete-display-name').text(name);
    openTableModal('deleteTableConfirmModal');
}

function confirmDeleteTable() {
    if (!targetElementFullId) return;
    $(document.getElementById(targetElementFullId)).fadeOut(150, function() { $(this).remove(); });
    closeTableModal('deleteTableConfirmModal');
}

function savePositions() {
    const data = [];
    $('.table-item').each(function() {
        const $this = $(this);
        const pos = $this.position();
        data.push({
            tableIdx: parseInt($this.attr('data-idx')) || 0,
            tableName: $this.find('.table-name').text().trim(),
            posX: Math.round(pos.left),
            posY: Math.round(pos.top),
            status: $this.find('.status-badge').hasClass('status-empty') ? 'EMPTY' : 'USE'
        });
    });

    if(!confirm(window.i18n.alertSaveConfirm)) return;

    $.ajax({
        url: window.contextPath + "/table/saveLayout",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: (res) => { if(res.trim() === 'success') location.reload(); else alert(res); },
        error: () => alert(window.i18n.alertError)
    });
}

function toggleEditMode(enable) {
    isEditMode = enable;
    const $floorPlan = $('#floor-plan');
    if (enable) {
        $floorPlan.addClass('edit-mode');
        $('#mode-text').text(window.i18n.editModeText).css('color', '#3498db');
        $('#btn-edit-start').addClass('hidden');
        $('#btn-add, #btn-save, #btn-cancel').removeClass('hidden');
        initDraggable();
    } else {
        location.reload();
    }
}

function initDraggable() {
    let dragging = false, target = null, offset = { x: 0, y: 0 };
    $(document).off('mousedown.draggable', '.table-item');
    $(document).on('mousedown.draggable', '.table-item', function(e) {
        if (!isEditMode || $(e.target).closest('.delete-btn').length) return;
        dragging = true; target = $(this); target.css('z-index', 1000);
        const pos = target.position();
        offset.x = e.pageX - pos.left; offset.y = e.pageY - pos.top;
        e.preventDefault();
    });
    $(document).on('mousemove.draggable', function(e) {
        if (!dragging || !target) return;
        const container = $('#floor-plan');
        let x = e.pageX - offset.x, y = e.pageY - offset.y;
        x = Math.max(0, Math.min(x, container.width() - target.outerWidth()));
        y = Math.max(0, Math.min(y, container.height() - target.outerHeight()));
        target.css({ left: x, top: y });
    });
    $(document).on('mouseup.draggable', () => { dragging = false; if(target) target.css('z-index', 10); target = null; });
}