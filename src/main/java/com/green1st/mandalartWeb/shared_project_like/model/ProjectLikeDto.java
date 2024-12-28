package com.green1st.mandalartWeb.shared_project_like.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title="프로젝트 좋아요")
public class ProjectLikeDto {
    @Schema(description = "공유할 프로젝트 아이디(등록된 프로젝트여야함)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long projectId;
    @Schema(description = "유저이메일", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
}
