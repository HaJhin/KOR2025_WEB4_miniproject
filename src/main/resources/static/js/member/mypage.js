// [1] 마이페이지에서 (로그인된) 내 정보 불러오기.
const myInfo = () => {
    fetch("/member/myinfo.do" , {method : "GET"})
        .then(r => r.json())
        .then(d => {
            if(d != '') // 응답 결과가 존재하면

            document.querySelector('.midInput').value = d.mid;
            document.querySelector('.mnameInput').value = d.mname;
            document.querySelector('.memailInput').value = d.memail;
        })
        .catch(e => {console.log(e);})
} // f ed
myInfo();

const myDelete = () => {
    
    let result = confirm("정말,탈퇴하시겠습니까?");
    if (result) {
    fetch("/member/delete.do" , {method : "DELETE"})
    .then(r => r.json())
    .then(d => {
        if (d == true) {
            alert("탈퇴 완료. 메인 페이지로 돌아갑니다.");
            location.href="/"
        } else {alert("탈퇴 실패.")}
    })
    .catch(e => {
        console.log(e);
        alert("탈퇴 실패. 관리자에게 문의 요망")
    })
} // if ed
}