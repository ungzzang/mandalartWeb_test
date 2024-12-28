package com.green1st.mandalartWeb.shared_project;

import com.green1st.mandalartWeb.shared_project.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SharedProjectMapper {
    int insSharedProject(SharedProjectPostReq p);
    List<SharedProjectGetRes> selSharedProjectList(SharedProjectGetReq p);
    int updSharedProject(SharedProjectPatchReq p);
    int delSharedProject(SharedProjectDelReq p);
}
