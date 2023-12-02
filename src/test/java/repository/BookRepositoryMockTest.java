package repository;

import model.Book;
import model.builder.BookBuilder;
import model.validator.Notification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMock;
import repository.book.Cache;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMockTest {

    private static BookRepository bookRepository;

    @BeforeAll
    public static void setupClass(){
        bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMock(),
                new Cache<>()
        );
    }

    @Test
    public void findAll(){
        assertEquals(0, bookRepository.findAll().size());
    }

    @Test
    public void findById(){
        final Optional<Book> books = bookRepository.findById(1L);
        assertTrue(books.isEmpty());
    }

    @Test
    public void save(){
        Book book = new BookBuilder()
                .setAuthor("Author")
                .setTitle("Title")
                .build();

        assertTrue(bookRepository.save(book));
    }

    @Test
    public void removeAll() {
        Book book1 = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        Book book2 = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        bookRepository.save(book1);
        bookRepository.save(book2);

        bookRepository.removeAll();
        List<Book> allBooks = bookRepository.findAll();

        assertTrue(allBooks.isEmpty());
    }

    @Test
    public void deleteById() {
        Book book1 = new BookBuilder()
                .setId(5L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        bookRepository.save(book1);
        assertTrue(bookRepository.deleteById(5L));
    }
}
