package com.green1st.mandalartWeb.project;

import com.green1st.mandalartWeb.common.model.ResultResponse;
import com.green1st.mandalartWeb.project.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name="프로젝트", description = "프로젝트 관련 API")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "프로젝트 등록", description = "프로젝트를 등록하는 API")
    public ResultResponse<?> postProject(@RequestBody ProjectPostReq p) {
        return projectService.postProject(p);
    }

    @GetMapping
    @Operation(summary = "프로젝트 조회", description = "프로젝트를 조회하는 API")
    public ResultResponse<?> getProject(@ParameterObject @Valid @ModelAttribute ProjectGetReq p) {
        return projectService.getProject(p);
    }

    @PatchMapping
    @Operation(summary = "프로젝트 수정", description = "프로젝트를 수정하는 API")
    public ResultResponse<?> postProject(@RequestPart MultipartFile pic, @RequestPart ProjectPatchReq p) {
        return projectService.patchProject(pic, p);
    }

    @DeleteMapping
    @Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제하는 API")
    public ResultResponse<?> postProject(@ParameterObject @ModelAttribute ProjectDelReq p) {
        return projectService.delProject(p);
    }
}
