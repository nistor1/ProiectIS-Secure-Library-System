package launcher.componentFactory;

import database.DatabaseConnectionFactory;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.order.OrderCustomerRepository;
import repository.order.OrderCustomerRepositoryMySQL;
import repository.order.OrderEmployeeRepository;
import repository.order.OrderEmployeeRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.*;
import service.user.administrator.AdministratorService;
import service.user.administrator.AdministratorServiceMySQL;
import service.user.customer.CustomerService;
import service.user.customer.CustomerServiceMySQL;
import service.user.employee.EmployeeBookService;
import service.user.employee.EmployeeServiceMySQL;

import java.sql.Connection;

public class ComponentFactory {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository bookRepository;
    private final OrderCustomerRepository orderCustomerRepository;
    private final OrderEmployeeRepository orderEmployeeRepository;
    private final CustomerService customerService;
    private final EmployeeBookService employeeService;
    private final BookService bookService;
    private final AdministratorService administratorService;
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
        this.orderEmployeeRepository = new OrderEmployeeRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        this.customerService = new CustomerServiceMySQL(bookService, orderCustomerRepository);
        this.employeeService = new EmployeeServiceMySQL(bookService, orderEmployeeRepository);
        this.administratorService = new AdministratorServiceMySQL(userRepository,authenticationService);

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

    public EmployeeBookService getEmployeeService() {
        return employeeService;
    }

    public OrderCustomerRepository getOrderCustomerRepository() {
        return orderCustomerRepository;
    }

    public OrderEmployeeRepository getOrderEmployeeRepository() {
        return orderEmployeeRepository;
    }

    public AdministratorService getAdministratorService() {
        return administratorService;
    }
}
