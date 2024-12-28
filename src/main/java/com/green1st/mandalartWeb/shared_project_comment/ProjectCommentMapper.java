package com.green1st.mandalartWeb.shared_project_comment;

import com.green1st.mandalartWeb.shared_project_comment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectCommentMapper {
    void postSharedProjectComment(ProjectCommentPostReq p);
    List<ProjectCommentDto> getSharedProjectComment(ProjectCommentGetReq p);
    int updateSharedProjectComment(ProjectCommentPatchReq p);
    int deleteSharedProjectComment(ProjectCommentDelReq p);
}
