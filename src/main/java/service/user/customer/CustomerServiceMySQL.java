package service.user.customer;

import model.Book;
import model.validator.Notification;
import repository.order.OrderCustomerRepository;
import service.book.BookService;

import java.util.List;

public class CustomerServiceMySQL implements CustomerService {
    private BookService bookService;
    private OrderCustomerRepository orderCustomerRepository;

    public CustomerServiceMySQL(BookService bookService, OrderCustomerRepository orderCustomerRepository) {
        this.bookService = bookService;
        this.orderCustomerRepository = orderCustomerRepository;
    }

    public List<Book> viewAllBooks() {
        return bookService.findAll();
    }

    public Notification<Book> buyBook(Long idBook, Long stock, Long idUser) {

        Notification<Book> bookNotification = new Notification<>();
        bookNotification.setResult(bookService.findById(idBook));

        if (bookNotification.getResult().getStock() < 1) {
            bookNotification.addError("Insufficient stock!");
            return bookNotification;
        }
        if(idBook == null || idBook == 0) {
            bookNotification.addError("Choose a book!");
        }
        if(idUser == null || idUser == 0) {
            bookNotification.addError("How is this even possible?");
        }
        orderCustomerRepository.addOrder(idBook, idUser);
        return bookNotification;
    }

    public boolean logout() {
        return false;
    }
}
