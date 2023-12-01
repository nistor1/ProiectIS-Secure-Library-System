package repository.order;

import model.Order;

import java.util.List;

public interface OrderEmployeeRepository {
    boolean deleteById(Long id);
    boolean completedBy(Long orderId, Long userId);
    List<Order> getOrders();
}
