package launcher.componentFactory;

import controller.CustomerController;
import controller.LoginController;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.CustomerService;
import service.user.CustomerServiceMySQL;
import view.CustomerView;
import view.LoginView;

public class CustomerComponentFactory {
    private final CustomerView customerView;
    private final CustomerController customerController;
    private final Notification<User> user;
    private static CustomerComponentFactory instance;

    public CustomerComponentFactory(ComponentFactory componentFactory, LoginView loginView, Notification<User> user) {
        this.customerView = new CustomerView(loginView.getPrimaryStage());
        this.user = user;
        this.customerController = new CustomerController(customerView, componentFactory, user);
    }
}
