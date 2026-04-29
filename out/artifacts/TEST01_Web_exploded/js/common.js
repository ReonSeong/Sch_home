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

    // 1. 초기 테마 설정 (저장된 게 없으면 기본 'dark')
    const savedTheme = localStorage.getItem('theme') || 'dark';

    // 초기 상태 반영: 확실하게 하기 위해 클래스를 정리하고 새로 고정
    if (savedTheme === 'light') {
        body.classList.remove('dark-mode');
        body.classList.add('light-mode');
        if (themeToggle) themeToggle.checked = false;
    } else {
        body.classList.remove('light-mode');
        body.classList.add('dark-mode');
        if (themeToggle) themeToggle.checked = true;
    }

    // 2. 토글 이벤트
    if (themeToggle) {
        themeToggle.addEventListener('change', () => {
            if (themeToggle.checked) {
                // 다크 모드 활성화
                body.classList.replace('light-mode', 'dark-mode') || body.classList.add('dark-mode');
                localStorage.setItem('theme', 'dark');
            } else {
                // 라이트 모드 활성화
                body.classList.replace('dark-mode', 'light-mode') || body.classList.add('light-mode');
                localStorage.setItem('theme', 'light');
            }

            // [선택 사항] 콘솔 로그로 상태 확인 (디버깅용)
            console.log("현재 테마:", localStorage.getItem('theme'));
        });
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
 * 헤더 언어 선택 드롭다운
 */
function initLangDropdown() {
    const langDropdown = document.getElementById('langDropdown');
    const currentLangText = document.getElementById('currentLangText');
    if (!langDropdown || !currentLangText) return;

    const options = langDropdown.querySelectorAll('.lang-opt');
    const savedLang = localStorage.getItem('preferredLang') || 'ko';

    // 1. 초기 텍스트 설정
    const initialOpt = Array.from(options).find(opt => opt.dataset.value === savedLang);
    if (initialOpt) currentLangText.textContent = initialOpt.textContent;

    // 2. 드롭다운 토글
    langDropdown.querySelector('.lang-selected').onclick = (e) => {
        e.stopPropagation();
        langDropdown.classList.toggle('active');
    };

    // 3. 언어 선택 (수정된 로직)
    langDropdown.querySelector('.lang-options').onclick = (e) => {
        const opt = e.target.closest('.lang-opt');
        if (!opt) return;

        const langValue = opt.dataset.value;
        if (langValue) {
            // 로컬스토리지 저장
            localStorage.setItem('preferredLang', langValue);

            // ContextPath 계산
            const cp = window.location.pathname.split('/')[1];
            const contextPath = cp && !cp.includes('.') ? `/${cp}` : '';
            const currentPath = window.location.pathname;
            const targetUrl = `${contextPath}/changeLang?lang=${langValue}&referer=${encodeURIComponent(currentPath)}`;

            window.location.href = targetUrl;
        }
    };

    // 4. 바깥 클릭 시 닫기
    document.addEventListener('click', () => langDropdown.classList.remove('active'));
}