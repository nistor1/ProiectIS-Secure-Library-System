import model.Book;
import model.builder.BookBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.BookRepositoryMock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestBookRepositoryMock {
    private BookRepositoryMock bookRepository;

    @Test
    public void testFindAll() {
        List<Book> allBooks = bookRepository.findAll();
        assertTrue(allBooks.isEmpty());
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

        List<Book> allBooks = bookRepository.findAll();
        assertEquals(1, allBooks.size());
        assertEquals(book, allBooks.get(0));
    }

    @Test
    public void testFindById() {
        Book book = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();        bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals(book, foundBook.get());

        Optional<Book> notFoundBook = bookRepository.findById(2L);
        assertFalse(notFoundBook.isPresent());
    }

    @Test
    public void testRemoveAll() {
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
}
