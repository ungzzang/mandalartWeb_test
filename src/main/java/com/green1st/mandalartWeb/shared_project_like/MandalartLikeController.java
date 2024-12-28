package com.green1st.mandalartWeb.shared_project_like;

import com.green1st.mandalartWeb.common.model.ResultResponse;
import com.green1st.mandalartWeb.shared_project_like.model.ProjectLikeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shared_project")
@Tag(name = "프로젝트 좋아요", description = "프로젝트 좋아요 관리")
public class MandalartLikeController {
    private final MandalartLikeService mandalartLikeService;

    @PostMapping("/like")
    @Operation(summary = "프로젝트 좋아요 등록")
    public ResultResponse<Integer> sharedProjectLike(@RequestBody ProjectLikeDto dto) {
        log.info("MandalartLikeController > sharedProjectLike > dto: {}", dto);
        try {
            int result = mandalartLikeService.insMandalratLike(dto);
            return ResultResponse.<Integer>builder()
                    .statusCode("200")
                    .resultMsg("프로젝트 좋아요 완료")
                    .resultData(result)
                    .build();
        } catch (DuplicateKeyException e) {
            log.error("MandalartLikeController > sharedProjectLike > error", e);
            return ResultResponse.<Integer>builder()
                    .statusCode("400")
                    .resultMsg("공유 프로젝트 좋아요 실패")
                    .resultData(0)
                    .build();
        }
    }

    @DeleteMapping("/like")
    @Operation(summary = "프로젝트 좋아요 취소")
    public ResultResponse<Integer> sharedProjectLikeDelete(@RequestBody ProjectLikeDto dto) {
        log.info("MandalartLikeController > sharedProjectLikeDelete > dto: {}", dto);
        try {
            int result = mandalartLikeService.delMandalratLike(dto);
            return ResultResponse.<Integer>builder()
                    .statusCode("200")
                    .resultMsg("프로젝트 좋아요 취소 완료")
                    .resultData(result)
                    .build();
        } catch (DuplicateKeyException e) {
            log.error("MandalartLikeController > sharedProjectLikeDelete > error", e);
            return ResultResponse.<Integer>builder()
                    .statusCode("400")
                    .resultMsg("공유 프로젝트 좋아요 취소 실패")
                    .resultData(0)
                    .build();
        }
    }
}
