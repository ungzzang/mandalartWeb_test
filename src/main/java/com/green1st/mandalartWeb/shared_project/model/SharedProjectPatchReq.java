package com.green1st.mandalartWeb.shared_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedProjectPatchReq {
    @Schema(description = "수정할 프로젝트 아이디", example = "test@gamil.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long projectId;
    @Schema(description = "로그인한 사용자 아이디", example = "test@gamil.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @Schema(description = "프로젝트 제목", example = "프로젝트 제목 입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "프로젝트 내용", example = "프로젝트 내용 입니다.")
    private String content;
}
