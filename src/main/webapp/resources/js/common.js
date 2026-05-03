/**
 * File Name : Common.js
 * Writer : ReonQ
 * Updated Date     Version     User        Log
 * 2026-04-17           0.1                 First make
 * 2026-05-01           0.2                 Unify JS
 * 2026-05-01           0.3     ReonQ       Add Global Error Modal Logic
 *
 * Now Version : 0.3
 *
 * Description:
 * UI(테마, 드롭다운, 모달), 다국어(i18n), 파일 업로드 통합 관리
 */

window.i18n = {};

document.addEventListener('DOMContentLoaded', () => {
    initI18n();
    initTheme();
    initMobileDropdown();
    initLangDropdown();
});

/* ===========================================
   0. 다국어(i18n) 및 공통 UI 제어
   =========================================== */

function initI18n() {
    if (window.i18nData) {
        window.i18n = window.i18nData;
    }
}

/**
 * 공통 모달 열기 (CSS active 클래스 대응 추가)
 */
function openModal(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.remove('hidden');
        // modal.css의 display: flex 대응을 위해 active 추가
        setTimeout(() => modal.classList.add('active'), 10);
    }
}

function closeModal(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.remove('active');
        modal.classList.add('hidden');
    }
}

/**
 * 전역 에러 알림 모달 호출 함수
 * @param {String} title - 에러 제목 (생략 시 기본값)
 * @param {String} message - 에러 상세 내용
 */
function showError(title, message) {
    const errorTitle = document.getElementById('errorTitle');
    const errorDetail = document.getElementById('errorDetail');

    if (errorTitle) errorTitle.innerText = title || (window.i18n.errorDefaultTitle || "Error");
    if (errorDetail) errorDetail.innerText = message || (window.i18n.errorDefaultMessage || "An unexpected error occurred.");

    openModal('errorModal');
}

/* ===========================================
   1. 테마 및 드롭다운 로직
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
                const cp = window.contextPath || "";
                location.href = cp + "/changeLang?lang=" + langValue;
            }
        };
    }
    document.addEventListener('click', () => langDropdown.classList.remove('active'));
}

/* ===========================================
   2. 공통 파일 업로드 및 미리보기
   =========================================== */

function commonFileUpload(formId, url, extraData, callback) {
    const form = document.querySelector(formId);
    if (!form) return;

    const formData = new FormData(form);
    if (extraData) {
        Object.keys(extraData).forEach(key => formData.append(key, extraData[key]));
    }

    $.ajax({
        url: url,
        type: "POST",
        processData: false,
        contentType: false,
        data: formData,
        success: (res) => { if (typeof callback === "function") callback(res); },
        error: (err) => {
            console.error("Upload Error:", err);
            showError(window.i18n.alertErrorTitle, window.i18n.alertError || "File upload failed.");
        }
    });
}

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