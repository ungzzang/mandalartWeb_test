package com.green1st.mandalartWeb.mandalart.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(title = "")
public class MandalartPostReq {
    @Schema(description = "프로젝트 id")
    private long projectId;
    private Long mandalartId;  // 만다라트 ID
    private String title;  // 제목
    private String contents;  // 내용
    private Integer depth;  // 깊이
    private Integer orderId;  // 순서
    private Boolean completedFg;  // 완료 여부
    private LocalDate startDate;  // 시작일
    private LocalDate finishDate;  // 종료일
    private Long parentId;  // 부모 ID
    private String colorCode;  // 색상 코드 추가


/*
    ex) lv2의 7번 칸 - lv2 7칸 - lv 8칸
    -> depth , order_id 로 따로 작성 xxx
    프론트에서 데이터를 해당 칸에 직접 입력함
    만다라트 시작 날짜 > 프로젝트 시작일 - 상위 부모의 시작일보다 더 빨리 시작 x
    만다라트 종료 날짜 > 프로젝트 종료일 - 상위 부모의 종료일보다 더 늦게 종료 x

     */
}
