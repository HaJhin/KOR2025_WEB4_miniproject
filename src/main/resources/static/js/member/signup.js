// JS 자동완성 : 1. VSCODE 2. tabnine

// 1. 회원가입 함수
const onSignUp = ( ) => { console.log("함수 실행.")
    // (1) INPUT DOM 가져오기
    let midInput = document.querySelector(".midInput"); console.log(midInput);
    let mpwdInput = document.querySelector(".mpwdInput"); console.log(mpwdInput);
    let mpwdCheckInput = document.querySelector(".mpwdCheckInput"); console.log(mpwdCheckInput);
    let mnameInput = document.querySelector(".mnameInput"); console.log(mnameInput);
    let memailInput = document.querySelector(".memailInput"); console.log(memailInput);

    // (2) DOM의 value(입력값) 반환
    let mid = midInput.value; console.log(mid);
    let mpwd = mpwdInput.value; console.log(mpwd);
    let mpwdCheck = mpwdCheckInput.value; console.log(mpwdCheck);
    let mname = mnameInput.value; console.log(mname);
    let memail = memailInput.value; console.log(memail);

    // (3) 입력받은 값 객체화
    let dataObj = {mid : mid , mpwd : mpwd , mname : mname , memail : memail}
    console.log(dataObj);
    // (4) fetch 옵션
    let option = {
        method : 'POST' ,
        headers : {"Content-Type" : "application/json"} , // HTTP 통신 요청을 보낼때 header body(본문) 타입 설정
        body : JSON.stringify(dataObj) // HTTP 통신 요청을 보낼때 body(본문) 자료를 대입하는데
            // JSON.stringify(객체) : 객체타입 --> 문자열 타입 변환 , HTTP는 문자열 타입만 전송 가능
    }
    // (5) fetch 통신
    fetch("/member/signup.do" , option) // fetch('통신할 URL' , 옵션)
        .then(r => r.json()) // .then() 통신 요청을 보내고 응답 객체를 반환받고 .json()을 이용한 응답객체를 json타입으로 반환
        .then(d => {
            if(d == true) {
                alert("가입 성공. 로그인 페이지로 돌아갑니다.")
                location.href="/member/login";
            } else {
                alert("가입 실패.")
            } // if ed
        })
        .catch(e => {alert("가입 실패. 관리자에게 문의 요망"); console.log(e)})
    // (6) fetch 응답에 따른 화면 구현
} // f ed