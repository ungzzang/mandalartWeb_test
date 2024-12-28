package com.green1st.mandalartWeb.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@Schema(title = "유저 정보 GET 요청")
@ToString
public class UserInfoGetReq {
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "유효하지 않은 형식의 이메일입니다.")
    @Schema(title = "로그인한 유저 이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    public UserInfoGetReq(String userId) {
        this.userId = userId;
    }
}
