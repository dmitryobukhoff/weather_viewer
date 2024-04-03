package integrations;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.dmitryobukhoff.exceptions.NotUniqueLoginException;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.repositories.UserRepository;
import ru.dmitryobukhoff.services.AuthenticationService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {
    private AuthenticationService authenticationService = new AuthenticationService();
    private UserRepository userRepository = new UserRepository();

    @Test
    @DisplayName("Проверка на существование пользователя в базе данных")
    public void notFoundUser(){
        assertThrows(NotUniqueLoginException.class, () -> {
            Optional<User> user = authenticationService.findUserByLogin("admin");
        });
    }



}
