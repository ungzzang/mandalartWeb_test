package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

//삭제예정
@Getter
@Setter
public class UserDeleteRes {
    private String message;
    private int check;

    private String userId;
    @JsonIgnore
    private String upw;
}
