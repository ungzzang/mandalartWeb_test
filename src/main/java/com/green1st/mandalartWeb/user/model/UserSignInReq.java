package com.green1st.mandalartWeb.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "로그인 요청")
public class UserSignInReq {
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "유효하지 않은 형식의 이메일입니다.")
    @Schema(title = "유저이메일")
    private String userId;


    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\\\\\|\\\\[{\\\\]};:'\\\",<.>/?]).{8,}$"
            , message = "비밀번호는 특수문자와 숫자를 포함한 8자 이상이어야 합니다.")
    @Schema(title = "유저비밀번호")
    private String upw;
}
