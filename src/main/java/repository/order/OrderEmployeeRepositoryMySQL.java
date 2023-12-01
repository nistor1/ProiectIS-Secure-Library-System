package repository.order;

import model.Book;
import model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderEmployeeRepositoryMySQL implements OrderEmployeeRepository {
    private Connection connection;
    public OrderEmployeeRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM `order` WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean completedBy(Long orderId, Long userId) {
        String sql = "UPDATE `order` SET completedByEmployee_id = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Order> getOrders() {
        String sql = "SELECT * FROM `order` WHERE completedByEmployee_id >= 0;";

        List<Order> orders = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderFromResultSet(ResultSet resultSet) {
        try{
            return new Order(resultSet.getLong("id"), resultSet.getLong("user_id"), resultSet.getLong("book_id"), resultSet.getLong("completedByEmployee_id"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Order getFriendlyOrderFromResultSet(ResultSet resultSet) {
        try{
            return new Order(resultSet.getLong("id"),
                            resultSet.getLong("book_id"),
                            resultSet.getLong("customer_id"),
                            resultSet.getString("book.author"), resultSet.getString("book.title"),
                            resultSet.getString("book.publishedDate"), resultSet.getString("user_username"),
                            resultSet.getString("employee_username"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        String sql = "SELECT * FROM `order` WHERE id = ?";
        Optional<Order> order = Optional.empty();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = Optional.of(getOrderFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public List<Order> getFriendlyOrders() {
        String sql = "SELECT `order`.id AS id, `order`.book_id AS book_id, `order`.user_id AS customer_id, book.author, book.title, book.publishedDate, u1.username AS user_username, u2.username AS employee_username" +
                    " FROM book RIGHT JOIN `order` ON book.id = `order`.book_id " +
                    " JOIN `user` AS u1 ON `order`.user_id = u1.id " +
                    " LEFT JOIN `user` AS u2 ON `order`.completedByEmployee_id = u2.id";

        List<Order> orders = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                orders.add(getFriendlyOrderFromResultSet(resultSet));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
