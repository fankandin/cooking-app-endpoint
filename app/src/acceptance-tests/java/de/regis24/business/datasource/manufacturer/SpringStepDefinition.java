package de.regis24.business.datasource.manufacturer;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = ManufacturerApp.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "valid_product_types=1,2,5",
    "default_tax_class_id=2",
    "spring.cors.origins=http://localhost:3000"
})
public abstract class SpringStepDefinition {

  @LocalServerPort
  protected int port;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected ManufacturerDbFixturesHelper dbFixture;

  /**
   * Gets API url base.
   */
  protected String getApiUrlBase() {
    return String.format("http://localhost:%d/manufacturers", port);
  }

  protected OkHttpClient client() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    return new OkHttpClient.Builder()
        .addInterceptor(logging)
        .build();
  }


}
