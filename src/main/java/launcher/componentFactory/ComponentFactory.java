package launcher.componentFactory;

import controller.CustomerController;
import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.order.OrderCustomerRepository;
import repository.order.OrderCustomerRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.*;
import view.CustomerView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository bookRepository;
    private final OrderCustomerRepository orderCustomerRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final BookService bookService;
    private static ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentsForTests) {
        instance = new ComponentFactory(componentsForTests);

        return instance;
    }

    public ComponentFactory(Boolean componentsForTests) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.orderCustomerRepository = new OrderCustomerRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        this.customerService = new CustomerServiceMySQL(bookService, orderCustomerRepository);
        this.employeeService = new EmployeeServiceMySQL(bookService);

    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
}
