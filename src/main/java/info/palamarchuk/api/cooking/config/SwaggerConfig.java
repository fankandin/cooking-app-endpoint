package info.palamarchuk.api.cooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration.
 * Swagger UI: http://localhost:[port]/swagger-ui.html
 * Swagger 2.0 API documentation: http://localhost:[port]/v2/api-docs
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/.*"))
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Cooking API")
            .description("RESTful backend for working with private cooking recipes")
            .description("")
            .version("1.0.1")
            .contact(new Contact("Alexander Palamarchuk", "http://www.palamarchuk.info", "a@palamarchuk.info"))
            //.termsOfServiceUrl("http://terms-of-services.url")
            //.license("LICENSE")
            //.licenseUrl("http://url-to-license.com")
            .build();
    }
}
