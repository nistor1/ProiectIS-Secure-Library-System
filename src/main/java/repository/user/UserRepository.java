package repository.user;

import model.User;
import model.validator.Notification;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);

    boolean deleteUserById(Long id);
    boolean updateUserUsername(Long id, String username);
    boolean updateUserPassword(Long id, String password);
    Optional<User> findUser(Long id);
}
