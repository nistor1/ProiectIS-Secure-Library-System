package service.user;

import model.Book;
import service.book.BookService;

import java.util.List;

public class CustomerServiceMySQL implements CustomerService {
    private BookService bookService;
    public CustomerServiceMySQL(BookService bookService) {
        this.bookService = bookService;
    }

    public List<Book> viewAllBooks() {
        return bookService.findAll();
    }

    public Book buyBook(Long id) {
        Book book = bookService.findById(id);
        bookService.deleteById(id);
        return book;
    }
}
