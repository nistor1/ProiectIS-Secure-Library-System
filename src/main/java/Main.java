import database.JDBConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMock;
import repository.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");

        BookRepository bookRepository = new BookRepositoryMySQL(connectionWrapper.getConnection());

        bookRepository.removeAll();
        Book book = new BookBuilder()
                .setId(2L)
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        bookRepository.save(book);
        System.out.println(bookRepository.findById(2L));
       // System.out.println(bookRepository.findAll());


    }
}
