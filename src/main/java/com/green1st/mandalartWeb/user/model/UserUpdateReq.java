package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(title = "유저정보수정 요청")
public class UserUpdateReq {
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "유효하지 않은 형식의 이메일입니다.")
    @Schema(title = "유저이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\\\\\|\\\\[{\\\\]};:'\\\",<.>/?]).{8,}$"
            , message = "비밀번호는 특수문자와 숫자를 포함한 8자 이상이어야 합니다.")
    @Schema(title = "기존비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

    @Schema(title = "신규비밀번호")
    private String newUpw;
    @Schema(title = "비밀번호확인")
    private String checkUpw;
    @Schema(title = "바꿀닉네임")
    private String nickName;
    @Schema(title = "프로필등록")
    private MultipartFile pic;

    @JsonIgnore
    private String picName;

}
