package service.book;

import model.Book;
import repository.book.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService {

    final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        if (bookRepository.save(book)) {
            return true;
        } else {
            throw new IllegalArgumentException("Book not saved: " + book);
        }
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }

    @Override
    public boolean deleteById(Long id) {
        if (bookRepository.deleteById(id)) {
            return true;
        } else {
            throw new IllegalArgumentException("Book with id: %d not deleted".formatted(id));
        }
    }

    @Override
    public boolean updateStockById(Long id, Long stock) {
        if (bookRepository.updateStockById(id, stock)) {
            return true;
        } else {
            throw new IllegalArgumentException("Book with id: %d not updated with stock %d".formatted(id, stock));
        }
    }
}
