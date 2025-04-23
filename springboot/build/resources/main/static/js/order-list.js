function setDate(keyword) {
    const startDate = document.getElementById('startDate');
    const day = new Date();

    switch (keyword) {
        // 1주일 버튼 클릭 시
        case 'oneWeek':
            day.setDate(day.getDate() - 7);
            break;
        // 1개월 버튼 클릭 시
        case 'oneMonth':
            day.setMonth(day.getMonth() - 1, day.getDate() + 1);
            break;
        // 6개월 버튼 클릭 시
        case 'sixMonth':
            day.setMonth(day.getMonth() - 6, day.getDate() + 1);
            break;
        // 오늘 버튼 클릭 시
        default:
            break;
    }

    const year = day.getFullYear();
    const month = String(day.getMonth() + 1).padStart(2, '0');
    const date = String(day.getDate()).padStart(2, '0');

    startDate.value = `${year}-${month}-${date}`;
}