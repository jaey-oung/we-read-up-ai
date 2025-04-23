// 현재 날짜 가져오기
const today = new Date();
const year = today.getFullYear();
const month = String(today.getMonth() + 1).padStart(2, '0');
const day = String(today.getDate()).padStart(2, '0');

//현재로부터 몇년 전인지
const years_ago = 200;

// 현재 날짜 형식 지정 (YYYY-MM-DD)
const formattedToday = `${year}-${month}-${day}`;

// years_ago년 전 날짜 계산
const pastDate = new Date(year - years_ago, today.getMonth(), today.getDate());
const pastYear = pastDate.getFullYear();
const formattedPastDate = `${pastYear}-${month}-${day}`;

// HTML input 요소에 min과 max 속성 설정
const birth = document.getElementById('birth');
birth.setAttribute('min', formattedPastDate);
birth.setAttribute('max', formattedToday);