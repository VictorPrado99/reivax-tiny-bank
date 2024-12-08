package cloud.reivax.tiny_bank.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        final String securitySchemeName = "bearerAuth";

        final SecurityScheme securitySchemeItem = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        final SecurityRequirement securityItem = new SecurityRequirement().addList(securitySchemeName);

        final Components components = new Components().addSecuritySchemes(securitySchemeName, securitySchemeItem);

        final Info version = new Info().title("Tiny Bank API").version("v1");

        return new OpenAPI()
                .addSecurityItem(securityItem)
                .components(components)
                .info(version);
    }

}
