package com.green1st.mandalartWeb.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
        info = @Info(
                title = "Mandalart",
                description = "만다라트 웹 솔루션",
                version = "v1"
        )
)
public class SwaggerConfiguration {
}
