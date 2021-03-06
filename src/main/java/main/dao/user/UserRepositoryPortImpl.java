package main.dao.user;

import main.domain.user.entity.User;
import main.domain.user.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryPortImpl implements UserRepositoryPort {
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryPortImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional <User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional <User> findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Override
    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByCode(String code) {
        return userRepository.findUserByCode(code);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}
