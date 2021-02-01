package pet.taskplanner.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static pet.taskplanner.dao.DAOTest.DAOTestConfiguration.*;

/**
 * @author lelay
 * @since 01.02.2021
 */
@ContextConfiguration(classes = {
        DAOTest.DAOTestConfiguration.class
})
@ExtendWith(SpringExtension.class)
@Testcontainers
public abstract class DAOTest {

    @Autowired
    protected TestDataHelper helper;

    @Container
    protected static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:10.15-alpine"))
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD);

    @BeforeEach
    public void initDatabaseData() throws SQLException {
        helper.insertAll();
    }

    @AfterEach
    public void dropDatabaseData() throws SQLException {
        helper.dropAll();
    }

    @Configuration
    static class DAOTestConfiguration {

        protected static final String DB_NAME = "test_db";
        protected static final String DB_USERNAME = "test_user";
        protected static final String DB_PASSWORD = "test_pass";

        @Bean
        public TestDataHelper testDataHelper(@Qualifier("hikariDataSource") DataSource dataSource) {
            return new TestDataHelper(dataSource);
        }

        @Bean
        public UserDAOImpl userDAOImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
            return new UserDAOImpl(dataSource);
        }

        @Bean("hikariDataSource")
        public DataSource hikariDataSource(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
            HikariConfig config = new HikariConfig();
            config.setMinimumIdle(3);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(10_000L);
            config.setDataSource(postgresDataSource);

            return new HikariDataSource(config);
        }

        @Bean("postgresDataSource")
        public DataSource postgresDataSource() {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(postgres.getDriverClassName());
            ds.setUrl(postgres.getJdbcUrl());
            ds.setUsername(postgres.getUsername());
            ds.setPassword(postgres.getPassword());

            return ds;
        }

        @Bean
        public SpringLiquibase springLiquibase(@Qualifier("hikariDataSource") DataSource dataSource) throws SQLException {
            try (Connection conn = dataSource.getConnection();
                 Statement stat = conn.createStatement()) {
                stat.execute("CREATE SCHEMA pet_tables; ALTER USER " + DB_USERNAME + " SET search_path TO pet_tables,public;");
            }

            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:liquibase/changelog-master.xml");
            liquibase.setLiquibaseSchema("pet_tables");
            liquibase.setDefaultSchema("pet_tables");

            return liquibase;
        }
    }
}
