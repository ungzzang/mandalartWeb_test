package com.green1st.mandalartWeb.shared_project_like;

import com.green1st.mandalartWeb.shared_project_like.model.ProjectLikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MandalartLikeMapper {
    int insSharedProjectLike(ProjectLikeDto p);
    int delSharedProjectLike(ProjectLikeDto p);
}
