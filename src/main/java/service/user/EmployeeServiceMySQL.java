package service.user;

import model.Book;
import model.Order;
import repository.order.OrderEmployeeRepository;
import service.book.BookService;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceMySQL implements EmployeeBookService {
    private BookService bookService;
    private OrderEmployeeRepository orderEmployeeRepository;

    public EmployeeServiceMySQL(BookService bookService, OrderEmployeeRepository orderEmployeeRepository) {
        this.bookService = bookService;
        this.orderEmployeeRepository = orderEmployeeRepository;
    }

    @Override
    public Book findBookById(Long id) {
        return bookService.findById(id);
    }

    @Override
    public Book addBook(Book book) {
        bookService.save(book);
        return book;
    }

    @Override
    public boolean updateStockById(Long id, Long stock) {
        return bookService.updateStockById(id, stock);
    }

    @Override
    public boolean deleteBookById(Long id) {
        return bookService.deleteById(id);
    }

    @Override
    public List<Book> viewAllBooks() {
        return bookService.findAll();
    }

    @Override
    public Order sellBook(Long id, Long stock, Long userId) {
        Optional<Order> order = orderEmployeeRepository.findOrderById(id);
        if (order.isEmpty()) {
            new IllegalArgumentException("Order not found!");
            return null;
        }

        if (stock < 1) {
            new IllegalArgumentException("Insufficient stock!");
            return null;
        }

        bookService.updateStockById(order.get().getBookId(), (stock--));
        orderEmployeeRepository.completedBy(id, userId);

        return order.get();
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public List<Order> viewAllOrders() {
        return orderEmployeeRepository.getFriendlyOrders();
    }

    @Override
    public boolean deleteOrderById(Long id) {
        return orderEmployeeRepository.deleteById(id);
    }
    @Override
    public Order findOrderById(Long id) {
        Optional<Order> order = orderEmployeeRepository.findOrderById(id);

        if(order.isEmpty()) {
            new IllegalArgumentException("Order not found");
            return null;
        }
        return order.get();
    }
}
