/*
    Created by IntelliJ IDEA.
    User: ReonQ
    Date: 2026-04-10
    Time: 오후 6:50
*/

//Theme
document.addEventListener('DOMContentLoaded', () => {
    initTheme();
});

//language
document.addEventListener('DOMContentLoaded', () => {
    const mobileMenu = document.querySelector('#mobileMenu');
    if (mobileMenu) {
        const currentPath = window.location.pathname;
        Array.from(mobileMenu.options).forEach(option => {
            if (currentPath.includes(option.value)) {
                option.selected = true;
            }
        });
    }
});

/**
 * 다크모드 초기화 및 토글 이벤트 설정
 */
function initTheme() {
    const themeToggle = document.querySelector('#darkModeToggle');
    const body = document.body;

    // 1. 로컬 스토리지에서 테마 불러오기
    const savedTheme = localStorage.getItem('theme');

    // 2. 초기 상태 설정
    if (savedTheme === 'light') {
        body.classList.add('light-mode');
        body.classList.remove('dark-mode');
        if (themeToggle) themeToggle.checked = false;
    } else {
        // 기본값은 다크모드
        body.classList.add('dark-mode');
        body.classList.remove('light-mode');
        if (themeToggle) themeToggle.checked = true;
    }

    // 3. 토글 스위치 이벤트 리스너
    if (themeToggle) {
        themeToggle.addEventListener('change', (e) => {
            if (e.target.checked) {
                // 다크모드 활성화
                body.classList.replace('light-mode', 'dark-mode');
                localStorage.setItem('theme', 'dark');
            } else {
                // 라이트모드 활성화
                body.classList.replace('dark-mode', 'light-mode');
                localStorage.setItem('theme', 'light');
            }
        });
    }
}