package service.user.administrator;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface AdministratorService {
    Notification<Boolean> addUser(String username, String password, String role);
    boolean deleteUserById(Long id);
    Notification<Boolean> updateUserUsername(Long id, String username);
    Notification<Boolean> updateUserPassword(Long id, String password);
    List<User> findAll();
    boolean existsByUsername(String username);
    Notification<User> findUser(Long id);
    void removeAll();


}
