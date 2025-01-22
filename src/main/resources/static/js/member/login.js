const onlogin = ( ) => {
    // (1) INPUT DOM 가져오기
    let midInput = document.querySelector(".midInput");
    let mpwdInput = document.querySelector(".mpwdInput");

    // (2) 가져온 DOM value 가져오기
    let mid = midInput.value;
    let mpwd = mpwdInput.value;
    // (!) 유효성 검사 생략

    // (3) 입력받은 값들을 보낼 객체 만들기
    let dataObj = {mid : mid , mpwd : mpwd}
    
    // (4) 옵션 정의
    const option = {
        method : "POST" ,
        headers : {"Content-Type" : "application/json"} ,
        body : JSON.stringify(dataObj)
    }

    // fetch
    fetch("/member/login.do" , option)
        .then(r => r.json())
        .then(d => {
            if(d == true) {
                alert("로그인 성공")
                location.href="/"
            } else {alert("로그인 실패")}
        })
        .catch(e => {alert("로그인 실패. 관리자 문의 바람"); console.log(e)
        })
}