/**
 * Updated: 2026-04-10
 * Description: Theme, Mobile Menu, Language Dropdown 통합 관리
 */

document.addEventListener('DOMContentLoaded', () => {
    initTheme();           // 1. 테마 설정
    initMobileDropdown();  // 2. 모바일 메뉴
    initLangDropdown();    // 3. 언어 선택 (컨트롤러 연동)
});

/**
 * 테마 초기화 및 토글
 */
function initTheme() {
    const themeToggle = document.querySelector('#darkModeToggle');
    const body = document.body;
    const savedTheme = localStorage.getItem('theme') || 'dark';

    if (savedTheme === 'light') {
        body.classList.add('light-mode');
        if (themeToggle) themeToggle.checked = false;
    }

    if (themeToggle) {
        themeToggle.onchange = (e) => {
            if (e.target.checked) {
                body.classList.remove('light-mode');
                localStorage.setItem('theme', 'dark');
            } else {
                body.classList.add('light-mode');
                localStorage.setItem('theme', 'light');
            }
        };
    }
}

/**
 * 모바일 메인 메뉴 드롭다운
 */
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

/**
 * 헤더 언어 선택 드롭다운 (서버 컨트롤러 연동 강화)
 */
function initLangDropdown() {
    const langDropdown = document.getElementById('langDropdown');
    if (!langDropdown) return;

    const selected = langDropdown.querySelector('.lang-selected');
    const options = langDropdown.querySelectorAll('.lang-opt');
    const currentLangText = document.getElementById('currentLangText');

    // 1. 현재 URL 파라미터로 텍스트 표시 업데이트
    const urlParams = new URLSearchParams(window.location.search);
    const langParam = urlParams.get('lang');
    if (langParam) {
        currentLangText.textContent = langParam.toUpperCase();
    }

    // 2. 드롭다운 토글
    selected.onclick = (e) => {
        e.stopPropagation();
        langDropdown.classList.toggle('active');
        document.getElementById('mobileDropdown')?.classList.remove('active');
    };

    // 3. [핵심] 언어 선택 시 컨트롤러 호출
    options.forEach(opt => {
        opt.onclick = function(e) {
            e.stopPropagation();
            const langValue = this.getAttribute('data-value');

            console.log("[JS] 선택된 언어:", langValue);

            if (langValue) {
                // 가장 안전하게 ContextPath 가져오는 방법
                // 만약 localhost:8080/ 이면 "", localhost:8080/Project 면 "/Project" 반환
                const host = window.location.host;
                const path = window.location.pathname;
                const contextPath = path.substring(0, path.indexOf('/', 1) === -1 ? 0 : path.indexOf('/', 1));

                // 최종 타겟 URL 생성
                const targetUrl = contextPath + "/changeLang?lang=" + langValue;

                console.log("[JS] 이동할 URL:", targetUrl);

                // 페이지 이동 (이때 LanguageController가 호출되어야 함)
                window.location.href = targetUrl;
            }
        };
    });

    // 바깥 영역 클릭 시 닫기
    document.addEventListener('click', () => {
        langDropdown.classList.remove('active');
    });
}