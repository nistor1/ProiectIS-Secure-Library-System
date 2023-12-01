
package launcher.componentFactory;

import controller.EmployeeBookController;
import controller.EmployeeOrderController;
import model.User;
import model.validator.Notification;
import view.EmployeeBookView;
import view.EmployeeOrderView;
import view.LoginView;

public class EmployeeOrderComponentFactory {

    private final EmployeeOrderView employeeView;
    private final EmployeeOrderController employeeController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public EmployeeOrderComponentFactory(ComponentFactory componentFactory, EmployeeBookView employeeBookView, Notification<User> user) {
        this.employeeView = new EmployeeOrderView(employeeBookView.getPrimaryStage());
        this.user = user;
        this.employeeController = new EmployeeOrderController(employeeView, componentFactory, user);
    }
}
