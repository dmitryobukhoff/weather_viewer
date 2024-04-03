package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.repositories.UserRepository;
import ru.dmitryobukhoff.validators.EntitiesValidator;

import java.util.Optional;

public class RegistrationService {
    private final UserRepository userRepository = new UserRepository();
    private final EntitiesValidator entitiesValidator = new EntitiesValidator();
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }
    public void createUser(User user){
        userRepository.create(user);
    }
    public boolean isLoginValid(String login){
       return entitiesValidator.isLoginValid(login);
    }

}
