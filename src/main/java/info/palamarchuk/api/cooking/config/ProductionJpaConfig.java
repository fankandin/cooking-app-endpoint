package info.palamarchuk.api.cooking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

//@Configuration
//@Profile("production")
//@EnableJpaRepositories
//@EnableTransactionManagement
public class ProductionJpaConfig extends JpaConfig {

    @Value("${hibernate.hbm2ddl.auto}")
    private String generationStrategy;

    @Override
    protected Properties additionalProperties() {
        Properties properties = super.additionalProperties();
        properties.setProperty("hibernate.hbm2ddl.auto", generationStrategy);
        return properties;
    }
}
