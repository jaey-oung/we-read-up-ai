function setupAddCartButtons(selector) {
    const addCartButtons = document.querySelectorAll(selector);

    addCartButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            // 기본 동작 방지 (a 태그 대응 포함)
            event.preventDefault();
            event.stopPropagation();

            const bookId = button.dataset.bookId;
            const price = button.dataset.price;

            axios.post("/cart/add/one", { bookId, price })
                .then(response => {
                    const message = response.data;

                    if (message === "LOGIN_REQUIRED") {
                        if (confirm("로그인이 필요합니다. 로그인 페이지로 이동할까요?")) {
                            window.location.href = "/login/login";
                        }
                    } else {
                        alert(message);
                    }
                })
                .catch(error => {
                    console.log(error);
                    alert("장바구니 요청 중 문제가 발생했습니다.");
                });
        });
    });
}