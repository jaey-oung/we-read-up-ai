document.addEventListener("DOMContentLoaded", function () {
    // ===== DOM 요소 선택 =====
    const popup = document.getElementById("popup-container");
    const questions = document.getElementById("questions-container");
    const startBtn = document.getElementById("popup-start-btn");
    const popupCloseBtn = document.getElementById("popup-close-btn");
    const quizCloseBtn = document.getElementById("quiz-close-btn");
    const nextBtns = document.querySelectorAll(".next-btn");
    const prevBtns = document.querySelectorAll(".prev-btn");
    const submitBtn = document.getElementById("submit-btn");
    const choiceBoxes = document.querySelectorAll(".choice-box");
    const progressFill = document.querySelector(".progress-fill");
    const progressText = document.querySelector(".progress-text");

    // ===== 상태 관리 =====
    let currentQuestion = 1;
    const answers = new Array(4).fill(null);

    // ===== 유틸리티 함수 =====
    // 진행 상태 업데이트
    function updateProgress() {
        progressFill.style.width = `${(currentQuestion / 4) * 100}%`;
        progressText.textContent = `${currentQuestion}/4`;
    }

    // 제출 버튼 상태 업데이트
    function updateSubmitButton() {
        const allAnswered = answers.every(answer => answer !== null);
        submitBtn.disabled = !allAnswered;
        submitBtn.classList.toggle("active", allAnswered);
    }

    // 질문 표시/숨김
    function showQuestion(num) {
        document.getElementById(`q${num}`).classList.add("active");
    }

    function hideQuestion(num) {
        document.getElementById(`q${num}`).classList.remove("active");
    }

    // ===== 이벤트 핸들러 =====
    function handleStartClick() {
        popup.style.display = "none";
        questions.style.display = "flex";
        showQuestion(currentQuestion);
        updateProgress();
        updateSubmitButton();
    }

    function handleCloseClick() {
        popup.style.display = "none";
    }

    function handleQuizCloseClick() {
        questions.style.display = "none";
    }

    function handleNextClick(e) {
        const num = parseInt(e.currentTarget.dataset.question);
        if (num < 4) {
            hideQuestion(num);
            currentQuestion = num + 1;
            showQuestion(currentQuestion);
            updateProgress();
        }
    }

    function handlePrevClick(e) {
        const num = parseInt(e.currentTarget.dataset.question);
        if (num > 1) {
            hideQuestion(num);
            currentQuestion = num - 1;
            showQuestion(currentQuestion);
            updateProgress();
        }
    }

    function handleSubmitClick() {
        if (answers.every(answer => answer !== null)) {
            alert("당신의 성향은: " + answers.join(""));
            questions.style.display = "none";
            // TODO: 결과 화면 띄우기
        } else {
            alert("모든 질문에 답변해 주세요!");
        }
    }

    function handleChoiceClick() {
        const parent = this.closest(".question-container");
        const code = this.dataset.code;
        const questionNumber = parseInt(parent.id.replace("q", ""));
        parent.querySelectorAll(".choice-box").forEach(cb => cb.classList.remove("selected"));
        this.classList.add("selected");
        answers[questionNumber - 1] = code;
        updateSubmitButton();
    }

    // ===== 이벤트 리스너 등록 =====
    startBtn.addEventListener("click", handleStartClick);
    popupCloseBtn.addEventListener("click", handleCloseClick);
    quizCloseBtn.addEventListener("click", handleQuizCloseClick);
    nextBtns.forEach(btn => btn.addEventListener("click", handleNextClick));
    prevBtns.forEach(btn => btn.addEventListener("click", handlePrevClick));
    submitBtn.addEventListener("click", handleSubmitClick);
    choiceBoxes.forEach(box => box.addEventListener("click", handleChoiceClick));
});