package _42.spring.service.application;

import _42.spring.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");

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