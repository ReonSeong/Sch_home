<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

<!-- CSS 연결 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/menu_manage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modal.css">

<body class="dark-mode">

<div class="dashboard-container">
    <main class="main-content">
        <!-- 상단 헤더 -->
        <div class="content-header">
            <div class="header-title">
                <h2><i class="fas fa-utensils"></i> 메뉴 관리</h2>
                <p class="text-sub">등록된 메뉴를 관리하고 새로운 메뉴를 추가할 수 있습니다.</p>
            </div>

            <div class="header-right-btn">
                <button class="btn-modal secondary" onclick="openAddModal()" style="padding: 12px 25px; border-radius: 50px; cursor: pointer;">
                    <i class="fas fa-plus"></i> 메뉴 추가하기
                </button>
            </div>
        </div>

        <!-- 메뉴 컨테이너 -->
        <div class="menu-container">
            <div class="category-tabs">
                <div class="tab-item active" data-rel="ALL">전체</div>
                <div class="tab-item" data-rel="MEAL">식사</div>
                <div class="tab-item" data-rel="DRINK">음료</div>
                <div class="tab-item" data-rel="SIDE">사이드</div>
            </div>

            <div id="menu-grid" class="menu-grid-layout">
                <!-- JS 데이터 주입 영역 -->
            </div>
        </div>
    </main>
</div>

<!-- [모달 1] 상세 정보 모달 -->
<div id="menuDetailModal" class="modal-overlay hidden">
    <div class="modal-content detail-mode">
        <button type="button" class="abs-close-btn" onclick="closeModal('menuDetailModal')">&times;</button>
        <div class="modal-body">
            <div class="modal-detail-wrapper">
                <div class="detail-img-box">
                    <img id="detail-img" src="" alt="Menu Image">
                </div>
                <div class="detail-info-box">
                    <h2 id="detail-name"></h2>
                    <div id="detail-price" class="detail-price"></div>
                    <div class="detail-ingredients-title">
                        <span><i class="fas fa-info-circle"></i> 재료 구성 (Ingredients)</span>
                    </div>
                    <div id="detail-ingredients" class="detail-ingredients-content"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- [모달 2] 메뉴 등록 모달 -->
