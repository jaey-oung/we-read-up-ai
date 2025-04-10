function setDateRange(event, daysAgo) {
    event.preventDefault(); // 폼 제출 방지

    const today = new Date();
    const startDate = new Date();

    // 시작 날짜 설정 (오늘 날짜에서 daysAgo 만큼 뺌)
    startDate.setDate(today.getDate() - daysAgo);

    // 날짜를 YYYY-MM-DD 형식으로 변환하는 함수
    function formatDate(date) {
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0'); // 월 (0부터 시작하므로 +1 필요)
    const dd = String(date.getDate()).padStart(2, '0'); // 일
    return `${yyyy}-${mm}-${dd}`;
}

    // input 요소에 값 설정
    document.getElementById("startDate").value = formatDate(startDate);
    document.getElementById("endDate").value = formatDate(today);
}