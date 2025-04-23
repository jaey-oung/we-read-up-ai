document.addEventListener("DOMContentLoaded", () => {
    initializeCheckboxes();         // 모든 항목 체크
    setupSelectAllCheckbox();       // 전체 선택 체크박스 동작
    setupItemCheckboxes();          // 개별 선택 체크박스 동작
    setupQuantityButtons();         // 수량 증가/감소 버튼
    setupDeleteAllButton();         // 전체 항목 삭제 버튼
    setupDeleteItemButtons();       // 개별 항목 삭제 버튼
    updateSummary();                // 총 수량/금액 계산
});

// 주문 시 상품정보 넘기기
document.querySelector(".order-btn").addEventListener("click", function () {
    const selectedItems = [];

    document.querySelectorAll(".cart-item").forEach(item => {
        const checkbox = item.querySelector(".select-item");
        if (checkbox.checked) {
            const cartId = item.getAttribute("data-cart-id")
            const bookId = item.getAttribute("data-book-id");
            const salePrice = item.getAttribute("data-sale-price");
            const image = item.getAttribute("data-image");
            const name = item.getAttribute("data-name");
            const quantity = item.getAttribute("data-quantity");

            selectedItems.push({
                cartId,
                bookId,
                salePrice,
                image,
                name,
                quantity
            });
        }
    });

    if (selectedItems.length === 0) {
        alert("주문할 상품을 선택해주세요.");
        return;
    }

    const form = document.createElement("form");
    form.method = "POST";
    form.action = "/orderForm";

    selectedItems.forEach((item, index) => {
        form.appendChild(makeHiddenInput(`orderBookRequestList[${index}].cartId`, item.cartId));
        form.appendChild(makeHiddenInput(`orderBookRequestList[${index}].bookId`, item.bookId));
        form.appendChild(makeHiddenInput(`orderBookRequestList[${index}].image`, item.image));
        form.appendChild(makeHiddenInput(`orderBookRequestList[${index}].name`, item.name));
        form.appendChild(makeHiddenInput(`orderBookRequestList[${index}].orderPrice`, item.salePrice * item.quantity));
        form.appendChild(makeHiddenInput(`orderBookRequestList[${index}].quantity`, item.quantity));
    });

    document.body.appendChild(form);
    form.submit();
});

// 정보(name, value) input hidden 만들기
function makeHiddenInput(name, value) {
    const input = document.createElement("input");

    input.type = "hidden";
    input.name = name;
    input.value = value;

    return input;
}

// 모든 항목을 체크 상태로 초기화
function initializeCheckboxes() {
    const selectAllCheckbox = document.querySelector(".select-all");
    const itemCheckboxes = document.querySelectorAll(".select-item");


    if (selectAllCheckbox)
        selectAllCheckbox.checked = true;

    itemCheckboxes.forEach(checkbox => checkbox.checked = true);
}

// 전체 선택 설정
function setupSelectAllCheckbox() {
    const selectAllCheckbox = document.querySelector(".select-all");
    const itemCheckboxes = document.querySelectorAll(".select-item");

    selectAllCheckbox.addEventListener("change", () => {
        itemCheckboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
        updateSummary();
    });
}

// 개별 선택 설정
function setupItemCheckboxes() {
    const selectAllCheckbox = document.querySelector(".select-all");
    const itemCheckboxes = document.querySelectorAll(".select-item");

    itemCheckboxes.forEach(checkbox => {
        checkbox.addEventListener("change", () => {
            selectAllCheckbox.checked = Array.from(itemCheckboxes).every(cb => cb.checked);
            updateSummary();
        });
    });
}

// 장바구니에 담긴 도서 수량 증가 및 감소 설정
function setupQuantityButtons() {
    document.querySelectorAll(".quantity-container").forEach(container => {
        const decreaseBtn = container.querySelector(".decrease-btn");
        const increaseBtn = container.querySelector(".increase-btn");
        const quantitySpan = container.querySelector(".quantity-number");
        const cartItem = container.closest(".cart-item");

        const cartId = parseInt(cartItem.dataset.cartId);
        const bookId = parseInt(cartItem.dataset.bookId);
        const salePrice = parseInt(cartItem.dataset.salePrice);
        const priceSpan = cartItem.querySelector(".price");

        // 수량 감소 버튼
        decreaseBtn.addEventListener("click", () => {
            let quantity = parseInt(quantitySpan.innerText);
            if (quantity <= 1) {
                alert("1보다 작을 수 없습니다");
                return;
            }

            quantity--;
            quantitySpan.innerText = quantity;
            cartItem.dataset.quantity = quantity.toString();

            const price = salePrice * quantity;
            priceSpan.innerText = price.toLocaleString() + "원";

            updateCart(cartId, bookId, quantity, price);
        });

        // 수량 증가 버튼
        increaseBtn.addEventListener("click", () => {
            let quantity = parseInt(quantitySpan.innerText);

            quantity++;
            quantitySpan.innerText = quantity;
            cartItem.dataset.quantity = quantity.toString();

            const price = salePrice * quantity;
            priceSpan.innerText = price.toLocaleString() + "원";

            updateCart(cartId, bookId, quantity, price);
        });
    });
}

