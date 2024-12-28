package com.green1st.mandalartWeb.project;

import com.green1st.mandalartWeb.project.model.*;
import com.green1st.mandalartWeb.shared_project.model.ProjectSelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    int insProject(ProjectPostReq p);
    int insMandalart(MandalartInsDto p);
    List<ProjectGetRes> selProjectList(ProjectGetReq p);
    ProjectResultDto selProjectByUserIdAndProjectId(ProjectSelDto p);
    int updProject(ProjectPatchReq p);
    int delProject(ProjectDelReq p);
    int delMandalart(long ProjectId);
}
