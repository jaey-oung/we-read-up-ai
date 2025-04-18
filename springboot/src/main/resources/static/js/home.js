document.addEventListener("DOMContentLoaded", function () {
    /* ===== DOM 요소 선택 ===== */
    // 컨테이너 요소
    const popup = document.getElementById("popup-container");
    const questions = document.getElementById("questions-container");

    // 버튼 요소
    const startBtn = document.getElementById("popup-start-btn");
    const popupCloseBtn = document.getElementById("popup-close-btn");
    const quesCloseBtn = document.getElementById("ques-close-btn");
    const nextBtns = document.querySelectorAll(".next-btn");
    const prevBtns = document.querySelectorAll(".prev-btn");
    const submitBtn = document.getElementById("submit-btn");

    // 선택 및 진행 상태 요소
    const scaleOptions = document.querySelectorAll(".scale-option");
    const progressFill = document.querySelector(".progress-fill");
    const progressText = document.querySelector(".progress-text");

    /* ===== 상수 정의 ===== */
    const FIRST_QUESTION = 1;
    const LAST_QUESTION = 4;

    /* ===== 상태 관리 ===== */
    // 현재 질문 번호
    let currentQuestion = FIRST_QUESTION;
    // 성향 점수 저장 객체
    const answers = {
        S: 0, I: 0,
        F: 0, D: 0,
        N: 0, M: 0,
        Q: 0, A: 0
    };

    /* ===== 유틸리티 함수 ===== */
    // 진행 상태 업데이트
    function updateProgress() {
        progressFill.style.width = `${(currentQuestion / LAST_QUESTION) * 100}%`;
        progressText.textContent = `${currentQuestion}/${LAST_QUESTION}`;
    }

    // 제출 버튼 상태 업데이트
    function updateSubmitButton() {
        // 모든 질문에 답변했는지 확인
        const allAnswered = document.querySelectorAll('.scale-option.selected').length === LAST_QUESTION;

        submitBtn.disabled = !allAnswered;
        submitBtn.classList.toggle("active", allAnswered);
    }

    // 질문 표시/숨김 처리
    function showQuestion(num) {
        document.getElementById(`q${num}`).classList.add("active");
        updateNavigationButtons();
    }

    function hideQuestion(num) {
        document.getElementById(`q${num}`).classList.remove("active");
    }

    // 네비게이션 버튼 상태 업데이트
    function updateNavigationButtons() {
        const prevBtns = document.querySelectorAll('.prev-btn');
        const nextBtns = document.querySelectorAll('.next-btn');

        prevBtns.forEach(btn => {
            if (parseInt(btn.closest('.question-text').dataset.question) === FIRST_QUESTION) {
                btn.style.display = 'none';
            } else {
                btn.style.display = 'flex';
            }
        });

        nextBtns.forEach(btn => {
            if (parseInt(btn.closest('.question-text').dataset.question) === LAST_QUESTION) {
                btn.style.display = 'none';
            } else {
                btn.style.display = 'flex';
            }
        });
    }

    /* ===== 이벤트 핸들러 ===== */
    // 시작 버튼 클릭
    function handleStartClick() {
        popup.style.display = "none";
        questions.style.display = "flex";
        showQuestion(currentQuestion);
        updateProgress();
        updateSubmitButton();
    }

    // 닫기 버튼 클릭
    function handleCloseClick() {
        popup.style.display = "none";
    }

    function handleQuesCloseClick() {
        questions.style.display = "none";
    }

    // 다음 버튼 클릭
    function handleNextClick(e) {
        const questionText = e.currentTarget.closest('.question-text');
        const num = parseInt(questionText.dataset.question);
        if (num < LAST_QUESTION) {
            hideQuestion(num);
            currentQuestion = num + 1;
            showQuestion(currentQuestion);
            updateProgress();
        }
    }

    // 이전 버튼 클릭
    function handlePrevClick(e) {
        const questionText = e.currentTarget.closest('.question-text');
        const num = parseInt(questionText.dataset.question);
        if (num > FIRST_QUESTION) {
            hideQuestion(num);
            currentQuestion = num - 1;
            showQuestion(currentQuestion);
            updateProgress();
        }
    }

    // 제출 버튼 클릭
    function handleSubmitClick() {
        // 각 질문에 대한 점수 계산
        for (let i = FIRST_QUESTION; i <= LAST_QUESTION; i++) {
            const container = document.querySelector(`#q${i} .scale-container`);
            const selected = container.querySelector('.scale-option.selected');

            if (!selected) {
                alert("모든 질문에 답변해 주세요!");
                return;
            }

            // 점수 계산 (4척도 기준)
            // 1: 왼쪽 완전 선호 (100 / 0)
            // 2: 왼쪽 약간 선호 (66.7 / 33.3)
            // 3: 오른쪽 약간 선호 (33.3 / 66.7)
            // 4: 오른쪽 완전 선호 (0 / 100)
            const value = parseInt(selected.dataset.score);
            const leftCode = container.dataset.leftCode;
            const rightCode = container.dataset.rightCode;

            const rightRatio = ((value - 1) / 3) * 100;

            answers[leftCode] = Math.round(100 - rightRatio);
            answers[rightCode] = Math.round(rightRatio);
        }

        alert("당신의 독서 성향 점수:\n" + JSON.stringify(answers, null, 2));
        questions.style.display = "none";

        // TODO: 백엔드로 결과 전송
    }

    // 척도 옵션 클릭
    function handleScaleOptionClick(e) {
        const option = e.currentTarget;
        const parent = option.closest(".question-container");
        const container = parent.querySelector(".scale-container");

        // 이전 선택 제거
        container.querySelectorAll(".scale-option").forEach(opt => {
            opt.classList.remove("selected");
        });

        // 새로운 선택 표시
        option.classList.add("selected");
        updateSubmitButton();
    }

    /* ===== 이벤트 리스너 등록 ===== */
    startBtn.addEventListener("click", handleStartClick);
    popupCloseBtn.addEventListener("click", handleCloseClick);
    quesCloseBtn.addEventListener("click", handleQuesCloseClick);
    nextBtns.forEach(btn => btn.addEventListener("click", handleNextClick));
    prevBtns.forEach(btn => btn.addEventListener("click", handlePrevClick));
    submitBtn.addEventListener("click", handleSubmitClick);
    scaleOptions.forEach(option => option.addEventListener("click", handleScaleOptionClick));
});