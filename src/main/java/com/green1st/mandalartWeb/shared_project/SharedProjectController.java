package com.green1st.mandalartWeb.shared_project;

import com.green1st.mandalartWeb.common.model.ResultResponse;
import com.green1st.mandalartWeb.project.model.ProjectGetReq;
import com.green1st.mandalartWeb.project.model.ProjectPostReq;
import com.green1st.mandalartWeb.shared_project.model.SharedProjectDelReq;
import com.green1st.mandalartWeb.shared_project.model.SharedProjectGetReq;
import com.green1st.mandalartWeb.shared_project.model.SharedProjectPatchReq;
import com.green1st.mandalartWeb.shared_project.model.SharedProjectPostReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shared_project")
@Tag(name="공유 프로젝트", description = "공유 프로젝트 관련 API")
public class SharedProjectController {
    private final SharedProjectService sharedProjectService;

    @PostMapping
    @Operation(summary = "공유 프로젝트 등록", description = "공유 프로젝트를 등록하는 API")
    public ResultResponse<?> postProject(@RequestBody SharedProjectPostReq p) {
        return sharedProjectService.postSharedProject(p);
    }

    @GetMapping
    @Operation(summary = "공유 프로젝트 조회", description = "공유 프로젝트를 조회하는 API")
    public ResultResponse<?> getProject(@ParameterObject @ModelAttribute SharedProjectGetReq p) {
        return sharedProjectService.getSaherdProject(p);
    }

    @PatchMapping
    @Operation(summary = "공유 프로젝트 등록", description = "공유 프로젝트를 등록하는 API")
    public ResultResponse<?> pathProject(@RequestBody SharedProjectPatchReq p) {
        return sharedProjectService.patchSharedProject(p);
    }

    @DeleteMapping
    @Operation(summary = "공유 프로젝트 등록", description = "공유 프로젝트를 등록하는 API")
    public ResultResponse<?> delProject(@ModelAttribute SharedProjectDelReq p) {
        return sharedProjectService.delSharedProject(p);
    }
}
