package database;

import model.Book;
import model.Order;
import model.Role;
import model.User;
import model.builder.BookBuilder;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.order.OrderEmployeeRepository;
import repository.order.OrderEmployeeRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.administrator.AdministratorService;
import service.user.administrator.AdministratorServiceMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.*;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

// Script - code that automates some steps or processes

public class Bootstrap {

    private static RightsRolesRepository rightsRolesRepository;

    public static void main(String[] args) throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();

        bootstrapBooks();

        //testOrderEmployeeRepository();
        testAdministratorService();
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`;",
                    "DROP TABLE `role_right`;",
                    "TRUNCATE `right`;",
                    "DROP TABLE `right`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE `user_role`;",
                    "TRUNCATE `order`;",
                    "DROP TABLE  `order`;",
                    "TRUNCATE `role`;",
                    "DROP TABLE  `book`, `role`, `user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapUserData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());

            bootstrapRoles();
            bootstrapRights();
            bootstrapRoleRight();
            bootstrapUserRoles();
        }
        bootstrapUsers();

    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRights() throws SQLException {
        for (String right : RIGHTS) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }

    private static void bootstrapUsers() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping users in " + schema + " schema");
            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);

            UserRepository userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);

            AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
            authenticationService.register("cccc.cccc@gmail.com", "Cccccccc1$");
            authenticationService.register("eeee.eeee@gmail.com", "Eeeeeeee1$", EMPLOYEE);
        }
    }

    private static void bootstrapUserRoles() throws SQLException {

    }

    private static void bootstrapBooks() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping books in " + schema + " schema");
            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);

            BookRepository bookRepository = new BookRepositoryMySQL(connectionWrapper.getConnection());

            Book book = new BookBuilder()
                    .setAuthor("Cezar Petrescu")
                    .setTitle("Fram Ursul Polar")
                    .setPublishedDate(LocalDate.of(2010, 6, 2))
                    .setStock(100L)
                    .build();
            bookRepository.save(book);
            book = new BookBuilder()
                    .setAuthor("Fiodor Dostoievski")
                    .setTitle("Fratii Karamazov")
                    .setPublishedDate(LocalDate.of(1880, 11, 2))
                    .setStock(100L)
                    .build();
            bookRepository.save(book);
            book = new BookBuilder()
                    .setAuthor("Edmund Hillary")
                    .setTitle("Inalta aventura")
                    .setPublishedDate(LocalDate.of(1965, 11, 2))
                    .setStock(100L)
                    .build();
            bookRepository.save(book);

        }
    }
    private static void testOrderEmployeeRepository() {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(SCHEMAS[1]);

        BookRepository bookRepository = new BookRepositoryMySQL(connectionWrapper.getConnection());
        OrderEmployeeRepository orderEmployeeRepository = new OrderEmployeeRepositoryMySQL(connectionWrapper.getConnection());

        orderEmployeeRepository.completedBy(3L, 2L);
        for(Order o : orderEmployeeRepository.getOrders()) {
            System.out.println("id:%d---customer:%d----book:%d----employee:%d".formatted(o.getId(), o.getCustomerId(), o.getBookId(), o.getEmployeeId()));
        }


    }
    private static void testAdministratorService() {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(SCHEMAS[1]);
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        UserRepository userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(),rightsRolesRepository);
        AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        AdministratorService administratorService = new AdministratorServiceMySQL(userRepository, authenticationService);
        administratorService.addUser("aaaa.aaaa@gmail.com", "Aaaaaaa1$", ADMINISTRATOR);
        //System.out.println(administratorService.findUser(1L).getResult().toString());
        administratorService.updateUserUsername(3L, "a.a@gmail.com");
        for(User user : administratorService.findAll()) {
            System.out.println(user.toString());
        }
    }
}