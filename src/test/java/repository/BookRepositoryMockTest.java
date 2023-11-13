package repository;

import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class BookRepositoryMockTest {
    private BookRepository bookRepository;

    @Test
    public void testFindAll() {
        BookRepository bookRepository = new BookRepositoryMock();
        bookRepository.removeAll();

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
    public void saveTest() {

        BookRepository bookRepository = new BookRepositoryMock();
        bookRepository.removeAll();

        Book book = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        boolean saved = bookRepository.save(book);
        assertTrue(saved);
    }

    @Test
    public void testFindById() {
        BookRepository bookRepository = new BookRepositoryMock();
        bookRepository.removeAll();

        Book book = new BookBuilder()
                .setId(1L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(book.getId());

        Book book2 = foundBook.get();
        assertTrue(book.equals(book2));
    }

    @Test
    public void testRemoveAll() {
        BookRepository bookRepository = new BookRepositoryMock();
        bookRepository.removeAll();

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
