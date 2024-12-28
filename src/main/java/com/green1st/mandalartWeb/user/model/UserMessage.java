package com.green1st.mandalartWeb.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserMessage {
    @Schema(title = "메세지", description = "성공 or 실패에 관한 메세지")
    private String message;
}
