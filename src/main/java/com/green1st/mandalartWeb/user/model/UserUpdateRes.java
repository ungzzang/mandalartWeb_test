package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

//삭제예정
@Getter
@Setter
public class UserUpdateRes {
    private String userId;
    private int result;
    private String upw;
    private String message;
}
