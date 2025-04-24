/* ===== 상수 정의 ===== */
// 질문 번호 시작/끝 값
const FIRST_QUESTION = 1;
const LAST_QUESTION = 4;

/* ===== 전역 상태 정의 ===== */
// 각 성향 축(S/I, F/D, N/M, Q/A)에 대한 사용자 응답 점수를 저장하는 객체
const answers = { S: 0, I: 0, F: 0, D: 0, N: 0, M: 0, Q: 0, A: 0 };

// 추천 도서 리스트
let recommendedBooks = [];

/* ===== 독서 성향 유형 데이터 ===== */
// 각 MBTI 성향 조합에 따른 결과 정보 정의
const readerTypes = {
    SFNQ: {
        name: "몰입형 이야기꾼",
        description:
            "감성적으로 공감하며 이야기 자체에 몰입하는 독서형 감성러입니다.",
        icon: "book-open",
    },
    SFNA: {
        name: "스토리 헌터",
        description:
            "감성과 현실에 기반해 목표한 이야기를 집중적으로 찾아 읽는 탐색자입니다.",
        icon: "compass",
    },
    SFMQ: {
        name: "따뜻한 의미 수집가",
        description:
            "감성으로 의미를 찾아내며 이야기 안팎에서 여운을 곱씹는 독서가입니다.",
        icon: "heart",
    },
    SFMA: {
        name: "정보형 탐험가",
        description:
            "현실적이며 감성적인 성격으로 메시지를 중심으로 자유롭게 독서를 탐험하는 타입입니다.",
        icon: "map",
    },
    SDNQ: {
        name: "사색적 분석가",
        description:
            "이야기에서 논리적 흐름을 읽어내며 분석하는 독서형 지성인입니다.",
        icon: "brain",
    },
    SDNA: {
        name: "현실주의 전략가",
        description:
            "데이터 기반 현실주의자로 목표 독서 리스트를 체계적으로 공략하는 독자입니다.",
        icon: "target",
    },
    SDMQ: {
        name: "지식 탐험가",
        description:
            "데이터 중심 사고로 깊은 의미를 자유롭게 탐험하며 책을 즐기는 탐색가입니다.",
        icon: "search",
    },
    SDMA: {
        name: "메시지 설계자",
        description:
            "정보 중심 메시지를 중시하며 구조적이고 의도된 독서를 추구하는 성격입니다.",
        icon: "layout",
    },
    IFNQ: {
        name: "이야기 연금술사",
        description:
            "풍부한 감성과 상상력으로 이야기를 자신의 세계로 재해석하는 독자입니다.",
        icon: "wand",
    },
    IFNA: {
        name: "몽상형 독서가",
        description:
            "감성과 상상을 바탕으로 목표 독서를 즐기는 꿈꾸는 독서가입니다.",
        icon: "moon",
    },
    IFMQ: {
        name: "직관형 감정가",
        description:
            "상상 속 의미를 자유롭게 해석하며 감정을 중심으로 책을 경험하는 성격입니다.",
        icon: "feather",
    },
    IFMA: {
        name: "감성 큐레이터",
        description:
            "상상력을 자극하는 메시지를 감성적으로 수집하고 정리하는 예술적 독서가입니다.",
        icon: "palette",
    },
    IDNQ: {
        name: "창조적 탐색가",
        description:
            "이야기에서 새로운 패턴과 통찰을 끌어내는 분석형 탐색가입니다.",
        icon: "puzzle",
    },
    IDNA: {
        name: "분석형 비전가",
        description:
            "상상과 데이터를 조합해 목표한 독서 목표를 논리적으로 완수하는 독서가입니다.",
        icon: "lightbulb",
    },
    IDMQ: {
        name: "지식 개척자",
        description:
            "지적 호기심을 기반으로 자유롭게 독서를 넘나드는 창의적 유형입니다.",
        icon: "rocket",
    },
    IDMA: {
        name: "비전 메이커",
        description:
            "메시지를 통해 상상과 사실을 엮으며 깊이 있는 통찰을 추구하는 독자입니다.",
        icon: "eye",
    },
};

