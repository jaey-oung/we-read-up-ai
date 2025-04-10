document.addEventListener('DOMContentLoaded', () => {
    setupAddCartButtons("#add-cart-btn");

    const deleteSelectedBtn = document.getElementById("delete-selected-btn");
    const deleteAllBtn = document.getElementById("delete-all-btn");

    // 선택 삭제 버튼
    if (deleteSelectedBtn) {
        deleteSelectedBtn.addEventListener("click", () => {
            const confirmed = confirm("정말로 선택 항목을 삭제하시겠습니까?");

            if (confirmed)
                deleteSelected();
        });
    }

    // 전체 삭제 버튼
    if (deleteAllBtn) {
        deleteAllBtn.addEventListener("click", (e) => {
            const confirmed = confirm("정말로 전체 항목을 삭제하시겠습니까?");

            if (!confirmed)
                e.preventDefault();
        });
    }
});

function deleteSelected() {
    const form = document.getElementById("deleteSelectedForm");

    // 체크된 bookId만 수집해서 form에 추가
    document.querySelectorAll('input[type="checkbox"][name="bookId"]:checked').forEach(checkbox => {
        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "bookId";
        hiddenInput.value = checkbox.value;
        form.appendChild(hiddenInput);
    });

    // 선택된 항목이 없을 경우 확인
    if (form.querySelectorAll("input[name='bookId']").length === 0) {
        alert("삭제할 항목을 선택해주세요.");
        return;
    }

    form.submit();
}