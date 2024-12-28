package com.green1st.mandalartWeb.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TempPasswordDto {
    @Schema(description = "유저이메일", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @JsonIgnore
    private String tmpPassword;
}
