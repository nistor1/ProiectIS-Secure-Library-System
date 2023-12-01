package service.user;

import model.Book;
import model.Order;

import java.util.List;

public interface EmployeeBookService {
    List<Book> viewAllBooks();
    List<Order> viewAllOrders();
    Order sellBook(Long id, Long stock, Long userId);//

    Book findBookById(Long id);

    Book addBook(Book book);

    boolean updateStockById(Long id, Long stock);

    boolean deleteBookById(Long id);
    boolean deleteOrderById(Long id);
    boolean logout();//

}
