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

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySQLTest {
    private Connection connection;
    private BookRepository bookRepository;

    @Test
    public void testFindAll() {
        int expectedNumberOfBooks = 0;

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);
        bookRepository.removeAll();

        Book book1 = new BookBuilder()
                .setId(1L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        Book book2 = new BookBuilder()
                .setId(2L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> allBooks = bookRepository.findAll();
        int i = 1;
        for(Book b : allBooks) {
            if(i == 1) {
                assertTrue(b.equals(book1));
            } else {
                assertTrue(b.equals(book2));
            }
            i++;
        }
    }

    @Test
    public void testFindById() {

        Book book = new BookBuilder()
                .setId(1L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);
        bookRepository.removeAll();

        bookRepository.save(book);


        Optional<Book> bookTemp = bookRepository.findById(book.getId());
        Book book2 = bookTemp.get();

        assertTrue(book.equals(book2));
    }

    @Test
    public void testSave() {

        Book book = new BookBuilder()
                .setId(1L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        connection = connectionWrapper.getConnection();
        bookRepository = new BookRepositoryMySQL(connection);
        bookRepository.removeAll();

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
