package launcher.componentFactory;

import controller.EmployeeBookController;
import model.User;
import model.validator.Notification;
import view.EmployeeBookView;
import view.LoginView;

public class EmployeeBookComponentFactory {

    private final EmployeeBookView employeeView;
    private final EmployeeBookController employeeController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public EmployeeBookComponentFactory(ComponentFactory componentFactory, LoginView loginView, Notification<User> user) {
        this.employeeView = new EmployeeBookView(loginView.getPrimaryStage());
        this.user = user;
        this.employeeController = new EmployeeBookController(employeeView, componentFactory, user);
    }
}
