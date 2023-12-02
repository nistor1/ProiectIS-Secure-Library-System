package service.user.employee;

import model.Book;
import model.Order;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface EmployeeBookService {
    List<Book> viewAllBooks();
    List<Order> viewAllOrders();
    List<Order> viewAllOrdersForEmployee(User user);

    Notification<Order> sellBook(Long id, Long stock, Long userId);
    Order findOrderById(Long id);

    Book findBookById(Long id);

    Notification<Book> addBook(Book book);

    boolean updateStockById(Long id, Long stock);

    boolean deleteBookById(Long id);
    boolean deleteOrderById(Long id);
    boolean logout();//

}
