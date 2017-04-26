package info.palamarchuk.api.cooking;

import java.util.Arrays;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


/**
 * Main entry point for the application.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class App {

  /**
   * Application entry point.
   *
   * @param args command line arguments.
   */
  public static void main(String... args) {
    SpringApplication.run(App.class, args);
  }

  /** CORS and Swagger configuration. */
  /*
  @Configuration
  @EnableWebMvc
  protected static class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Value("${spring.cors.origins}")
    private String[] origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
      LOGGER.info("Enabling CORS for origins: {}", Arrays.toString(origins));
      registry.addMapping("/**").allowedOrigins(origins);
    }
  }
  */

  /*
  @Configuration
  @EnableSwagger2
  protected static class SwaggerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");

      registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket apiDocket() {
      return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(p -> p.startsWith("/cooking"))
          .build()
          .pathMapping("/")
          .apiInfo(apiInfo())
          .consumes(Sets.newHashSet(MediaType.APPLICATION_JSON_UTF8_VALUE))
          .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_UTF8_VALUE))
          .alternateTypeRules(
              newRule(typeResolver.resolve(DeferredResult.class,
                  typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                  typeResolver.resolve(WildcardType.class)))
          .useDefaultResponseMessages(false)
          .enableUrlTemplating(true);
    }

    private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
          .title("api-datasource-cooking")
          .description("API for working with the recipe")
          .contact(new Contact("Alexander Palamarchuk", "http://www.palamarchuk.info", "a@palamarchuk.info"))
          .version("1.0.0")
          .license("Commercial")
          .build();
    }
  }
  */
}
