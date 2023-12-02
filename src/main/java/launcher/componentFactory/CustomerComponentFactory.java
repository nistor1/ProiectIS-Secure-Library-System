package launcher.componentFactory;

import controller.customer.CustomerController;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import view.customer.CustomerView;

public class CustomerComponentFactory {
    private final CustomerView customerView;
    private final CustomerController customerController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public CustomerComponentFactory(ComponentFactory componentFactory, Stage stage, Notification<User> user) {
        this.customerView = new CustomerView(stage);
        this.user = user;
        this.customerController = new CustomerController(customerView, componentFactory, user);
    }
}