// 전체 삭제 설정
function setupDeleteAllButton() {
    const deleteAllBtn = document.querySelector(".delete-all-btn");

    deleteAllBtn.addEventListener("click", () => {
        const checkedItems = document.querySelectorAll(".select-item:checked");
        const allItems = document.querySelectorAll(".select-item");

        if (checkedItems.length === 0) {
            alert("삭제할 항목을 선택해주세요.");
            return;
        }

        if (!confirm("장바구니의 항목을 삭제하시겠습니까?"))
            return;

        // 모두 선택된 경우
        if (checkedItems.length === allItems.length) {
            axios.post("/cart/remove/all")
                .then(deleteResponse)
                .catch(deleteError);
        } else { // 일부 선택된 경우
            const cartIds = Array.from(checkedItems).map(checkbox =>
                parseInt(checkbox.closest(".cart-item").dataset.cartId)
            );

            axios.post("/cart/remove/selected", cartIds)
                .then(deleteResponse)
                .catch(deleteError);
        }
    });
}

// 삭제 응답 처리
function deleteResponse(response) {
    const result = response.data;

    if (result === "LOGIN_REQUIRED") {
        if (confirm("로그인이 필요합니다. 로그인하시겠습니까?"))
            window.location.href = "/login/login";
    } else {
        alert(result);

        // 선택된 항목만 삭제
        document.querySelectorAll(".select-item:checked").forEach(checkbox => {
            checkbox.closest(".cart-item").remove();
        });
        document.querySelector(".select-all").checked = false;

        updateSummary();
        checkEmptyCart();
    }
}

// 삭제 에러 처리
function deleteError(error) {
    console.error("삭제 오류:", error);
    alert("삭제 처리 중 오류가 발생했습니다.");
}

function setupDeleteItemButtons() {
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", () => {
            const cartItem = button.closest(".cart-item");
            const cartId = parseInt(cartItem.dataset.cartId);

            if (!confirm("해당 항목을 삭제하시겠습니까?"))
                return;

            axios.post("/cart/remove/one", {
                cartId: cartId
            })
            .then(response => {
                const result = response.data;

                if (result === "LOGIN_REQUIRED") {
                    if (confirm("로그인이 필요합니다. 로그인하시겠습니까?"))
                        window.location.href = "/login/login";
                } else {
                    alert(result);
                    cartItem.remove();
                    updateSummary();
                    checkEmptyCart();
                }
            })
            .catch(error => {
                console.error("개별 삭제 중 오류:", error);
                alert("개별 삭제 중 오류가 발생했습니다.");
            });
        });
    });
}

// 장바구니 항목의 총 수량과 금액 계산
function updateSummary() {
    let cnt = 0;
    let total = 0;

    // 모든 장바구니에 대한 수량 * 단가 계산
    document.querySelectorAll(".cart-item").forEach(item => {
        const checkbox = item.querySelector(".select-item");

        if (!checkbox.checked)
            return;

        const quantity = parseInt(item.querySelector(".quantity-number").innerText);
        const salePrice = parseInt(item.dataset.salePrice);

        cnt += quantity;
        total += salePrice * quantity;
    });

    document.querySelector(".total-cnt").innerText = cnt + "개";
    document.getElementById("total-price").innerText = total.toLocaleString() + "원";

    const cartCnt = document.querySelector(".cart-cnt");
    cartCnt.innerText = document.querySelectorAll(".cart-item").length;
}

// 장바구니가 비었을 때
function checkEmptyCart() {
    const remainingItems = document.querySelectorAll(".cart-item");

    if (remainingItems.length === 0) {
        document.querySelector(".cart-empty").style.display = "block";
        document.querySelector(".cart-list").style.display = "none";
        document.querySelector(".cart-summary").style.display = "none";
    }
}

// 장바구니 정보 업데이트
function updateCart(cartId, bookId, quantity, price) {
    axios.post("/cart/update", {
        cartId: cartId,
        bookId: bookId,
        quantity: quantity,
        price: price
    })
    .then(response => {
        const result = response.data;

        if (result === "LOGIN_REQUIRED") {
            if (confirm("로그인이 필요합니다. 로그인하시겠습니까?"))
                window.location.href = "/login/login";
        } else {
            alert(result);
            updateSummary();
        }
    })
    .catch(error => {
        console.error("장바구니 수정 오류:", error);
        alert("장바구니 수정 중 오류가 발생했습니다.");
    });
}