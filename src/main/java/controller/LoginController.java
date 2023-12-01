package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.CustomerComponentFactory;
import launcher.componentFactory.EmployeeBookComponentFactory;
import model.Role;
import model.User;
import model.validator.Notification;
import view.LoginView;

import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;

public class LoginController {

    private final LoginView loginView;
    private final ComponentFactory componentFactory;


    public LoginController(LoginView loginView, ComponentFactory componentFactory) {
        this.loginView = loginView;
        this.componentFactory = componentFactory;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = componentFactory.getAuthenticationService().login(username, password);

            if (loginNotification.hasErrors()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Login Successfull!");
                for(Role r : loginNotification.getResult().getRoles()) {
                    if(r.getRole().equals(EMPLOYEE)) {
                        new EmployeeBookComponentFactory(componentFactory, loginView.getPrimaryStage(), loginNotification);
                    } else if(r.getRole().equals(CUSTOMER)) {
                        new CustomerComponentFactory(componentFactory, loginView.getPrimaryStage(), loginNotification);
                    }
                }
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = componentFactory.getAuthenticationService().register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}
