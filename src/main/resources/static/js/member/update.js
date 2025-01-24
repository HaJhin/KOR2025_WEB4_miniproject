// [1] 수정페이지에 들어왔을때 수정 하기 전 정보를 보여주기

const getMyInfo = () => {
    fetch("/member/myinfo.do" , {method : "GET"})
        .then(r => r.json())
        .then(d => {
            document.querySelector(".mnameInput").value = d.mname;
            document.querySelector(".memailInput").value = d.memail;
        })
        .catch(e => {console.log(e);})
}
getMyInfo();

const myUpdate = () => {
    let mname = document.querySelector(".mnameInput").value;
    let memail = document.querySelector(".memailInput").value;

    let updateObj = {mname : mname , memail : memail};

    const option = {
        method : "PUT" ,
        headers : {"Content-Type" : "application/json"} ,
        body : JSON.stringify(updateObj)
    }

    fetch("/member/update.do" , option)
        .then(r => r.json())
        .then(d => {
            if(d == true) {
                alert("수정 완료. 마이 페이지로 돌아갑니다.");
                location.href="/member/mypage";
            } else {
                alert("수정 실패.")
            }
        })
        .catch(e => {alert("수정 실패. 관리자 문의 요망"); console.log(e)})
}