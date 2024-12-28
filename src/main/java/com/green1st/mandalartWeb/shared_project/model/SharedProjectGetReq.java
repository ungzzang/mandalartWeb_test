package com.green1st.mandalartWeb.shared_project.model;

import com.green1st.mandalartWeb.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
@Schema(description = "공유 프로젝트 리스트 조회 모델")
public class SharedProjectGetReq extends Paging {
    @Schema(description = "검색 필터(1:제목 2:내용 3: 제목 + 내용 4: 닉네임)", example = "1")
    private Integer searchFilter;
    @Schema(description = "검색 필터(0:최신순 1:좋아요 순 2: 댓글순)", example = "0")
    private Integer orderFilter;
    @Schema(description = "검색 단어", example = "찾는 단어")
    private String searchText;
    @Schema(description = "로그인한 사용자 아이디", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @ConstructorProperties({"page", "size", "searchFilter", "orderFilter", "searchText", "userId"})
    public SharedProjectGetReq(Integer page, Integer size
            , Integer searchFilter
            , Integer orderFilter
            , String searchText
            , String userId) {
        super(page, size);
        this.searchFilter = searchFilter == null ? 0 : searchFilter;
        this.orderFilter = orderFilter == null ? 0 : orderFilter;
        this.searchText = searchText == null ? "" : searchText;
        this.userId = userId;
    }
}
