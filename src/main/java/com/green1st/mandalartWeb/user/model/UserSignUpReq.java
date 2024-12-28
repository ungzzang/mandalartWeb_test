package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@Schema(title = "회원가입")
public class UserSignUpReq {
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$", message = "유효하지 않은 형식의 이메일입니다.")
    @Schema(title = "유저이메일", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\\\\\|\\\\[{\\\\]};:'\\\",<.>/?]).{8,}$"
            , message = "비밀번호는 특수문자와 숫자를 포함한 8자 이상이어야 합니다.")
    @Schema(title = "유저비밀번호", example = "PassWord", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "닉네임은 3자 이상, 20자 이하의 알파벳, 숫자, 밑줄(_)만 가능합니다.")
    @Schema(title = "유저닉네임", example = "hide on bush", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickName;

    @Schema(title = "프로필사진")
    private String pic;



}
