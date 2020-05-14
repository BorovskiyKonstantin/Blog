package main.dao.user;

import main.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByName(String username);

    Optional<User> findUserByEmail(String email);
}
