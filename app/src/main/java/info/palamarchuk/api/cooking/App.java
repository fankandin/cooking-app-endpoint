package info.palamarchuk.api.cooking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


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

    @Configuration
    protected static class WebConfig extends WebMvcConfigurerAdapter {
        @Value("${spring.cors.origins}")
        private String[] origins;

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true).maxAge(3600);
        }
    }
}
