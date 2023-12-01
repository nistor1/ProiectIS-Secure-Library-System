package repository.order;

import model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
