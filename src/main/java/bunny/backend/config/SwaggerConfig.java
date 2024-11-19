package bunny.backend.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "BUNNY API 명세서",
                description = "백엔드 API")
)
@Configuration
public class SwaggerConfig {
}
