package _42.services;

import fr._42.exceptions.AlreadyAuthenticatedException;
import fr._42.models.User;
import fr._42.repositories.UsersRepository;
import fr._42.services.UsersServiceImpl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

    final User CORRECT_USER = new User("bob11", "godSaveAmerica001", false);
    @Test
    void isAuthenticateMethodCorrectForValidCredentials() throws AlreadyAuthenticatedException {
        UsersRepository mockRepository = mock(UsersRepository.class);
        UsersServiceImpl usersService = new UsersServiceImpl(mockRepository);
        when(mockRepository.findByLogin(CORRECT_USER.getLogin())).thenReturn(CORRECT_USER);
        assertDoesNotThrow(() -> {
            assertTrue(usersService.authenticate(CORRECT_USER.getLogin(), CORRECT_USER.getPassword()));
        });
    }

    @Test
    void isAuthenticateMethodCorrectForIncorrectLogin() {
        UsersRepository mockRepository = mock(UsersRepository.class);
        UsersServiceImpl usersService = new UsersServiceImpl(mockRepository);
        when(mockRepository.findByLogin("user1")).thenReturn(null);
        assertFalse(usersService.authenticate(CORRECT_USER.getLogin(), CORRECT_USER.getPassword()));
    }

    @Test
    void isAuthenticateMethodCorrectForIncorrectPassword() {
        UsersRepository mockRepository = mock(UsersRepository.class);
        UsersServiceImpl usersService = new UsersServiceImpl(mockRepository);
        when(mockRepository.findByLogin(CORRECT_USER.getLogin())).thenReturn(CORRECT_USER);
        assertFalse(usersService.authenticate(CORRECT_USER.getLogin(), "wrong_password"));
    }

}
