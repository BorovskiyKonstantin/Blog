package main.domain.user.usecase;

import main.domain.user.entity.User;
import main.domain.user.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUseCase {
    @Autowired
    UserRepositoryPort userRepositoryPort;

    public Optional<User> getUserByUsername(String username) {
        return userRepositoryPort.findUserByName(username);
    }

    public Optional<User> getUserById(int id){
        return userRepositoryPort.findById(id);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepositoryPort.findUserByEmail(email);
    }
}
