package repository.order;

import model.Order;
import model.User;

import java.util.List;
import java.util.Optional;

public interface OrderEmployeeRepository {
    boolean deleteById(Long id);
    boolean completedBy(Long orderId, Long userId);
    Optional<Order> findOrderById(Long id);
    List<Order> getOrders();
    List<Order> getFriendlyOrders();
    List<Order> getOrdersForEmployee(User user);
}
