document.addEventListener("DOMContentLoaded", function () {
    const body = document.querySelector("body");
    const firstCategoryTitle = document.querySelector(".first-category-title");
    const userTab = document.querySelector("#user-tab");
    const memberTab = document.querySelector("#member-tab");
    const form = document.querySelector(".third-category");
    const title = document.querySelector("#signup-title");
    const memberFields = document.querySelectorAll(".member-field");
    const checkBtn = document.querySelector(".check-btn");
    const emailInput = document.getElementById("email");
    const submitBtn = document.querySelector(".btn.submit");

    // 회원 전용 필드 숨기기
    function hideMemberFields() {
        memberFields.forEach(el => el.classList.add("hidden"));
        form.action = "/register/user";
        firstCategoryTitle.textContent = "홈 / 비회원가입";
        userTab.classList.add("active");
        memberTab.classList.remove("active");
        title.textContent = "비회원가입";
    }

    // 회원 전용 필드 보이기
    function showMemberFields() {
        memberFields.forEach(el => el.classList.remove("hidden"));
        form.action = "/register/member";
        firstCategoryTitle.textContent = "홈 / 회원가입";
        userTab.classList.remove("active");
        memberTab.classList.add("active");
        title.textContent = "회원가입";
    }

    const defaultTab = body.dataset.tab || "user"; // 없으면 기본값은 user
    if (defaultTab === "member") {
        showMemberFields();
    } else {
        hideMemberFields();
    }

    // 중복확인 버튼 눌렀을 때
    checkBtn.addEventListener("click", function () {
        const email = emailInput.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // 이메일이 비었을 때
        if (email.length === 0) {
            alert("이메일을 작성해주세요")
            return;
        }

        // 이메일 형식이 맞지 않을 때
        if (!emailRegex.test(email)) {
            alert("올바른 이메일 형식이 아닙니다");
            return;
        }

        axios.post("/register/check/email", {
            email: email
        })
            .then(response => {
                alert(response.data);

                if (response.data === "사용 가능한 이메일입니다") {
                    emailInput.readOnly = true;
                    submitBtn.disabled = false;
                } else {
                    submitBtn.disabled = true;
                }
            })
            .catch(error => {
                console.error("중복확인 오류:", error);
                alert("이메일 중복 확인 중 오류가 발생했습니다.");
            });
    });

    // 회원가입 탭을 눌렀을 때
    userTab.addEventListener("click", hideMemberFields);

    // 비회원가입 탭을 눌렀을 때
    memberTab.addEventListener("click", showMemberFields);

    // 폼 제출할 때
    form.addEventListener("submit", function (e) {
        const isMember = memberTab.classList.contains("active");
        console.log(isMember)

        // 공통 필드
        const email = emailInput.value.trim();
        const password = document.getElementById("password").value.trim();
        const pwConfirm = document.getElementById("password-confirm").value.trim();
        const name = document.getElementById("name").value.trim();

        // 기본 필드 검사
        if (!email || !password || !pwConfirm || !name) {
            alert("모든 필수 항목을 입력해주세요");
            e.preventDefault();
            return;
        }

        // 회원일 경우 회원 전용 필드 검사
        if (isMember) {
            const nickname = document.getElementById("nickname").value.trim();
            const phone = document.getElementById("phone").value.trim();
            const birth = document.getElementById("birth").value.trim();
            const gender = document.querySelector('input[name="gender"]:checked');

            if (!nickname || !phone || !birth || !gender) {
                alert("모든 필수 항목을 입력해주세요");
                e.preventDefault();
                return;
            }
        }

        // 비밀번호 확인 일치 여부
        if (password !== pwConfirm) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다 ");
            e.preventDefault();
        }
    });

});
