// [1] 로그인 정보 요청
const getLoginMid = () => {
    // fetch 함수를 활용하여 현재 로그인 상태 체크
    // 1. fetch option
    const option = {method : "GET"}
    // 2. fetch
    fetch("/member/login/id.do" , option) 
        // .then(r => r.json())
        .then(r => r.text()) // String controller 에서 String 타입으로 반환할 겨웅에는 text() 함수로 반환해야 한다.
        .then(d => {console.log(d);

            // 로그인 상태에 따라 버튼 활성화 여부 다르게 표현
                // 1. 출력할 위치 DOM 가져오기
                let memberBox = document.querySelector(".memberBox");
                // 2. 
                let html = '';
            if(d == '') { // 응답 결과가 비어있으면
                console.log("로그아웃 상태")
                // 로그아웃 상태이면
                html += `<li class="nav-item"><a class="nav-link" href="/member/signup"> 회원가입 </a></li>
                <li class="nav-item"><a class="nav-link" href="/member/login"> 로그인 </a></li>`
            } else {console.log("로그인 중")
                // 로그아웃 버튼 , 마이페이지 버튼 , 로그인된 아이디 
                html += `<li class="nav-item"><a class="nav-link" href="#"> ${d}님 </a></li>
                <li class="nav-item"><a class="nav-link" href="/member/mypage"> 마이 페이지 </a></li>
                <li class="nav-item"><a class="nav-link" href="#" onclick="logout()"> 로그아웃 </a></li>
                `
            }
            
                memberBox.innerHTML = html;
        })
        .catch(e => {console.log(e);})
}
getLoginMid();

const logout = () => {
    const option = {method : "GET"}
    fetch("/member/logout.do" , option)
        .then(r => r.json())
        .then(d => { console.log(d)
            if (d == true) {alert("로그아웃 성공"); location.href="/member/login";
        } else {console.log("로그아웃 실패");}
        })
        .catch(e => console.log(e))
}