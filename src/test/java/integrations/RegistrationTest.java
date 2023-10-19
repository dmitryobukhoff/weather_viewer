package integrations;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class RegistrationTest {

    private UserRepository userRepository = new UserRepository();

    @Test
    @DisplayName("Проверка на добавление нового пользователя")
    public void addUser(){
        userRepository.create(new User("admin", "admin"));
        Optional<User> user = userRepository.findUserByLogin("admin");
        assertTrue(user.isPresent());
    }

    @Test
    @DisplayName("Пользователя в базе данных нет")
    public void notFoundUser(){
        Optional<User> user = userRepository.findUserByLogin("qwerty");
        assertTrue(user.isEmpty());
    }

    @Test
    @DisplayName("Проверка на то, что пользователь удаляется из базы данных")
    public void deleteUser(){
        User user = new User("admin", "admin");
        userRepository.create(user);
        userRepository.delete(user);
        Optional<User> userOptional = userRepository.findUserByLogin("admin");
        assertTrue(userOptional.isPresent());
    }

    @Test
    @DisplayName("Проверка на то, что данные о пользователе обновляются в базе данных")
    public void updateUser(){
        User user = new User("admin", "admin");
        userRepository.create(user);
        user.setLogin("qwerty");
        userRepository.update(user);
        Optional<User> userOptional = userRepository.findUserByLogin("qwerty");
        assertTrue(userOptional.isPresent());
    }


}
