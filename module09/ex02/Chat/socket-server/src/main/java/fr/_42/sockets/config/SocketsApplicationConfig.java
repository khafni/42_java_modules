package fr._42.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr._42.sockets.repositories.ChatroomRepository;
import fr._42.sockets.repositories.ChatroomRepositoryImpl;
import fr._42.sockets.repositories.MessagesRepository;
import fr._42.sockets.repositories.MessagesRepositoryImpl;
import fr._42.sockets.repositories.UsersRepository;
import fr._42.sockets.repositories.UsersRepositoryImpl;
import fr._42.sockets.server.Server;
import fr._42.sockets.services.ChatroomService;
import fr._42.sockets.services.ChatroomServiceImpl;
import fr._42.sockets.services.MessagesService;
import fr._42.sockets.services.MessagesServiceImpl;
import fr._42.sockets.services.UsersService;
import fr._42.sockets.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("db.url"));
        config.setUsername(env.getProperty("db.username"));
        config.setPassword(env.getProperty("db.password"));
        config.setDriverClassName(env.getProperty("db.driver.class"));
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean
    public ChatroomRepository chatroomRepository(JdbcTemplate jdbcTemplate) {
        return new ChatroomRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public UsersRepository usersRepository(JdbcTemplate jdbcTemplate, ChatroomRepository chatroomRepository) {
        return new UsersRepositoryImpl(jdbcTemplate, chatroomRepository);
    }

    @Bean
    public MessagesRepository messagesRepository(
        JdbcTemplate jdbcTemplate,
        UsersRepository usersRepository,
        ChatroomRepository chatroomRepository
    ) {
        return new MessagesRepositoryImpl(jdbcTemplate, usersRepository, chatroomRepository);
    }

    @Bean
    public ChatroomService chatroomService(ChatroomRepository chatroomRepository) {
        return new ChatroomServiceImpl(chatroomRepository);
    }

    @Bean
    public UsersService usersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        return new UsersServiceImpl(usersRepository, passwordEncoder);
    }

    @Bean
    public MessagesService messagesService(MessagesRepository messagesRepository) {
        return new MessagesServiceImpl(messagesRepository);
    }

    @Bean
    public Server server(
        UsersService usersService,
        MessagesService messagesService,
        ChatroomService chatroomService
    ) {
        return new Server(usersService, messagesService, chatroomService);
    }
}
