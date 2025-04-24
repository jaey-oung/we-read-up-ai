const selectedForm = document.getElementById('deleteSelectedForm'); // 선택 주문 form
const deleteAllBtn = document.getElementById('delete-all-btn'); // 전체 삭제 button
const deleteSelectedBtn = document.getElementById('delete-selected-btn'); // 선택 삭제 button
const orderAllBtn = document.getElementById('order-all-btn'); // 전체 주문 button

// 전체 삭제
deleteAllBtn.addEventListener('click', function (event) {
    const identify = confirm('전체 상품을 삭제하시겠습니까?');

    if (!identify) {
        event.preventDefault();
    }
});

// 선택 삭제
deleteSelectedBtn.addEventListener('click', function (event) {
    const identify = confirm('해당 상품을 삭제하시겠습니까?');

    if (identify)
        deleteSelected();
    else
        event.preventDefault();
});

// input type=hidden 추가 후 form submit
function deleteSelected() {
    const checkList = document.querySelectorAll('input[type=checkbox]:checked');

    // checkbox에 체크된 내용 input type=hidden으로 추가
    for (let i = 0; i < checkList.length; i++) {
        const input = document.createElement('input');

        input.type = 'hidden';
        input.name = checkList[i].name;
        input.value = checkList[i].value;

        // form 태그에 input type=hidden 추가
        selectedForm.appendChild(input);
    }

    // check한 항목이 없을 때 alert 발생
    if (checkList.length === 0) {
        alert('삭제할 항목을 선택해주세요.');
        return;
    }

    // form submit
    selectedForm.submit();
}

// 개별 상품 주문
document.querySelectorAll('#order-selected-btn').forEach(orderSelectedBtn => {
    orderSelectedBtn.addEventListener('click', function () {
        const form = document.createElement('form');
        form.action = '/orderForm';
        form.method = 'post';

        const container = orderSelectedBtn.closest('.wishlist-item');
        const item = container.querySelector('.book-info');
        // 속성을 input type=hidden으로 변경 후 form에 추가
        const data = item.dataset;

        for (let key in data) {
            const input = document.createElement('input');

            input.type = 'hidden';
            input.name = `orderBookRequestList[0].${key}`;
            input.value = data[key];

            form.appendChild(input);
        }

        document.body.appendChild(form);
        form.submit();
    });
})

// 전체 상품 주문
orderAllBtn.addEventListener('click', function () {
    const form = document.createElement('form');
    form.action = '/orderForm';
    form.method = 'post';

    // bookInfo를 읽어 input type=hidden name=orderBookRequestList[i] 생성
    document.querySelectorAll('.book-info').forEach((item, index) => {
        const data = item.dataset;

        for (let key in data) {
            const input = document.createElement('input');

            input.type = 'hidden';
            input.name = `orderBookRequestList[${index}].${key}`;
            input.value = data[key];

            form.appendChild(input);
        }
    });

    document.body.appendChild(form);
    form.submit();
});