<div id="addMenuModal" class="modal-overlay hidden">
    <div class="modal-content detail-mode" style="max-width: 550px;">
        <button type="button" class="abs-close-btn" onclick="closeModal('addMenuModal')">&times;</button>
        <h2 style="margin-bottom: 25px; font-weight: 700;">신규 메뉴 등록</h2>

        <form id="addMenuForm" class="glass-form">
            <div class="input-group" style="margin-bottom: 20px;">
                <label>메뉴 이미지 (JPG, PNG... → AVIF 변환)</label>
                <div class="image-upload-wrapper glass" onclick="$('#menuImageFile').click();"
                     style="border: 2px dashed var(--glass-border); border-radius: 15px; height: 150px; display: flex; flex-direction: column; justify-content: center; align-items: center; cursor: pointer; overflow: hidden;">
                    <img id="imagePreview" src="" class="hidden" style="width: 100%; height: 100%; object-fit: cover;">
                    <div id="uploadPlaceholder">
                        <i class="fas fa-cloud-upload-alt" style="font-size: 2rem; color: var(--accent-blue);"></i>
                        <p style="font-size: 0.8rem; margin-top: 10px; color: var(--text-sub);">클릭하여 이미지 업로드</p>
                    </div>
                </div>
                <input type="file" id="menuImageFile" name="menuImage" accept="image/*" class="hidden" onchange="previewImage(this)">
            </div>

            <div class="input-group" style="margin-bottom: 20px;">
                <label>카테고리</label>
                <div class="custom-dropdown-wrapper" id="categoryDropdown">
                    <div class="custom-dropdown-selected glass">
                        <span id="selectedCategoryText">선택하세요</span>
                        <i class="fas fa-chevron-down arrow-small"></i>
                    </div>
                    <div class="custom-dropdown-options glass">
                        <div class="custom-opt" data-value="MEAL">식사 (MEAL)</div>
                        <div class="custom-opt" data-value="DRINK">음료 (DRINK)</div>
                        <div class="custom-opt" data-value="SIDE">사이드 (SIDE)</div>
                        <div class="custom-opt" data-value="DIRECT" style="border-top: 1px dashed var(--glass-border); color: var(--accent-yellow);">
                            <i class="fas fa-plus-circle"></i> 직접 추가하기
                        </div>
                    </div>
                </div>
                <input type="text" id="categoryDirectInput" class="glass-input hidden" placeholder="카테고리명을 직접 입력하세요" style="margin-top: 10px; display: none;">
                <input type="hidden" name="category" id="selectedCategoryValue" value="">
            </div>

            <div class="input-group" style="margin-bottom: 15px;">
                <label>메뉴 이름</label>
                <input type="text" name="menuName" class="glass-input" placeholder="예: 비빔밥" required>
            </div>

            <div class="input-group" style="margin-bottom: 15px;">
                <label>가격 (RSD)</label>
                <input type="number" name="price" class="glass-input" placeholder="0" required>
            </div>

            <div class="input-group" style="margin-bottom: 25px;">
                <label>재료 (재료명:용량, 구분자 필수)</label>
                <textarea name="ingredients" class="glass-input" rows="3" placeholder="예: 애호박:50g, 당근:30g"></textarea>
            </div>

            <input type="hidden" name="shopCode" value="MAT001">

            <div class="modal-footer" style="display: flex; gap: 12px;">
                <button type="button" class="btn-modal secondary" onclick="closeModal('addMenuModal')" style="flex: 1; padding: 14px; border-radius: 12px; cursor: pointer;">취소</button>
                <button type="button" class="btn-modal primary" onclick="saveNewMenu()" style="flex: 2; padding: 14px; border-radius: 12px; cursor: pointer; font-weight: 600;">저장하기</button>
            </div>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    const ctx = "${pageContext.request.contextPath}";
    const backupNoImage = "https://placehold.co/400x400?text=No+Image";

    // 1. 서버 데이터를 JS 배열로 변환
    const fullMenuData = [
        <c:forEach var="m" items="${menus}" varStatus="status">
        {
            id: "${m.menuIdx}",
            name: '<c:out value="${m.menuName}" />',
            price: ${m.price != null ? m.price : 0},
            currency: "${m.currency}",
            category: "${m.category}",
            shopCode: "${m.shopCode}",
            imagePath: "${m.imagePath}"
        }${!status.last ? ',' : ''}
        </c:forEach>
    ];

    $(document).ready(function() {
        renderGrid('ALL');

        // 카드 클릭 이벤트 (변수명 shopCode로 통일)
        $(document).on('click', '.menu-card', function() {
            const menuIdx = $(this).attr('data-id');
            const shopCode = $(this).attr('data-shop');
            if (menuIdx && shopCode) {
                openMenuDetail(menuIdx, shopCode);
            }
        });

        $('.tab-item').on('click', function() {
            $('.tab-item').removeClass('active');
            $(this).addClass('active');
            renderGrid($(this).data('rel'));
        });

        $('#categoryDropdown').on('click', function(e) {
            e.stopPropagation();
            $(this).toggleClass('active');
        });

        $('.custom-opt').on('click', function(e) {
            e.stopPropagation();
            const val = $(this).data('value');
            const text = $(this).text();
            const $directInput = $('#categoryDirectInput');

            if (val === "DIRECT") {
                $('#selectedCategoryText').text("직접 입력");
                $directInput.removeClass('hidden').show().focus();
                $('#selectedCategoryValue').val("");
            } else {
                $directInput.addClass('hidden').hide().val("");
                $('#selectedCategoryText').text(text);
                $('#selectedCategoryValue').val(val);
            }
            $('#categoryDropdown').removeClass('active');
        });

        $(document).on('click', function() {
            $('.custom-dropdown-wrapper').removeClass('active');
        });
    });

    function renderGrid(category) {
        const $grid = $('#menu-grid');
        const items = (category === 'ALL') ? fullMenuData : fullMenuData.filter(m => m.category === category);

        let html = '';
        items.forEach(function(item) {
            const finalImg = getImagePath(item.imagePath);
            const priceStr = Number(item.price).toLocaleString();

            html += '<div class="menu-card" data-id="' + item.id + '" data-shop="' + item.shopCode + '">';
            html += '    <div class="menu-img-wrapper">';
            html += '        <img src="' + finalImg + '" alt="' + item.name + '" onerror="this.onerror=null; this.src=\'' + backupNoImage + '\';">';
            html += '    </div>';
            html += '    <div class="menu-info">';
            html += '        <span class="menu-name">' + item.name + '</span>';
            html += '        <span class="menu-price">' + priceStr + ' ' + item.currency + '</span>';
            html += '    </div>';
            html += '</div>';
        });
        if (items.length === 0) html = '<div style="grid-column: 1/-1; text-align: center; color: var(--text-sub); padding: 50px;">데이터가 없습니다.</div>';
        $grid.html(html);
    }

    // [수정] 상세 조회 함수 - shopCode 변수명 및 데이터 전달 방식 수정
    function openMenuDetail(menuIdx, shopCode) {
        $.ajax({
            url: ctx + "/detail",
            type: "GET",
            data: {
                menuIdx: menuIdx,
                shopCode: shopCode // 서버 컨트롤러의 @RequestParam("shopCode")와 일치해야 함
            },
            dataType: "json",
            success: function(data) {
                if(!data) return;
                $('#detail-name').text(data.menuName);
                $('#detail-price').text(Number(data.price).toLocaleString() + ' ' + data.currency);
                $('#detail-img').attr('src', getImagePath(data.imagePath));

                const rawIngredients = data.ingredients;
                const $container = $('#detail-ingredients');
                $container.empty();

                if (rawIngredients) {
                    let tableHtml = '<table class="ingredients-table"><tbody>';
                    rawIngredients.split(',').forEach(item => {
                        const parts = item.split(':');
                        if (parts.length === 2) {
                            tableHtml += '<tr><td><i class="fas fa-check" style="color:var(--accent-blue); font-size:0.7rem; margin-right:8px;"></i>' + parts[0].trim() + '</td><td>' + parts[1].trim() + '</td></tr>';
                        }
                    });
                    tableHtml += '</tbody></table>';
                    $container.html(tableHtml);
                }
                $('#menuDetailModal').removeClass('hidden').css('display', 'flex');
            },
            error: function(xhr) {
                alert("상세 정보를 불러오는 중 오류가 발생했습니다. (코드: " + xhr.status + ")");
            }
        });
    }

    function openAddModal() {
        $('#addMenuForm')[0].reset();
        $('#selectedCategoryText').text("선택하세요");
        $('#categoryDirectInput').addClass('hidden').hide();
        $('#selectedCategoryValue').val("");
        $('#imagePreview').addClass('hidden');
        $('#uploadPlaceholder').removeClass('hidden');
        $('#addMenuModal').removeClass('hidden').css('display', 'flex');
    }

    function saveNewMenu() {
        let finalCategory = $('#selectedCategoryValue').val();
        if (!$('#categoryDirectInput').hasClass('hidden')) {
            finalCategory = $('#categoryDirectInput').val().trim();
        }

        const menuName = $('#addMenuForm [name="menuName"]').val().trim();
        const priceRaw = $('#addMenuForm [name="price"]').val();
        const price = parseInt(priceRaw);

        if (!finalCategory) { alert("카테고리를 선택하거나 입력해주세요."); return; }
        if (!menuName) { alert("메뉴 이름을 입력해주세요."); return; }
        if (!priceRaw || isNaN(price) || price < 0) { alert("유효한 가격을 입력해주세요."); return; }

        const formData = new FormData();
        const imageFile = $('#menuImageFile')[0].files[0];
        if (imageFile) formData.append("menuImage", imageFile);

        // [수정] shopcode -> shopCode로 통일
        formData.append("shopCode", "MAT001");
        formData.append("category", finalCategory);
        formData.append("menuName", menuName);
        formData.append("price", price);
        formData.append("ingredients", $('#addMenuForm [name="ingredients"]').val());
        formData.append("currency", "RSD");
        formData.append("regUser", "admin");

        $.ajax({
            url: ctx + "/save",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function(res) {
                if (res === "success") {
                    alert("성공적으로 등록되었습니다.");
                    location.reload();
                } else if (res === "exists") {
                    alert("해당 메뉴 이름이 이미 존재합니다.");
                } else {
                    alert("저장 실패");
                }
            }
        });
    }

    function getImagePath(path) {
        if (!path || path === "" || path === "null") return backupNoImage;
        if (path.startsWith('http')) return path;
        return (ctx === "/" ? "" : ctx) + "/" + (path.startsWith('/') ? path.substring(1) : path);
    }

    function closeModal(id) {
        $('#' + id).addClass('hidden').css('display', 'none');
    }

    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                $('#imagePreview').attr('src', e.target.result).removeClass('hidden');
                $('#uploadPlaceholder').addClass('hidden');
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
</body>
</html>