/* ===== 유틸리티 함수 ===== */
// 팝업 초기 표시 여부 처리
function handlePopupVisibility() {
    const popup = document.getElementById("popup-container");
    const banner = document.getElementById("mbti-banner");
    const hideUntil = localStorage.getItem("mbti-popup-hide-until");

    if (!hideUntil || new Date() >= new Date(hideUntil)) {
        popup.classList.add("active");
        banner.classList.add("active");
    } else {
        popup.classList.remove("active");
        banner.classList.remove("active");
    }
}

// 사용자 응답 점수를 바탕으로 4자리 성향 코드 생성
function calculateType() {
    const types = [
        { left: "S", right: "I" },
        { left: "F", right: "D" },
        { left: "N", right: "M" },
        { left: "Q", right: "A" },
    ];

    return types
        .map(({ left, right }) => (answers[left] >= answers[right] ? left : right))
        .join("");
}

// 결과 DOM을 받아온 성향 유형 정보로 업데이트
function displayResult(code, info) {
    const resultTypeName = document.getElementById("result-type-name");
    const resultTypeDescription = document.getElementById("result-type-description");

    resultTypeName.innerHTML = `
        <i data-lucide="${info.icon}" class="result-icon"></i>
        <span class="result-code">${code}</span>
        <span class="result-name">${info.name}</span>
    `;
    resultTypeDescription.textContent = info.description;

    // 성향 유형에 맞는 추천 책 데이터 로드
    loadRecommendedBooks(code);
}

// 성향 유형에 맞는 추천 책 데이터 로드
function loadRecommendedBooks(typeCode) {
    // 여기서는 예시 데이터를 사용
    // 추후에 서버에서 데이터 가져옴
    recommendedBooks = [
        {
            id: 1,
            title: "책 제목 1",
            author: "저자 1",
            cover: "/img/book-cover-1.jpg",
            description:
                "책 설명 1",
        },
        {
            id: 2,
            title: "책 제목 2",
            author: "저자 2",
            cover: "/img/book-cover-2.jpg",
            description:
                "책 설명 2",
        },
        {
            id: 3,
            title: "책 제목 3",
            author: "저자 3",
            cover: "/img/book-cover-3.jpg",
            description:
                "책 설명 3",
        },
        {
            id: 4,
            title: "책 제목 4",
            author: "저자 4",
            cover: "/img/book-cover-4.jpg",
            description:
                "책 설명 4",
        },
        {
            id: 5,
            title: "책 제목 5",
            author: "저자 5",
            cover: "/img/book-cover-5.jpg",
            description:
                "책 설명 5",
        },
    ];

    // 추천 책 그리드 초기화
    initBooksGrid();
}

// 추천 책 그리드 초기화
function initBooksGrid() {
    const gridContainer = document.querySelector(".recommendation-card-grid");

    // 그리드 컨테이너 초기화
    gridContainer.innerHTML = "";

    // 각 책 아이템 추가
    recommendedBooks.forEach((book) => {
        const bookItem = document.createElement("div");
        bookItem.className = "recommended-book-item";
        bookItem.innerHTML = `
          <img src="${book.cover}" alt="${book.title}" class="recommended-book-cover">
          <h4 class="recommended-book-title">${book.title}</h4>
          <p class="recommended-book-author">${book.author}</p>
          <p class="recommended-book-description">${book.description}</p>
        `;
        gridContainer.appendChild(bookItem);
    });
}

// 추천 책 섹션 토글
function toggleRecommendedBooks() {
    const section = document.getElementById("book-recommendation-modal");
    const button = document.getElementById("recommend-btn");

    section.classList.toggle("active");
    button.classList.toggle("active");
}

// 일치하는 성향 코드 보여주기
function showResult(type) {
    const resultContainer = document.getElementById("result-container");
    const container = document.getElementById("types-container");

    container.innerHTML = "";

    // 완전 일치 시 우선 처리
    if (readerTypes[type])
        displayResult(type, readerTypes[type]);

    // 결과 유형 동적으로 채우기
    Object.entries(readerTypes).forEach(([code, info]) => {
        const item = document.createElement("div");
        item.className = "type-item";
        if (code === type) item.classList.add("selected");
        item.setAttribute("data-type", code);
        item.setAttribute("title", info.description);

        // 내부 요소 구성
        item.innerHTML = `
            <i data-lucide="${info.icon}" class="type-icon"></i>
            <div class="code">${code}</div>
            <div class="name">${info.name}</div>
            <div class="description">${info.description}</div>
        `;

        container.appendChild(item);
    });

    // // lucide 아이콘 다시 렌더링
    lucide.createIcons();

    resultContainer.classList.add("active");
}

