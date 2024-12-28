package com.green1st.mandalartWeb.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
@Schema(title = "유저 삭제")
public class UserDeleteReq {
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$", message = "유효하지 않은 형식의 이메일입니다.")
    @Schema(description = "유저 이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\\\\\|\\\\[{\\\\]};:'\\\",<.>/?]).{8,}$"
            , message = "비밀번호는 특수문자와 숫자를 포함한 8자 이상이어야 합니다.")
    @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

    /*@Schema(description = "프로젝트 pk", requiredMode = Schema.RequiredMode.REQUIRED)
    private long projectId;*/

    /*@ConstructorProperties("project_id")
    public UserDeleteReq(long projectId) {
        this.projectId = projectId;
    }*/
}
