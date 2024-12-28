package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateNickNameRes {
    @Schema(title = "중복체크", description = "사용가능: 1, 사용불가능: 0")
    private int check;
    @Schema(title = "중복체크메세지", description = "사용가능 메세지")
    private String message;

    private String nickName;
}
