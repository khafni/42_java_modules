package _42.spring.service.services;

import _42.spring.service.TestApplicationConfig;
import _42.spring.service.models.User;
import _42.spring.service.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsersServiceImplIntegrationTest {

    private AnnotationConfigApplicationContext context;
    private UsersService usersService;
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = context.getBean(UsersService.class);
        usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @Test
    void signUpReturnsTemporaryPassword() {
        String email = "integration@test.com";

        String temporaryPassword = usersService.signUp(email);

        assertNotNull(temporaryPassword, "Temporary password should not be null");
        assertFalse(temporaryPassword.isBlank(), "Temporary password should not be blank");

        List<User> users = usersRepository.findAll();
        assertEquals(1, users.size(), "User should be persisted");
        assertEquals(email, users.get(0).getEmail(), "Persisted user must match the email");
    }
}
