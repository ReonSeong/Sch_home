/**
 * File Name : sales_manage.js
 * Updated Date     Version     User        Change Log
 * 2026-05-07           0.1     ReonQ       Published
 *
 * Now Version : 0.1
 *
 * Description:
 * - Drill down
 * - Show chart sales_manage
 */

let dailySalesChart = null;

$(document).ready(function() {
    // contextPath가 정의되지 않았을 경우를 대비한 안전장치
    if (typeof window.contextPath === 'undefined') {
        window.contextPath = '';
    }

    // 초기 날짜 세팅 (오늘 기준 월)
    if(!$('#salesMonth').val()){
        const now = new Date();
        const currentMonth = now.getFullYear() + '-' + String(now.getMonth() + 1).padStart(2, '0');
        $('#salesMonth').val(currentMonth);
    }

    loadDailySales();

    $('#salesMonth').on('change', function() {
        loadDailySales();
    });
});

function loadDailySales() {
    const selectedMonth = $('#salesMonth').val();

    $.ajax({
        url: window.contextPath + "/api/stats/daily",
        type: "GET",
        data: { month: selectedMonth },
        success: function(data) {
            const labels = data.map(item => item.label);
            const amounts = data.map(item => item.totalAmount);

            const totalMonthSum = amounts.reduce((a, b) => a + b, 0);
            $('#totalMonthAmount').text(totalMonthSum.toLocaleString() + "원");

            renderDailyChart(labels, amounts);

            $('#selectedDateText').text('날짜를 선택하세요');
            $('#hourlySalesBody').html('<tr><td colspan="3" class="empty-msg">그래프 막대를 클릭하세요.</td></tr>');
            $('#menuDetailBody').html('<tr><td colspan="3" class="empty-msg">시간대 행을 클릭하세요.</td></tr>');
        },
        error: function(xhr) {
            console.error("Error:", xhr);
            alert("일별 매출 로드 실패");
        }
    });
}

function renderDailyChart(labels, amounts) {
    const ctx = document.getElementById('dailySalesChart').getContext('2d');

    if (dailySalesChart) {
        dailySalesChart.destroy();
    }

    dailySalesChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '일일 매출액',
                data: amounts,
                backgroundColor: 'rgba(52, 152, 219, 0.5)',
                borderColor: '#3498db',
                borderWidth: 2,
                borderRadius: 5
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true },
                x: { grid: { display: false } }
            },
            onClick: (evt, elements) => {
                if (elements.length > 0) {
                    const index = elements[0].index;
                    const selectedDate = labels[index];
                    loadHourlySales(selectedDate);
                }
            }
        }
    });
}

function loadHourlySales(date) {
    $('#selectedDateText').text(date);
    $('#hourlySalesBody').html('<tr><td colspan="3" class="empty-msg">불러오는 중...</td></tr>');

    $.ajax({
        url: window.contextPath + "/api/stats/hourly",
        type: "GET",
        data: { date: date },
        success: function(data) {
            let html = '';
            if (!data || data.length === 0) {
                html = '<tr><td colspan="3" class="empty-msg">데이터 없음</td></tr>';
            } else {
                data.forEach(item => {
                    html += `
                        <tr class="clickable-row" onclick="loadMenuDetails('${date}', '${item.label}')">
                            <td>${item.label}시</td>
                            <td>${item.count}건</td>
                            <td style="text-align: right;">${item.totalAmount.toLocaleString()}원</td>
                        </tr>`;
                });
            }
            $('#hourlySalesBody').html(html);
        }
    });
}

function loadMenuDetails(date, hour) {
    $.ajax({
        url: window.contextPath + "/api/stats/menu",
        type: "GET",
        data: { date: date, hour: hour },
        success: function(data) {
            let html = '';
            if (!data || data.length === 0) {
                html = '<tr><td colspan="3" class="empty-msg">상세 없음</td></tr>';
            } else {
                data.forEach(item => {
                    html += `
                        <tr>
                            <td>${item.label}</td>
                            <td>${item.count}</td>
                            <td style="text-align: right;">${item.totalAmount.toLocaleString()}원</td>
                        </tr>`;
                });
            }
            $('#menuDetailBody').html(html);
        }
    });
}