/* ===== 초기화 함수 ===== */
function init() {
    /* ===== DOM 요소 선택 ===== */
    // 컨테이너 요소
    const popup = document.getElementById("popup-container");
    const questions = document.getElementById("questions-container");
    const resultContainer = document.getElementById("result-container");
    const typeInfoBox = document.getElementById("type-info-box");
    const mbtiBanner = document.getElementById("mbti-banner");

    // 버튼 요소
    const startBtn = document.getElementById("popup-start-btn");
    const hideBtn = document.getElementById("popup-hide-btn");
    const popupCloseBtn = document.getElementById("popup-close-btn");
    const quesCloseBtn = document.getElementById("ques-close-btn");
    const nextBtns = document.querySelectorAll(".next-btn");
    const prevBtns = document.querySelectorAll(".prev-btn");
    const submitBtn = document.getElementById("submit-btn");
    const typeInfoBtn = document.getElementById("type-info-btn");
    const recommendBooksBtn = document.getElementById("recommend-btn");
    const recommendedBooksCloseBtn = document.getElementById("modal-close-btn");

    // 선택 및 진행 상태 요소
    const progressFill = document.querySelector(".progress-fill");
    const progressText = document.querySelector(".progress-text");

    /* ===== 상태 관리 ===== */
    let currentQuestion = FIRST_QUESTION;

    /* ===== 유틸리티 함수 ===== */
    // 진행 바(%) 및 질문 번호 텍스트 업데이트
    function updateProgress() {
        progressFill.style.width = `${(currentQuestion / LAST_QUESTION) * 100}%`;
        progressText.textContent = `${currentQuestion}/${LAST_QUESTION}`;
    }

    // 모든 질문이 응답되었는지 확인 후 제출 버튼 활성화
    function updateSubmitButton() {
        const allAnswered = document.querySelectorAll(".scale-option.selected").length === LAST_QUESTION;
        submitBtn.disabled = !allAnswered;
        submitBtn.classList.toggle("active", allAnswered);
    }

    // 모든 질문 숨기고 현재 질문만 보여주기
    function showQuestion(num) {
        document.querySelectorAll(".question-container").forEach(q => q.classList.remove("active"));
        document.getElementById(`q${num}`).classList.add("active");
    }

    // 현재 질문 숨기기
    function hideQuestion(num) {
        document.getElementById(`q${num}`).classList.remove("active");
    }

    // 점수 계산 후 최종 성향 판단 → 결과 출력 및 팝업 닫기
    function handleSubmitClick() {
        for (let i = FIRST_QUESTION; i <= LAST_QUESTION; i++) {
            const container = document.querySelector(`#q${i} .scale-container`);
            const selected = container.querySelector(".scale-option.selected");
            if (!selected) return alert("모든 질문에 답변해 주세요!");

            // 점수 계산 (4척도 기준)
            // 1: 왼쪽 완전 선호 (100 / 0)
            // 2: 왼쪽 약간 선호 (66.7 / 33.3)
            // 3: 오른쪽 약간 선호 (33.3 / 66.7)
            // 4: 오른쪽 완전 선호 (0 / 100)
            const value = parseInt(selected.dataset.score);
            const leftCode = container.dataset.leftCode;
            const rightCode = container.dataset.rightCode;
            const rightRatio = Math.round((value - 1) * 33.3);

            answers[leftCode] = 100 - rightRatio;
            answers[rightCode] = rightRatio;
        }

        const type = calculateType();
        showResult(type);
        alert("당신의 독서 성향 점수:\n" + JSON.stringify(answers, null, 2));
        questions.classList.remove("active")

        // TODO: 백엔드로 결과 전송
    }

    // 스케일(1~4) 선택 시 선택 상태 갱신 및 제출 버튼 상태 업데이트
    function handleScaleOptionClick(e) {
        const option = e.currentTarget;
        const container = option.closest(".scale-container");

        container.querySelectorAll(".scale-option").forEach((opt) => opt.classList.remove("selected"));
        option.classList.add("selected");
        updateSubmitButton();
    }

    /* ===== 이벤트 리스너 등록 ===== */
    // 설문 시작 버튼 클릭 시 팝업 닫고 질문 화면으로 전환
    startBtn.addEventListener("click", () => {
        popup.classList.remove("active");
        questions.classList.add("active");
        showQuestion(currentQuestion);
        updateProgress();
        updateSubmitButton();
    });

    // 설문 숨기기 버튼 클릭 시 24시간 동안 보이지 않음
    hideBtn.addEventListener("click", () => {
        const until = new Date(Date.now() + 24 * 60 * 60 * 1000); // 24시간 후
        localStorage.setItem("mbti-popup-hide-until", until.toISOString());
        popup.classList.remove("active");
        mbtiBanner.classList.remove("active");
    });

    // 시작 팝업 닫기 버튼 클릭 시 팝업 닫힘
    popupCloseBtn.addEventListener("click", () => popup.classList.remove("active"));

    // 질문 화면 닫기 버튼 클릭 시 질문 창 닫힘
    quesCloseBtn.addEventListener("click", () => questions.classList.remove("active"));

    // 제출 버튼 클릭 시 점수 계산 및 결과 출력
    submitBtn.addEventListener("click", handleSubmitClick);

    // 다음 버튼 클릭 시 다음 질문으로 이동
    nextBtns.forEach((btn) => btn.addEventListener("click", (e) => {
        const num = parseInt(e.currentTarget.closest(".question-text").dataset.question);
        if (num < LAST_QUESTION) {
            hideQuestion(num);
            currentQuestion = num + 1;
            showQuestion(currentQuestion);
            updateProgress();
        }
    }));

    // 이전 버튼 클릭 시 이전 질문으로 이동
    prevBtns.forEach((btn) => btn.addEventListener("click", (e) => {
        const num = parseInt(e.currentTarget.closest(".question-text").dataset.question);
        if (num > FIRST_QUESTION) {
            hideQuestion(num);
            currentQuestion = num - 1;
            showQuestion(currentQuestion);
            updateProgress();
        }
    }));

    // 척도 선택 클릭 시 선택 상태 갱신 및 제출 버튼 활성화 여부 확인
    document.querySelectorAll(".scale-option").forEach((option) => {
        option.addEventListener("click", handleScaleOptionClick);
    });

    // 도서 추천 팝업 화면 닫기 버튼 클릭 시 팝업 창 닫힘
    recommendedBooksCloseBtn.addEventListener("click", () => {
        document.getElementById("book-recommendation-modal").classList.remove("active");
        document.getElementById("recommend-btn").classList.remove("active");
    });

    // 도서 추천 버튼 토글 형식으로 사용
    recommendBooksBtn.addEventListener("click", toggleRecommendedBooks);

    // lucide 아이콘 최초 초기화
    lucide.createIcons();

    // 결과창이 열릴 때마다 아이콘을 새로 렌더링
    new MutationObserver((mutations) => {
        mutations.forEach((mutation) => {
            if (mutation.type === "attributes" && mutation.attributeName === "style") {
                if (resultContainer.style.display === "flex") {
                    lucide.createIcons();
                }
            }
        });
    }).observe(resultContainer, { attributes: true });

    // 마우스 올릴 시 유형 박스 표시
    typeInfoBtn.addEventListener("mouseenter", () => {
        typeInfoBox.classList.add("active");
    });

    // 마우스 떠날 시 유형 박스 숨김
    typeInfoBox.addEventListener("mouseleave", () => {
        typeInfoBox.classList.remove("active");
    });

    // 배너 클릭 시 설문 팝업 표시
    mbtiBanner.addEventListener("click", () => {
        // 팝업 숨김, 질문 창 활성화
        popup.classList.add("active");

        // 답변 초기화
        Object.keys(answers).forEach((key) => answers[key] = 0);
        document.querySelectorAll(".scale-option").forEach(opt => opt.classList.remove("selected"));

        // 질문 초기화
        currentQuestion = FIRST_QUESTION;
        showQuestion(currentQuestion);
        updateProgress();
        updateSubmitButton();
    });
}

/* ===== 초기 실행 ===== */
document.addEventListener("DOMContentLoaded", handlePopupVisibility);
document.addEventListener("DOMContentLoaded", init);

// 결과 팝업 닫힘
document.getElementById("result-close-btn").addEventListener("click", () => {
    document.getElementById("result-container").classList.remove("active");
});