 (() => {
    const images_list = [
        {
            "url": "/img/slides/new-java.png",
            "name": "신간도서_자바의_정석_4판",
            "link": "/bookDetail?bookId=7"
        },
        {
            "url": "/img/slides/rec-health.png",
            "name": "추천_건강의_날_건강도서",
            "link": "/bookList?category=cm_1" /* 국내 건강 도서 기준 */
        },
        {
            "url": "/img/slides/event-join.png",
            "name": "회원가입_이벤트",
            "link": ""
        },
        {
            "url": "/img/slides/new-golf.png",
            "name": "신간도서_골프",
            "link": "/bookDetail?bookId=6"
        },
        {
            "url": "/img/slides/event-spring.png",
            "name": "봄_이벤트_쿠폰",
            "link": "/search?option=all&keyword=" /* 국내 도서 전체 리스트 출력 */
        },
        {
            "url": "/img/slides/rec-travel.png",
            "name": "추천_여행도서",
            "link": "/bookList?category=cm_2" /* 국내 여행 도서 기준 */
        },
        {
            "url": "/img/slides/benefit-member.png",
            "name": "회원_혜택",
            "link": ""
        }
    ];

     let slider_id = document.querySelector("#h-slider-1");
     let images_div = "";

     /* 이미지 슬라이드 태그 생성, img의 alt 값은 name과 동일 */
     for (let i = 0; i < images_list.length; i++) {
         let href = (images_list[i].link === "" ? "":' href="'+images_list[i].link+'"');
         images_div += '<a' + href + ' class="h-slides"' + (i === 0 ? ' style="display:flex"' : ' style="display:none"') + '>' +
             '<img src="' + images_list[i].url + '" alt="' + images_list[i].name + '">'
             + '</a>';
     }

     slider_id.querySelector(".h-slider-body").innerHTML = images_div;

     let slide_index = 0; /* 현재 슬라이드 번호 */
     let slider_number = slider_id.querySelector("#h-slide-number");

     function setNumberText() {
         slider_number.innerHTML = (slide_index < 10 ? "0" : "") + (slide_index + 1) + ' - ' + (images_list.length < 10 ? "0" : "") + images_list.length;
     }

     const images = slider_id.querySelectorAll(".h-slides");
     const prev_button = slider_id.querySelector("#h-slide-prev");
     const next_button = slider_id.querySelector("#h-slide-next");

     setNumberText(); /* 슬라이드 1번에도 slide-number 보이도록 설정 */

     const showSlides = () => {
         if (slide_index > images.length - 1) {
             slide_index = 0;
         }
         if (slide_index < 0) {
             slide_index = images.length - 1;
         }
         for (let i = 0; i < images.length; i++) {
             images[i].style.display = "none";
             if (i === slide_index) {
                 images[i].style.display = "flex";
             }
         }
         setNumberText();
     }

     prev_button.addEventListener("click", event => {
         event.preventDefault();
         slide_index--;
         showSlides();
     });

     next_button.addEventListener("click", event => {
         event.preventDefault();
         slide_index++;
         showSlides();
     });

     let intervalId; /* 밖에서 호출할 수 있도록 변수를 함수 바깥에서 선언 */

     /* 슬라이드 자동 재생 함수 정의 */
     function startInterval() {
         intervalId = setInterval(() => {
             slide_index++;
             showSlides();
         }, 7 * 1000);
     }

     startInterval(); /* 처음에 슬라이드 자동 재생 */

     const playButton = slider_id.querySelector("#play-button");
     const pauseButton = slider_id.querySelector("#pause-button");

     pauseButton.addEventListener("click", function() {
         playButton.style.display = "inline-block";
         pauseButton.style.display = "none";
         clearInterval(intervalId); /* ❚❚ 누르면 자동 재생 멈춤 */
     });

     playButton.addEventListener("click", function() {
         pauseButton.style.display = "inline-block";
         playButton.style.display = "none";
         startInterval(); /* ▶ 누르면 자동 재생 시작 */
     });
 })();