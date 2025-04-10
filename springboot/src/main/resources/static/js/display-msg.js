document.addEventListener("DOMContentLoaded", function () {
    const msg = document.body.dataset.msg;

    if (!msg) return;

    displayMsg(msg);
});

function displayMsg(msg) {
    switch (msg) {
        case "USER_SIGNUP_OK":
            alert("비회원 계정 생성 성공");
            break;

        case "USER_SIGNUP_ERR":
            alert("비회원 계정 생성 실패");
            break;

        case "MEMBER_SIGNUP_OK":
            alert("회원 계정 생성 성공");
            break;

        case "MEMBER_SIGNUP_ERR":
            alert("회원 계정 생성 실패");
            break;

        case "EMPTY_FIELD":
            alert("아이디 또는 비밀번호를 입력해주세요");
            break;

        case "LOGIN_ERR":
            alert("등록되지 않은 계정입니다");
            break;
    }
}