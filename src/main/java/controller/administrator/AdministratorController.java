package controller.administrator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.EmployeeOrderComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.Order;
import model.User;
import model.builder.BookBuilder;
import model.builder.UserBuilder;
import model.validator.Notification;
import view.administrator.AdministratorView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.ROLES;
import static view.employee.PDFReportGenerator.generatePDFReport;

public class AdministratorController {
    private final AdministratorView administratorView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public AdministratorController(AdministratorView administratorView, ComponentFactory componentFactory, Notification<User> user) {
        this.administratorView = administratorView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<User> users = componentFactory.getAdministratorService().findAll();

        administratorView.setListOfUsers(users);

        this.administratorView.addAddUserButtonListener(new controller.administrator.AdministratorController.AddUserButtonListener());
        this.administratorView.addFindAllButtonListener(new controller.administrator.AdministratorController.FindAllButtonListener());
        this.administratorView.addLogoutButtonListener(new controller.administrator.AdministratorController.Logout());
        this.administratorView.addDeleteUserButtonListener(new controller.administrator.AdministratorController.DeleteUserButtonListener());
        this.administratorView.addFindUserButtonListener(new controller.administrator.AdministratorController.FindUserButtonListener());
        this.administratorView.addUpdatePasswordButtonListener(new controller.administrator.AdministratorController.PasswordButtonListener());
        this.administratorView.addUpdateUsernameButtonListener(new controller.administrator.AdministratorController.UsernameButtonListener());
        this.administratorView.addGetReportButtonListener(new controller.administrator.AdministratorController.GetReportButtonListener());

    }

    private class AddUserButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {

            String username = administratorView.getUsernameTextInput();
            int error = 0;
            if (username.length() > 500 || username.length() < 1) {
                new IllegalAccessException("Username is empty or has too many characters!");
                administratorView.setActionTargetText("Username is empty or has too many characters!");
                error++;
            }
            String password = administratorView.getPasswordTextFieldInput();
            if (password.length() > 500 || password.length() < 1) {
                new IllegalAccessException("Password is empty or has too many characters!");
                administratorView.setActionTargetText("Password is empty or has too many characters!");
                error++;
            }
            Long role = administratorView.getRoleBox();
            if (role == null) {
                new IllegalAccessException("Invalid role input!");
                administratorView.setActionTargetText("Invalid role input!");
                error++;
            }
            if (error > 0) {
                return;
            }

            System.out.println("ADAUGA UTILIZATOR!");
            User user = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();

            Notification<Boolean> userNotification = componentFactory.getAdministratorService().addUser(username, password, ROLES[((role.intValue()))]);
            List<User> users = componentFactory.getAdministratorService().findAll();

            administratorView.setListOfUsers(users);
            if (userNotification.hasErrors()) {
                administratorView.setActionTargetText(userNotification.getFormattedErrors());
            } else {
                administratorView.setActionTargetTextToNull();
            }
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI UTOILIZATORII!");
            List<User> users = componentFactory.getAdministratorService().findAll();

