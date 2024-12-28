package com.green1st.mandalartWeb.project;

import com.green1st.mandalartWeb.common.MyFileUtils;
import com.green1st.mandalartWeb.common.model.ResultResponse;
import com.green1st.mandalartWeb.project.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectMapper projectMapper;
    private final MyFileUtils myFileUtils;

    @Transactional
    public ResultResponse<?> postProject(ProjectPostReq p) {
        // 프로젝트 생성
        int result = projectMapper.insProject(p);

        if(result == 1) {
            // 만다라트 생성(9 x 9 = 81 개)
            long projectId = p.getProjectId();

            MandalartInsDto firstMandalart = new MandalartInsDto();

            firstMandalart.setProjectId(projectId);
            firstMandalart.setParentId(null);
            firstMandalart.setDepth(0);
            firstMandalart.setOrderId(0);

            result = projectMapper.insMandalart(firstMandalart);

            if(result != 0) {
                List<Long> parentIds = new ArrayList<>(8);

                for (int i = 0; i < 8; i++) {
                    MandalartInsDto secondMandalart = new MandalartInsDto();
                    secondMandalart.setProjectId(projectId);
                    secondMandalart.setParentId(firstMandalart.getMandalartId());
                    secondMandalart.setDepth(1);
                    secondMandalart.setOrderId(i);

                    result = projectMapper.insMandalart(secondMandalart);

                    parentIds.add(secondMandalart.getMandalartId());
                }

                for (long item : parentIds) {
                    for (int i = 0; i < 8; i++) {
                        MandalartInsDto lastMandalart = new MandalartInsDto();
                        lastMandalart.setProjectId(projectId);
                        lastMandalart.setParentId(item);
                        lastMandalart.setDepth(2);
                        lastMandalart.setOrderId(i);

                        result = projectMapper.insMandalart(lastMandalart);

                    }
                }
            }

            if(result > 0) {
                ProjectPostRes projectPostRes = new ProjectPostRes();
                projectPostRes.setProjectId(projectId);

                return ResultResponse.<ProjectPostRes>builder()
                        .statusCode("200")
                        .resultData(projectPostRes)
                        .resultMsg("프로젝트 생성완료")
                        .build();
            }
        }

        return ResultResponse.<Integer>builder()
                .statusCode("400")
                .resultData(0)
                .resultMsg("프로젝트 생성실패")
                .build();
    }

    public ResultResponse<?> getProject(ProjectGetReq p) {
        List<ProjectGetRes> projectList = projectMapper.selProjectList(p);

        return ResultResponse.<List<ProjectGetRes>>builder()
                .statusCode("200")
                .resultData(projectList)
                .resultMsg("프로젝트 조회 완료")
                .build();
    }

    @Transactional
    public ResultResponse<?> patchProject(MultipartFile pic, ProjectPatchReq p) {
        String saveFileName = (pic != null) ? myFileUtils.makeRandomFileName(pic) : null; // 저장할 파일명

        p.setPic(saveFileName);

        // 프로젝트 수정
        int result = projectMapper.updProject(p);

        if(result == 1) {
            // 유저정보가 성공적으로 저장됐고 사진 파일이 존재할 경우
            if(pic != null) {
                //파일 업로드(저장할 경로 user/{프로젝트 번호})
                String middlePath = String.format("project/%d", p.getProjectId());

                // 폴더 생성
                myFileUtils.makeFolders(middlePath);

                // 기존 파일 삭제(방법 2가지: [1]: 폴더를 지운다. [2]select해서 기존 파일명을 얻어온다. [3]기존 파일명을 FE에서 받는다.)
                String deletePath = String.format("%s/project/%d",myFileUtils.getUploadPath(), p.getProjectId());
                myFileUtils.deleteFolder(deletePath, false);

                // 파일 저장 경로
                String path = middlePath + "/" + saveFileName;

                // 파일 저장
                try {
                    myFileUtils.transferTo(pic, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return ResultResponse.<Integer>builder()
                    .statusCode("200")
                    .resultData(1)
                    .resultMsg("프로젝트 수정완료")
                    .build();
        }

        return ResultResponse.<Integer>builder()
                .statusCode("400")
                .resultData(0)
                .resultMsg("프로젝트 수정실패")
                .build();
    }

    @Transactional
    public ResultResponse<?> delProject(ProjectDelReq p) {
        // 만다라트 삭제
        int result = projectMapper.delMandalart(p.getProjectId());


        if(result > 0) {
            // 프로젝트 삭제
            result = projectMapper.delProject(p);

            // 프로젝트 사진 파일이 존재할 경우 삭제
            String deletePath = String.format("%s/project/%d",myFileUtils.getUploadPath(), p.getProjectId());
            myFileUtils.deleteFolder(deletePath, true);

            if(result == 1) {
                return ResultResponse.<Integer>builder()
                        .statusCode("200")
                        .resultData(1)
                        .resultMsg("프로젝트 삭제완료")
                        .build();
            }
        }

        return ResultResponse.<Integer>builder()
                .statusCode("400")
                .resultData(0)
                .resultMsg("프로젝트 삭제실패")
                .build();
    }
}
