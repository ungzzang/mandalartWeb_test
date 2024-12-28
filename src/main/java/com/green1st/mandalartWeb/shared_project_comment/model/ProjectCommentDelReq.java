package com.green1st.mandalartWeb.shared_project_comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class ProjectCommentDelReq {
    @Schema(description = "삭제할 댓글 PK")
    private long commentId;
    @Schema(description = "유저아이디")
    private String userId;

    public ProjectCommentDelReq(long commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;
    }
}