            administratorView.setListOfUsers(users);
            administratorView.setActionTargetTextToNull();
        }
    }

    private class Logout implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("LOGOUT");
            user.setResult(null);
            LoginComponentFactory loginComponentFactory = new LoginComponentFactory(componentFactory, administratorView.getPrimaryStage());

        }
    }

    private class DeleteUserButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("STERGE UTILIZATOR!");

            if (administratorView.userSelected() == null) {
                administratorView.setActionTargetText("Choose a user!");
            } else {
                System.out.println("STERGE UTILIZATOR!");
                User user = administratorView.userSelected();

                if (!componentFactory.getAdministratorService().deleteUserById(user.getId())) {
                    administratorView.setActionTargetText("Something went wrong in deleting the user!");
                } else {
                    administratorView.setActionTargetTextToNull();
                }

                List<User> users = componentFactory.getAdministratorService().findAll();

                administratorView.setListOfUsers(users);
                administratorView.setActionTargetTextToNull();
            }
        }
    }

    private class FindUserButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            if (administratorView.userSelected() == null) {
                administratorView.setActionTargetText("Choose a user!");
            } else {
                System.out.println("VEZI UTILIZATOR!");

                List<User> users = new ArrayList<>();
                User user = administratorView.userSelected();
                Notification<User> userNotification = componentFactory.getAdministratorService().findUser(user.getId());
                if (userNotification.hasErrors()) {
                    administratorView.setActionTargetText(userNotification.getFormattedErrors());
                    return;
                }

                users.add(userNotification.getResult());

                administratorView.setListOfUsers(users);

                administratorView.setActionTargetTextToNull();

            }
        }
    }

    private class PasswordButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            System.out.println("ACTUALIZEAZA USERNAME!");

            String password = administratorView.getPasswordTextFieldInput();
            int error = 0;
            if (password.length() > 500 || password.length() < 1) {
                new IllegalAccessException("Password is empty or has too many characters!");
                administratorView.setActionTargetText("Password is empty or has too many characters!");
                error++;
            }
            String username = administratorView.getUsernameTextInput();
            if (username.length() > 0) {
                new IllegalAccessException("Space for username should be empty!");
                administratorView.setActionTargetText("Space for username should be empty!");

                error++;
            }
            if (administratorView.userSelected() == null) {
                administratorView.setActionTargetText("Choose a user!");
            } else {
                System.out.println("ACTUALIZEAZA Nume!");
                Notification<User> userNotification = componentFactory.getAdministratorService().findUser(administratorView.userSelected().getId());
                if (userNotification.hasErrors()) {
                    administratorView.setActionTargetText(userNotification.getFormattedErrors());
                    return;
                }

                User user = userNotification.getResult();
                user.setPassword(password);
                Notification<Boolean> updateNotification = componentFactory.getAdministratorService().updateUserPassword(user.getId(), user.getPassword());
                if (updateNotification.hasErrors()) {
                    administratorView.setActionTargetText(updateNotification.getFormattedErrors());
                    administratorView.setActionTargetText("Something went wrong in DataBase!");
                    return;
                } else {
                    administratorView.setActionTargetTextToNull();
                }
                List<User> users = componentFactory.getAdministratorService().findAll();

                administratorView.setListOfUsers(users);
                administratorView.setActionTargetTextToNull();
            }
        }
    }

        private class UsernameButtonListener implements EventHandler<ActionEvent> {

            @Override
            public void handle(ActionEvent event) {

                String username = administratorView.getUsernameTextInput();
                int error = 0;
                if (username.length() > 500 || username.length() < 1) {
                    new IllegalAccessException("Username is empty or has too many characters!");
                    administratorView.setActionTargetText("Username is empty or has too many characters!");
                    error++;
                }
                String password = administratorView.getPasswordTextFieldInput();
                if (password.length() > 0) {
                    new IllegalAccessException("Space for password should be empty!");
                    administratorView.setActionTargetText("Space for password should be empty!");

                    error++;
                }
                if (administratorView.userSelected() == null) {
                    administratorView.setActionTargetText("Choose a user!");
                } else {
                    System.out.println("ACTUALIZEAZA USERNAME!");
                    Notification<User> userNotification = componentFactory.getAdministratorService().findUser(administratorView.userSelected().getId());
                    if (userNotification.hasErrors()) {
                        administratorView.setActionTargetText(userNotification.getFormattedErrors());
                        return;
                    }

                    User user = userNotification.getResult();
                    user.setUsername(username);
                    Notification<Boolean> updateNotification = componentFactory.getAdministratorService().updateUserUsername(user.getId(), user.getUsername());
                    if (updateNotification.hasErrors()) {
                        administratorView.setActionTargetText(updateNotification.getFormattedErrors());
                        administratorView.setActionTargetText("Something went wrong in DataBase!");
                        return;
                    } else {
                        administratorView.setActionTargetTextToNull();
                    }
                    List<User> users = componentFactory.getAdministratorService().findAll();

                    administratorView.setListOfUsers(users);
                    administratorView.setActionTargetTextToNull();
                }
            }
        }

        private class GetReportButtonListener implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("VEZI RAPORT!");
                List<Order> orders = componentFactory.getEmployeeService().viewAllOrders();
                generatePDFReport(orders,false);
                administratorView.setActionTargetText("Report generated");
            }
        }
}