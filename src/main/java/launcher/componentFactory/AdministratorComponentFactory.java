package launcher.componentFactory;

import controller.administrator.AdministratorController;
import controller.employee.EmployeeBookController;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import view.administrator.AdministratorView;
import view.employee.EmployeeBookView;

public class AdministratorComponentFactory {

    private final AdministratorView administratorView;
    private final AdministratorController administratorController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public AdministratorComponentFactory(ComponentFactory componentFactory, Stage stage, Notification<User> user) {
        this.administratorView = new AdministratorView(stage);
        this.user = user;
        this.administratorController = new AdministratorController(administratorView, componentFactory, user);
    }
}