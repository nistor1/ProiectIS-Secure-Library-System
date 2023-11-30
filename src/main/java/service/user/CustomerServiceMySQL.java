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

    public Book buyBook(Long id, Long stock) {
        Book book = bookService.findById(id);
        if (book.getStock() < 1) {
            return book;
        }
        book.setStock((book.getStock() - 1));
        bookService.updateStockById(id, stock);
        return book;
    }

    public boolean logout() {
        return false;
    }
}
