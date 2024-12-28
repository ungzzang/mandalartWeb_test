package com.green1st.mandalartWeb.shared_project_comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@Schema(title = "공유 프로젝트 댓글 리스트 요청")
@ToString
public class ProjectCommentGetReq {

    @Schema(title = "프로젝트 PK", description = "프로젝트 PK", example = "1"
            , requiredMode = Schema.RequiredMode.REQUIRED)
    private long projectId;

    public ProjectCommentGetReq(long projectId) {
        this.projectId = projectId;
    }
}