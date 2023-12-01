package service.user;

import database.Constants;
import model.Role;
import model.User;
import model.validator.Notification;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);
    public Notification<Boolean> register(String username, String password, String role);
    Notification<User> login(String username, String password);

    boolean logout(User user);
}
