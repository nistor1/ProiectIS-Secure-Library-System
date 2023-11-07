package repository;

import database.JDBConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {
    private Connection connection;
    private BookRepositoryMySQL bookRepository;

    @Test
    public void testFindAll() {
        int expectedNumberOfBooks = 0;

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);

        List<Book> books = bookRepository.findAll();

        assertEquals(books.size(), expectedNumberOfBooks);
    }

    @Test
    public void testFindById() {

        Long id = 23L;
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);

        Optional<Book> book = bookRepository.findById(id);

        Boolean isPresent = book.isPresent();
        assertTrue(isPresent);
    }

    @Test
    public void testSave() {

        Book book = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);

        boolean isSaved = bookRepository.save(book);
        assertTrue(isSaved);

    }

    @Test
    public void testRemoveAll() {
        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);

        bookRepository.removeAll();
        List<Book> books = bookRepository.findAll();

        Boolean isEmpty = books.isEmpty();
        assertTrue(isEmpty);
    }
}
