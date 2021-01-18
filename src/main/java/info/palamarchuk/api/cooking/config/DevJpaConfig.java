package info.palamarchuk.api.cooking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

//@Configuration
//@Profile("dev")
//@EnableJpaRepositories
//@EnableTransactionManagement
public class DevJpaConfig extends JpaConfig {

    @Override
    protected Properties additionalProperties() {
        Properties properties = super.additionalProperties();
        properties.setProperty("spring.jpa.show-sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        return properties;
    }
}
