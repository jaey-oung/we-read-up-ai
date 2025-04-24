/* 카테고리 메뉴 display 속성 관련 이벤트 등록  */

const navIcon = document.getElementById('nav-icon');
const table = document.getElementById('category-container');

function enter() {
    table.style.display = 'flex';
}

function leave() {
    table.style.display = 'none';
}

['mouseover', 'click'].forEach(event => {
    navIcon.addEventListener(event, enter);
    table.addEventListener(event, enter);
});

['mouseout'].forEach(event => {
    navIcon.addEventListener(event, leave);
    table.addEventListener(event, leave);
});


const large_body = document.getElementById('category_large');
const medium_body = document.getElementById('category_medium');
const small_body = document.getElementById('category_small');

let cl_id = '';
let cm_id = '';
let cs_id = '';

/* 카테고리(중/소분류) innerHTML 태그 동적 생성 */
function createNewTags(className, spanId, name) {
    const newSpan = document.createElement('span');
    newSpan.setAttribute('class', className)
    newSpan.setAttribute('id', spanId);

    const newA = document.createElement('a');
    newA.setAttribute('href', '#');
    newA.textContent = name;

    newSpan.appendChild(newA);
    return newSpan;
}

document.addEventListener('DOMContentLoaded', function () {
    /* 중분류 태그 생성 */
    const larges = large_body.querySelectorAll('span');
    larges.forEach(span => {
        span.addEventListener('mouseenter', () => {
            cl_id = span.getAttribute('id')

            /* 마우스 올릴 때마다 비동기적으로 하위 리스트 생성 */
            $.ajax({
                type: 'GET',
                url: '/ajax/ctgM',
                dataType: 'json',
                data: { cl_id: cl_id },
                success: function(result) {
                    medium_body.innerHTML = '';
                    small_body.innerHTML = '';

                    result.forEach(ctg => {
                        /* 카테고리 리스트 태그 생성 */
                        const newTags = createNewTags('category-medium', ctg.categoryMediumId, ctg.categoryMediumName);
                        medium_body.appendChild(newTags);
                    });

                    /* 소분류 태그 생성 */
                    const mediums = medium_body.querySelectorAll('span')
                    mediums.forEach(span => {
                        span.addEventListener('mouseenter', () => {
                            cm_id = span.getAttribute('id')

                            /* 마우스 올릴 때마다 비동기적으로 하위 리스트 출력 */
                            $.ajax({
                                type: 'GET',
                                url: '/ajax/ctgS',
                                dataType: 'json',
                                data: { cm_id: cm_id },
                                success: function(result) {
                                    small_body.innerHTML = '';

                                    result.forEach(ctg => {
                                        /* 카테고리 리스트 태그 생성 */
                                        const newTags = createNewTags('category-small', ctg.categorySmallId, ctg.categorySmallName);
                                        small_body.appendChild(newTags);

                                        newTags.addEventListener('mouseenter', () => {
                                            cs_id = ctg.categorySmallId;
                                        })
                                    });
                                },
                                error: function () {
                                    alert('cs error')
                                } // $.ajax()
                            })
                        });
                    });
                },
                error: function () {
                    alert('cm error')
                } // $.ajax()
            })
        });
    });

    /* 특정 카테고리(span) 클릭 시 페이지 이동하는 이벤트 등록 */
    const container = document.getElementById('category-container');
    container.addEventListener('click', function (e) {
        let pathParts = [];

        const span = e.target.closest('span'); // 카테고리 span 중 가장 가까운 span을 찾음
        const classType = span.getAttribute('class');
        if (classType === "category-large") {
            cm_id = ''; cs_id = '';
        } else if (classType === "category-medium") {
            cs_id = '';
        }

        /* 카테고리 경로 이름 가져오기 */
        const cl = document.getElementById(cl_id)?.querySelector('a')?.textContent?.trim();
        if (cl) pathParts.push(cl); // 대분류 이름

        const cm = document.getElementById(cm_id)?.querySelector('a')?.textContent?.trim();
        if (cm) pathParts.push(cm); // 중분류 이름

        const cs = document.getElementById(cs_id)?.querySelector('a')?.textContent?.trim();
        if (cs) pathParts.push(cs); // 소분류 이름

        const path = pathParts.join(' > ');

        if (!span || !container.contains(span)) return; // span이 아니거나 범위 밖이면 무시

        window.location.href = `/book/bookList?cl_id=${cl_id}&cm_id=${cm_id}&cs_id=${cs_id}&path=${encodeURIComponent(path)}`;
    });
});