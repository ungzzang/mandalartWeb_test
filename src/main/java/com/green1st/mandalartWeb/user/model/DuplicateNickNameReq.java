package com.green1st.mandalartWeb.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "닉네임 중복체크")
public class DuplicateNickNameReq {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "닉네임은 3자 이상, 20자 이하의 알파벳, 숫자, 밑줄(_)만 가능합니다.")
    @Schema(title = "유저 닉네임")
    private String nickName;
}
