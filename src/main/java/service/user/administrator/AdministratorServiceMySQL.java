package service.user.administrator;

import model.User;
import model.validator.Notification;
import repository.user.UserRepository;
import service.user.AuthenticationService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

public class AdministratorServiceMySQL implements AdministratorService{
    private UserRepository userRepository;
    private AuthenticationService authenticationService;
    public AdministratorServiceMySQL(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Notification<Boolean> addUser(String username, String password, String role) {
        return authenticationService.register(username, password, role);
    }

    @Override
    public boolean deleteUserById(Long id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public Notification<Boolean> updateUserUsername(Long id, String username) {
        Notification<Boolean> ret = new Notification<>();
        ret.setResult(userRepository.updateUserUsername(id, username));
        return ret;
    }

    @Override
    public Notification<Boolean> updateUserPassword(Long id, String password) {
        Notification<Boolean> ret = new Notification<>();
        ret.setResult(userRepository.updateUserUsername(id, hashPassword(password)));
        return ret;
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Notification<User> findUser(Long id) {
        Notification<User> userNotification = new Notification<>();
        Optional<User> user = userRepository.findUser(id);
        if(user.isEmpty()) {
            userNotification.addError("User not found!");
            return userNotification;
        }
        userNotification.setResult(user.get());
        return userNotification;
    }

    @Override
    public void removeAll() {
        userRepository.removeAll();
    }
    private String hashPassword(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
