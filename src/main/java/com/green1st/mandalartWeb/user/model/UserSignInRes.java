package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "로그인 응답")
public class UserSignInRes {
    private String userId;
    private String nickName;
    private String pic;
    @JsonIgnore
    private String upw;
    @JsonIgnore
    private String message;
}
