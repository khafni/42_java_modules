package _42.spring.service;

import _42.spring.service.ApplicationConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = "_42.spring.service",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ApplicationConfig.class)
)
public class TestApplicationConfig {
    private static final String H2_URL = "jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    @Bean
    public DataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(H2_URL);
        dataSource.setUsername(H2_USER);
        dataSource.setPassword(H2_PASSWORD);
        initializeDatabase(dataSource);
        return dataSource;
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(H2_URL);
        config.setUsername(H2_USER);
        config.setPassword(H2_PASSWORD);
        config.setDriverClassName("org.h2.Driver");
        HikariDataSource dataSource = new HikariDataSource(config);
        initializeDatabase(dataSource);
        return dataSource;
    }

    private void initializeDatabase(DataSource dataSource) {
        ResourceDatabasePopulator populator =
                new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
