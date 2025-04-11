// 팝업 닫기 버튼 기능
document.addEventListener("DOMContentLoaded", function () {
    const popup = document.getElementById("survey-popup");
    const closeBtn = document.getElementById("survey-popup-close");
    const startBtn = document.getElementById("survey-popup-start");

    // 닫기 버튼 클릭 시 팝업 숨김
    closeBtn.addEventListener("click", () => {
        popup.style.display = "none";
    });

    // 설문 시작 버튼 클릭 시 실행될 로직
    startBtn.addEventListener("click", () => {
        // TODO: 설문 시작 로직
        alert("설문 시작!");
    });
});