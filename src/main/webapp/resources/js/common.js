/**
 * Updated: 2026-04-10
 * Description: Theme, Mobile Menu, Language Dropdown + File Upload 통합 관리
 */

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initMobileDropdown();
    initLangDropdown();
});

/* ===========================================
   1. 공통 파일 업로드 및 미리보기 로직
   =========================================== */

/**
 * 공통 파일 업로드 함수 (AJAX - FormData 활용)
 * @param {String} formId - 폼 선택자 (예: '#addMenuForm')
 * @param {String} url - 서버 엔드포인트 URL
 * @param {Object} extraData - 추가로 전송할 파라미터 객체
 * @param {Function} callback - 성공 시 실행할 콜백 함수
 */
function commonFileUpload(formId, url, extraData, callback) {
    const form = document.querySelector(formId);
    if (!form) return;

    const formData = new FormData(form);

    // 추가 데이터(category, regUser 등) 바인딩
    if (extraData) {
        Object.keys(extraData).forEach(key => {
            formData.append(key, extraData[key]);
        });
    }

    // jQuery가 포함되어 있다면 $.ajax 사용 (가독성 및 기존 코드 호환성)
    $.ajax({
        url: url,
        type: "POST",
        processData: false, // 파일 전송 시 필수
        contentType: false, // 파일 전송 시 필수
        data: formData,
        success: (res) => {
            if (typeof callback === "function") callback(res);
        },
        error: (err) => {
            console.error("Upload Error:", err);
            alert("업로드 중 오류가 발생했습니다.");
        }
    });
}

/**
 * 이미지 미리보기 공통 함수
 * @param {HTMLInputElement} input - 파일 인풋 엘리먼트
 * @param {String} previewId - 이미지를 보여줄 img 태그 ID (예: '#imagePreview')
 * @param {String} placeholderId - 업로드 아이콘 등 가릴 박스 ID (예: '#uploadPlaceholder')
 */
function handleImagePreview(input, previewId, placeholderId) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = document.querySelector(previewId);
            const placeholder = document.querySelector(placeholderId);

            if(preview) {
                preview.src = e.target.result;
                preview.classList.remove('hidden');
                preview.style.display = 'block';
            }
            if(placeholder) {
                placeholder.classList.add('hidden');
                placeholder.style.display = 'none';
            }
        }
        reader.readAsDataURL(input.files[0]);
    }
}

/* ===========================================
   2. 기존 UI 로직 (Theme, Dropdowns)
   =========================================== */

function initTheme() {
    const themeToggle = document.querySelector('#darkModeToggle');
    const body = document.body;
    const savedTheme = localStorage.getItem('theme') || 'dark';

    if (themeToggle) {
        themeToggle.checked = (savedTheme !== 'light');
        themeToggle.addEventListener('change', () => {
            if (themeToggle.checked) {
                body.classList.replace('light-mode', 'dark-mode') || body.classList.add('dark-mode');
                localStorage.setItem('theme', 'dark');
            } else {
                body.classList.replace('dark-mode', 'light-mode') || body.classList.add('light-mode');
                localStorage.setItem('theme', 'light');
            }
        });
    }
}

function initMobileDropdown() {
    const dropdown = document.getElementById('mobileDropdown');
    if (!dropdown) return;

    const selected = dropdown.querySelector('.dropdown-selected');
    const options = dropdown.querySelectorAll('.option');
    const selectedText = document.getElementById('selectedText');

    selected.onclick = (e) => {
        e.stopPropagation();
        document.getElementById('langDropdown')?.classList.remove('active');
        dropdown.classList.toggle('active');
    };

    options.forEach(option => {
        option.onclick = function(e) {
            e.stopPropagation();
            const val = this.getAttribute('data-value');
            if (val) location.href = val;
        };
    });

    const currentPath = window.location.pathname;
    options.forEach(opt => {
        const val = opt.getAttribute('data-value');
        if (val && currentPath.includes(val)) {
            selectedText.textContent = opt.textContent;
            opt.classList.add('active');
        }
    });
}

function initLangDropdown() {
    const langDropdown = document.getElementById('langDropdown');
    if (!langDropdown) return;

    const selectedArea = langDropdown.querySelector('.lang-selected');
    if (selectedArea) {
        selectedArea.onclick = (e) => {
            e.stopPropagation();
            langDropdown.classList.toggle('active');
        };
    }

    const optionsArea = langDropdown.querySelector('.lang-options');
    if (optionsArea) {
        optionsArea.onclick = (e) => {
            const opt = e.target.closest('.lang-opt');
            if (!opt) return;

            const langValue = opt.dataset.value;
            if (langValue) {
                localStorage.setItem('preferredLang', langValue);
                let cp = window.contextPath || "";
                const currentPath = window.location.pathname;
                const currentSearch = window.location.search;

                const targetUrl = (cp.endsWith('/') ? cp.slice(0, -1) : cp) +
                    "/changeLang?lang=" + langValue +
                    "&referer=" + encodeURIComponent(currentPath + currentSearch);

                setTimeout(() => { window.location.href = targetUrl; }, 100);
            }
        };
    }

    document.addEventListener('click', () => {
        langDropdown.classList.remove('active');
    });
}