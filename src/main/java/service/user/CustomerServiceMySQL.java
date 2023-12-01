package service.user;

import model.Book;
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

    public Book buyBook(Long idBook, Long stock, Long idUser) {
        Book book = bookService.findById(idBook);
        if (book.getStock() < 1) {
            return book;
        }
        orderCustomerRepository.addOrder(idBook, idUser);
        return book;
    }

    public boolean logout() {
        return false;
    }
}
