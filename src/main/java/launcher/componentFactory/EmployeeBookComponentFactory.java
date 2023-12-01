package launcher.componentFactory;

import controller.EmployeeBookController;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import view.EmployeeBookView;
import view.LoginView;

public class EmployeeBookComponentFactory {

    private final EmployeeBookView employeeView;
    private final EmployeeBookController employeeController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public EmployeeBookComponentFactory(ComponentFactory componentFactory, Stage stage, Notification<User> user) {
        this.employeeView = new EmployeeBookView(stage);
        this.user = user;
        this.employeeController = new EmployeeBookController(employeeView, componentFactory, user);
    }
}
