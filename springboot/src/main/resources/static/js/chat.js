const icon = document.getElementById('chatbot-icon'); // 챗봇 아이콘 버튼
const mainPopup = document.querySelector('.hidden'); // 메인 페이지 팝업창
const chatBtn = document.querySelector('.chatbot-menu li:first-child'); // 메인 페이지 실시간 채팅 버튼
const boardBtn = document.querySelector('.chatbot-menu li:nth-child(2)');
const bookListBtn = document.querySelector('.chatbot-menu li:nth-child(3)');
const myPageBtn = document.querySelector('.chatbot-menu li:last-child');
const cancel = document.getElementById('cancel-btn'); // 팝업 밑 취소 버튼
const chatPopup = document.querySelector('.chat-wrapper'); // 실시간 채팅 팝업창
const previousBtn = document.querySelector('.chat-header button'); // 실시간 채팅 좌측 상단 이전 버튼
const chatBody = document.querySelector('.chat-body'); // 실시간 채팅 메시지 영역
const chatInput = document.querySelector('.chat-footer input'); // 실시찬 채팅 메시지 입력 창
const sendBtn = document.querySelector('.chat-footer button'); // 실시간 채팅 전송 버튼

// 챗봇에서 게시판 문의하기 버튼 클릭 시 게시판으로 이동
boardBtn.addEventListener('click', function() {
   window.location.href = '/board/notice-list';
});

// 챗봇에서 상품 확인하기 버튼 클릭 시 상품 리스트로 이동
bookListBtn.addEventListener('click', function() {
    window.location.href = '/bookList?category=cl_1';
});

// 챗봇에서 마이페이지로 이동하기 버튼 클릭 시 마이페이지로 이동
myPageBtn.addEventListener('click', function() {
    window.location.href = '/myPage';
});

// uuid 생성
if (!localStorage.getItem("uuid")) {
    localStorage.setItem("uuid", crypto.randomUUID());
}
const uuid = localStorage.getItem("uuid");

// 챗봇 아이콘 클릭 시 팝업창 띄우기
icon.addEventListener('click', function() {
    icon.classList.add('hidden');
    mainPopup.classList.remove('hidden');
});

// 닫기 버튼 클릭 시 팝업창 닫기
cancel.addEventListener('click', function() {
    mainPopup.classList.add('hidden');
    icon.classList.remove('hidden');
});

// 이전 버튼 클릭 시 메인 팝업창 열기
previousBtn.addEventListener('click', function() {
    chatPopup.classList.add('hidden');
    mainPopup.classList.remove('hidden');
});

// 채팅방 진입 시 이전 메시지 로드
chatBtn.addEventListener('click', function() {
    mainPopup.classList.add('hidden');
    chatPopup.classList.remove('hidden');
    loadChatHistory();
});

// 입력 후 전송 버튼 클릭,엔터 시 해당 메시지 챗봇에 적용
sendBtn.addEventListener('click', sendMessage);
chatInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
});





// 

// 화면에 메시지 보여주는 함수
function appendMessage(message, sender, regDate) {
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${sender}`;
    const formattedMessage = message.replace(/(?<!\d)\. (?=[A-Z가-힣])/g, '.<br>');
    const formattedDate = regDate.slice(0, 19).replace('T', ' ');

    if (sender === 'bot') {
        messageDiv.innerHTML = `
            <div class="avatar"></div>
            <div class="bubble-container">
                <div class="bubble">
                    ${formattedMessage}
                </div>
                <div class="timestamp">${formattedDate}</div>
            </div>
        `;
    } else {
        messageDiv.innerHTML = `
            <div class="bubble-container">
                <div class="bubble">${message}</div>
                <div class="timestamp">${formattedDate}</div>
            </div>
        `;
    }

    chatBody.appendChild(messageDiv);
    chatBody.scrollTop = chatBody.scrollHeight;
}

// 채팅방 진입 시 이전 메시지 로드
async function loadChatHistory() {
    try {
        const response = await fetch(`/chat/history?uuid=${uuid}`);

        if (!response.ok) {
            throw new Error('응답에 실패하였습니다.');
        }

        const messages = await response.json();
        chatBody.innerHTML = ''; // 기존 메시지 초기화

        messages.forEach(msg => {
            const regDate = new Date(msg.regDate);
            const krRegDate = new Date(regDate.getTime() + (1000 * 60 * 60 * 9)).toISOString();
            appendMessage(msg.message, msg.sender, krRegDate);
        });
    } catch (error) {
        console.error(`메시지 기록 로드 실패 : ${error}`);
    }
}

// 채팅 입력
async function sendMessage() {
    const now = new Date(new Date().getTime() + (1000 * 60 * 60 * 9)).toISOString();

    // 입력창 메시지
    const message = chatInput.value.trim();
    chatInput.value = '';

    if (message) {
        // 사용자 메시지 채팅에 추가
        appendMessage(message, 'user', now);

        try {
            const response = await fetch('/chat/send', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    uuid: uuid,
                    sender: 'user',
                    message: message,
                    regDate: now
                })
            });

            if (!response.ok) {
                throw new Error(response.statusText);
            }
            // 응답 값 JSON 객체로 변환
            const data = await response.json();
            // 응답 메시지 채팅에 표시
            appendMessage(data.message, data.sender, data.regDate);
        } catch (error) {
            console.error(`채팅 입력 실패 : ${error}`)
        }
    }
}