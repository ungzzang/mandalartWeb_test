package com.green1st.mandalartWeb.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerification {
    private String email;
    private String authKey;
    private long expiryTime;
}
