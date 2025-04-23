document.addEventListener("DOMContentLoaded", function () {
    setupAddCartButtons(".add-cart-btn");
});

function buyNow(button) {
    event.preventDefault();
    event.stopPropagation();

    const userId = document.getElementById("userId").value;

    if (!userId || userId === '0') {
        if (confirm('로그인이 필요합니다. 로그인 페이지로 이동할까요?')) {
            window.location.href = '/login/login';
        }
        return false;
    }

    const quantity = button.dataset.quantity;
    const bookId = button.dataset.bookId;
    const image = button.dataset.image;
    const name = button.dataset.name;
    const orderPrice = button.dataset.price * quantity;

    const form = document.createElement("form");
    form.method = "POST";
    form.action = "/orderForm";

    form.appendChild(makeHiddenInput("orderBookRequestList[0].bookId", bookId));
    form.appendChild(makeHiddenInput("orderBookRequestList[0].image", image));
    form.appendChild(makeHiddenInput("orderBookRequestList[0].name", name));
    form.appendChild(makeHiddenInput("orderBookRequestList[0].orderPrice", orderPrice));
    form.appendChild(makeHiddenInput("orderBookRequestList[0].quantity", quantity));

    document.body.appendChild(form);
    form.submit();
}

function makeHiddenInput(name, value) {
    const input = document.createElement("input");

    input.type = "hidden";
    input.name = name;
    input.value = value;

    return input;
}