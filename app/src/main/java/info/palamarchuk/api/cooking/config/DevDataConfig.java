package info.palamarchuk.api.cooking.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Profile("dev")
public class DevDataConfig implements DataConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            //.addScript("classpath:com/bank/config/sql/schema.sql")
            //.addScript("classpath:com/bank/config/sql/test-data.sql")
            .build();
    }

}
