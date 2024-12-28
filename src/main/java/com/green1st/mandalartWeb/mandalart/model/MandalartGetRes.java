package com.green1st.mandalartWeb.mandalart.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Schema(title = "만다라트 조회 정보")
@Setter
@ToString
public class MandalartGetRes {
    @Schema(description = "만다라트 id")
    private long mandalartId;
    @Schema(description = "부모 id")
    private long parentId;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "만다라트 세부 내용")
    private String contents;
    @Schema(description = "완료 여부 (0: 미완료, 1: 완료)")
    private int completedFg;
    @Schema(description = "색상 코드")
    private String bgColor;
    @Schema(description = "단계 (0: 최상위, 1: 1단계, 2: 2단계)")
    private int depth;
    @Schema(description = "계획 시작일")
    private LocalDate startDate;
    @Schema(description = "계획 종료일")
    private LocalDate finishDate;
    @Schema(description = "순서 (0~7)")
    private int orderId;
}
