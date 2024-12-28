package com.green1st.mandalartWeb.mandalart.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(title = "")
public class MandalartPostRes {
    @Schema(title = "mandalart_id")
    private long mandalartId;
    @Schema(description = "부모 id")
    private long parentId;
    @Schema(description = "실천 목표")
    private String title;
    @Schema(description = "계획 목표")
    private String contents;
    @Schema(description = "계획 시작일")
    private LocalDate startDate;
    @Schema(description = "계획 종료일")
    private LocalDate finishDate;
    @Schema(description = "완료 여부 0:미완료 , 1:완료")
    private int completedFg;
    @Schema(description = "0:최상위 부모 1:1단계 2:2단계")
    private int depth;
    @Schema(description = "각 단계별 0~7칸 , 선택 칸 -> 데이터 입력 ")
    private int orderId;
    @Schema(description = "색상 코드")
    private String bgColor;


    public MandalartPostRes(MandalartPostReq request) {
        this.mandalartId = request.getMandalartId();
        this.parentId = request.getParentId();
        this.title = request.getTitle();
        this.contents = request.getContents();
        this.startDate = request.getStartDate();
        this.finishDate = request.getFinishDate();
        this.completedFg = request.getCompletedFg() != null ? (request.getCompletedFg() ? 1 : 0) : 0;
        this.depth = request.getDepth();
        this.orderId = request.getOrderId();
        this.bgColor = request.getColorCode() != null ? request.getColorCode() : "#FFFFFF";  // 기본 흰색 설정
    }
}
