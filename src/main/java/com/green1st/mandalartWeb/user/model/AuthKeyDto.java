package com.green1st.mandalartWeb.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
public class AuthKeyDto {
    private String email;
    private String authKey;
    private long expiryTime;
}
