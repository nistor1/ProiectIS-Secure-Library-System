package launcher.componentFactory;

import controller.CustomerController;
import controller.EmployeeController;
import model.User;
import model.validator.Notification;
import view.CustomerView;
import view.EmployeeView;
import view.LoginView;

public class EmployeeComponentFactory {

    private final EmployeeView employeeView;
    private final EmployeeController employeeController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public EmployeeComponentFactory(ComponentFactory componentFactory, LoginView loginView, Notification<User> user) {
        this.employeeView = new EmployeeView(loginView.getPrimaryStage());
        this.user = user;
        this.employeeController = new EmployeeController(employeeView, componentFactory, user);
    }
}
