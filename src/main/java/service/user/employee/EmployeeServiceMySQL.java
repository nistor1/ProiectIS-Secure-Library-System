package service.user.employee;

import model.Book;
import model.Order;
import model.User;
import model.validator.Notification;
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
    public Notification<Book> addBook(Book book) {
        Notification<Book> bookNotification = new Notification<>();
        bookNotification.setResult(book);
        if (bookService.save(book)) {
            return bookNotification;
        } else {
            bookNotification.addError("Something went wrong in Data Base");
            return  bookNotification;
        }
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
    public Notification<Order> sellBook(Long id, Long stock, Long userId) {
        Optional<Order> order = orderEmployeeRepository.findOrderById(id);
        Notification<Order> orderNotification  =new Notification<>();
        if (order.isEmpty()) {
            new IllegalArgumentException("Order not found!");
            orderNotification.addError("Order not found!");
            return orderNotification;
        }

        if (stock < 1) {
            new IllegalArgumentException("Insufficient stock!");
            orderNotification.addError("Insufficient stock!");
            return orderNotification;
        }
        stock--;
        if(!bookService.updateStockById(order.get().getBookId(), stock) || !orderEmployeeRepository.completedBy(id, userId)) {
            orderNotification.addError("Something went wrong in DataBase");
        }
        orderNotification.setResult(order.get());

        return orderNotification;
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
    public List<Order> viewAllOrdersForEmployee(User user) {
        return orderEmployeeRepository.getOrdersForEmployee(user);
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
