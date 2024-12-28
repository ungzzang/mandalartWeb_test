package com.green1st.mandalartWeb.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(title = "유저 정보 GET 응답")
public class UserInfoGetRes {
    private String userId;
    private String nickName;
    private String pic;
}
