package repository.user;

import model.Book;
import model.Role;
import model.User;
import model.builder.BookBuilder;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Roles.ROLES;
import static database.Constants.Tables.*;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        String sql = "select `user`.id AS id, `user`.username AS username, `user`.password AS password ,`role`.id AS roleId FROM `user` RIGHT JOIN  user_role ON (`user`.id = user_role.user_id)" +
                " LEFT JOIN `role` ON (user_role.role_id = `role`.id)";

        List<User> user = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User newUser = getUserFromResultSet(resultSet);
                boolean found = false;
                for(User u : user) {
                    if(u.getId() == newUser.getId()) {
                        found = true;
                        u.addRole(resultSet.getLong("roleId"));
                    }
                }
                if(found == false) {
                    newUser.addRole(resultSet.getLong("roleId"));
                    user.add(newUser);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // SQL Injection Attacks should not work after fixing functions
    // Be careful that the last character in sql injection payload is an empty space
    // alexandru.ghiurutan95@gmail.com' and 1=1; --
    // ' or username LIKE '%admin%'; --

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        String sql = "Select * from " + USER + " where username = ? and password = ?";

        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet userResultSet = preparedStatement.executeQuery();

            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();

                findByUsernameAndPasswordNotification.setResult(user);
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                return findByUsernameAndPasswordNotification;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }

        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `user` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        String sql = "Select * from `" + USER + "` where username = ?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet userResultSet = preparedStatement.executeQuery();

            return userResultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deleteUserById(Long id) {
        String sql = "DELETE from `" + USER + "` where id = ?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserUsername(Long id, String username) {
        String sql = "UPDATE `user` SET username = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUserPassword(Long id, String password) {
        String sql = "UPDATE `user` SET passsword = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<User> findUser(Long id) {
        String sql = "Select * from `" + USER + "` where id = ?";
        String sqlRole = "Select role_id from `" + USER_ROLE + "` where user_id = ?";
        Optional<User> user = Optional.empty();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet userResultSet = preparedStatement.executeQuery();
            if (userResultSet.next()) {
                user = Optional.of(getUserFromResultSet(userResultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
        List<Role> roles = new ArrayList<>();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sqlRole);
            preparedStatement.setLong(1, id);
            ResultSet userResultSet = preparedStatement.executeQuery();
            while (userResultSet.next()) {
                int roleId = userResultSet.getInt("role_id");
                Long roleIdLong = Long.valueOf(roleId);
                roles.add(new Role(roleIdLong, ROLES[roleId - 1], null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
        user.get().setRoles(roles);
        return user;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .build();
    }
}