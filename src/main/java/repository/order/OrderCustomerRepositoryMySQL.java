package repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderCustomerRepositoryMySQL implements OrderCustomerRepository{
    private final Connection connection;

    public OrderCustomerRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addOrder(Long idBook, Long idUser) {
        String sql = "INSERT INTO `order` VALUES(null, ?, ?, null);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idBook);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
