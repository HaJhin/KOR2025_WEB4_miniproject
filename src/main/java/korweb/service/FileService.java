package korweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    // * 서버(톰캣/build) 경로 내 img 폴더
    // 프로젝트폴더명 -> build-> resources -> main -> static -> img 폴더 오른쪽클릭 'copy path'
    // pc 마다 경로의 이름이 다 다르다.
    // 마지막 경로 뒤에 \\(백슬래시) 2개 추가
    String uploadPath = "D:\\KMH\\KOR2025_WEB4_2\\build\\resources\\main\\static\\img\\";

    // 1. 업로드 함수/메서드
    public String fileUpload(MultipartFile multipartFile) {

        // (1) 매개변수로 MultipartFile 타입 객체를 받는다. : 첨부파일이 들어있는 객체
        System.out.println(multipartFile.getOriginalFilename()); // 첨부파일의 파일명을 반환하는 함수
        System.out.println(multipartFile.getName()); // 첨부파일의 속성명을 반환하는 함수
        System.out.println(multipartFile.getSize()); // 첨부파일의 용량을 반환하는 함수
        System.out.println(multipartFile.isEmpty()); // 첨부파일이 존재하는 여부 반환 함수

        // (*) 만약 서로 다른 파일을 동일한 이름으로 업로드 했을때 식별 불가능
        // 방안 : 파일명 앞에 UUID 난수 텍스트 조합
            // 1. UUID 생성
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

        // (2) 업로드 경로와 파일명 조합하기 , 업로드경로 + UUID + 파일명
            // 2. UUID 의 구분자는 '-' 하이픈을 사용하므로, 파일명에 하이픈이 존재하면 안된다.
            // -> 파일명에 '-' 하이픈을 모두 '_' 언더바로 바꾼다.
            // 예시) UUID-파일명
        String fileName = uuid + "-" + multipartFile.getOriginalFilename().replaceAll("-","_");
        String uploadFile = uploadPath + fileName;

        // (3) 조합된 경로로 file 클래스 객체 만들기
        File file = new File(uploadFile);

        // (4) 업로드 하기! , transferTo(지정된 경로);
        try {multipartFile.transferTo(file);} catch (IOException e) {System.out.println(e);}
        return fileName;
    }

    // 2. 다운로드 함수/메서드

} // c ed
