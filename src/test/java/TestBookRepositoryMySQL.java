import model.Book;
import model.builder.BookBuilder;
import org.junit.Test;
import repository.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBookRepositoryMySQL {
    private Connection connection;
    private BookRepositoryMySQL bookRepository;

    @Test
    public void testFindAll() {
        int expectedNumberOfBooks = 3;

        List<Book> books = bookRepository.findAll();

        assertEquals(books.size(), expectedNumberOfBooks);
    }

    @Test
    public void testFindById() {

        Long id = 23L;

        Optional<Book> book = bookRepository.findById(id);
        assertTrue(book.isPresent());
    }

    @Test
    public void testSave() {
        Book book = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        boolean saved = bookRepository.save(book);

        assertTrue(saved);
    }

    @Test
    public void testRemoveAll() {

        bookRepository.removeAll();
        List<Book> books = bookRepository.findAll();

        assertTrue(books.isEmpty());
    }
}
