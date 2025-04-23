const deleteBtn = document.getElementById('delete-user');

deleteBtn.addEventListener('click', function (event) {
    const identify = confirm('정말로 회원 탈퇴하시겠습니까?');

    if (!identify) {
        event.preventDefault();
    }
});