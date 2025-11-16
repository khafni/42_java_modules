package _42.spring.service.application;

import _42.spring.service.ApplicationConfig;
import _42.spring.service.repositories.UsersRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(ApplicationConfig.class)) {

            // 1. JDBC impl
            UsersRepository jdbcRepo = ctx.getBean("usersRepositoryJdbc", UsersRepository.class);
            System.out.println("=== JDBC impl ===");
            System.out.println(jdbcRepo.findAll());

            // 2. JdbcTemplate impl
            UsersRepository templateRepo = ctx.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
            System.out.println("=== JdbcTemplate impl ===");
            System.out.println(templateRepo.findAll());
        }
    }
}
