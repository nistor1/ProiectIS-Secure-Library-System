package service.book;

import model.Book;
import model.validator.Notification;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    boolean save(Book book);

    int getAgeOfBook(Long id);

    boolean deleteById(Long id);

    boolean updateStockById(Long id, Long stock);
}
