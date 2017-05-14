package info.palamarchuk.api.cooking.config;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataConfig {

    DataSource dataSource();
}
