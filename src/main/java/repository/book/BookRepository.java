package repository.book;

import model.Book;
import model.validator.Notification;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    boolean save(Book book);

    void removeAll();

    boolean deleteById(Long id);

    boolean updateStockById(Long id, Long stock);

}
