package com.green1st.mandalartWeb.shared_project_comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Schema(title = "공유 프로젝트 댓글 등록 요청")
@ToString
public class ProjectCommentPostReq {
    @JsonIgnore
    private long commentId;

    @Schema(title = "프로젝트 PK", example = "1"
            , requiredMode = Schema.RequiredMode.REQUIRED)
    private long projectId;
    @Schema(title = "로그인한 유저 PK", example = "2"
            , requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @Schema(title = "댓글 내용", example = "댓글입니다."
            , requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
}
