package com.green1st.mandalartWeb.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class MyFileUtils {
    public String getUploadPath() {
        return uploadPath;
    }

    private final String uploadPath;

    /*
       @Value("${file.directory}")dms
       yaml 파일에 있는 file.directory 속성에 저장된 값을 생성자 호출할 때 값을 넣어준다.
     */
    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        log.info("MyfileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }

    //디렉토리 생성
    public String makeFolders(String path) {
        File file = new File(uploadPath, path);

        if(!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }

    //파일명에서 확장자 추출
    public String getExt(String filaName) {
        int lastIdx = filaName.lastIndexOf(".");
        return filaName.substring(lastIdx);
    }

    //랜덤 파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤 파일명 + 확장자 생성하여 리턴
    //오버로딩
    public String makeRandomFileName(String originalFileName) {
        return makeRandomFileName() + getExt(originalFileName);
    }

    public String makeRandomFileName(MultipartFile pic) {
        return makeRandomFileName(pic.getOriginalFilename());
    }

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
    }

    // 폴더 삭제
    public void deleteFolder(String path, boolean deleteRootFolder) {
        File folder = new File(path);

        if(folder.exists() && folder.isDirectory()) { //폴더가 존재하면서 디렉토리인가?
            File[] includeFiles = folder.listFiles();

            for(File f : includeFiles) {
                if(f.isDirectory()) {
                    deleteFolder(f.getAbsolutePath(), true);
                } else {
                    f.delete();
                }
            }

            if(deleteRootFolder) {
                folder.delete();
            }
        }
    }
}
