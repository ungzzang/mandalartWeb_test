package com.green1st.mandalartWeb.shared_project_comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectCommentPatchReq {
    private long commentId;
    private String userId;
    private String content;
}
