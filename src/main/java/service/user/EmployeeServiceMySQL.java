package service.user;

import model.Book;
import service.book.BookService;

import java.util.List;

public class EmployeeServiceMySQL implements EmployeeService {
    private BookService bookService;

    public EmployeeServiceMySQL(BookService bookService) {
        this.bookService = bookService;
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
    public Book sellBook(Long id, Long stock) {
        Book book = bookService.findById(id);
        if (book.getStock() < 1) {
            return book;
        }
        book.setStock((book.getStock() - 1));
        bookService.updateStockById(id, stock);

        return book;
    }

    @Override
    public boolean logout() {
        return false;
    }
}
