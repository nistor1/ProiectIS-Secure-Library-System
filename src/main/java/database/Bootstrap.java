package database;

import model.Book;
import model.Role;
import model.User;
import model.builder.BookBuilder;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.ROLES;
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

    private static void bootstrapUsers() throws SQLException{
        for(String schema : SCHEMAS) {
            System.out.println("Bootstrapping users in " + schema + " schema");
            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);

            UserRepository userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
            Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);

            AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
            authenticationService.register("aaaa.aaaa@gmail.com", "Aaaaaaaa1$");

        }
    }

    private static void bootstrapUserRoles() throws SQLException {

    }

    private static void bootstrapBooks() throws SQLException {
        for(String schema : SCHEMAS) {
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
}