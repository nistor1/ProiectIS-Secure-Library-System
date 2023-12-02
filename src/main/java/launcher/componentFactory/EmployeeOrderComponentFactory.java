
package launcher.componentFactory;

import controller.employee.EmployeeOrderController;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import view.employee.EmployeeOrderView;

public class EmployeeOrderComponentFactory {

    private final EmployeeOrderView employeeView;
    private final EmployeeOrderController employeeController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public EmployeeOrderComponentFactory(ComponentFactory componentFactory, Stage stage, Notification<User> user) {
        this.employeeView = new EmployeeOrderView(stage);
        this.user = user;
        this.employeeController = new EmployeeOrderController(employeeView, componentFactory, user);
    }
}
