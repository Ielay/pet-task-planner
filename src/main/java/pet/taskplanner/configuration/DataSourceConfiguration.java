package pet.taskplanner.configuration;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lelay
 * @since 01.02.2021
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource hikariDataSource(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
        throw new NotImplementedException();
    }

    @Bean("postgresDataSource")
    public DataSource postgresDataSource() {
        throw new NotImplementedException();
    }
}
