/**
 * Updated: 2026-04-10
 * Description: Theme, Mobile Menu, Language Dropdown 통합 관리
 */

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initMobileDropdown();
    initLangDropdown();
});

function initTheme() {
    const themeToggle = document.querySelector('#darkModeToggle');
    const body = document.body;
    const savedTheme = localStorage.getItem('theme') || 'dark';

    // 스위치 체크 상태 초기화
    if (themeToggle) {
        themeToggle.checked = (savedTheme !== 'light');
    }

    if (themeToggle) {
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
        // 언어 드롭다운이 열려있으면 닫기
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

    // 현재 경로에 따른 텍스트 활성화
    const currentPath = window.location.pathname;
    options.forEach(opt => {
        const val = opt.getAttribute('data-value');
        if (val && currentPath.includes(val)) {
            selectedText.textContent = opt.textContent;
            opt.classList.add('active');
        }
    });
}

/**
 * 헤더 언어 선택 드롭다운 관리
 * - 사용자가 언어를 클릭하면 localStorage를 즉시 갱신하고
 * - 서버 컨트롤러(changeLang)를 호출하여 세션과 동기화합니다.
 */
function initLangDropdown() {
    const langDropdown = document.getElementById('langDropdown');
    const currentLangText = document.getElementById('currentLangText');

    if (!langDropdown || !currentLangText) return;

    // 1. 드롭다운 토글
    const selectedArea = langDropdown.querySelector('.lang-selected');
    if (selectedArea) {
        selectedArea.onclick = (e) => {
            e.stopPropagation();
            langDropdown.classList.toggle('active');
        };
    }

    // 2. 언어 옵션 클릭 (이벤트 위임 보강)
    const optionsArea = langDropdown.querySelector('.lang-options');
    if (optionsArea) {
        optionsArea.onclick = (e) => {
            const opt = e.target.closest('.lang-opt');
            if (!opt) return;

            const langValue = opt.dataset.value;
            if (langValue) {
                // [확인용] 일단 클릭된 값부터 찍어보기
                console.log("클릭된 언어:", langValue);

                localStorage.setItem('preferredLang', langValue);

                let cp = window.contextPath;
                if (!cp || cp === "undefined") cp = "";

                const currentPath = window.location.pathname;
                const currentSearch = window.location.search;

                const targetUrl = (cp.endsWith('/') ? cp.slice(0, -1) : cp) +
                    "/changeLang?lang=" + langValue +
                    "&referer=" + encodeURIComponent(currentPath + currentSearch);

                // [디버깅 팁] 콘솔을 확인하기 위해 이동을 0.1초만 지연시켜봅니다.
                console.log("이동할 최종 URL:", targetUrl);

                setTimeout(() => {
                    window.location.href = targetUrl;
                }, 100);
            }
        };
    }

    document.addEventListener('click', () => {
        langDropdown.classList.remove('active');
    });
}