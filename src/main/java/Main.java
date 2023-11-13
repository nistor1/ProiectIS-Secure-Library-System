import database.JDBConnectionWrapper;
import model.AudioBook;
import model.Book;
import model.EBook;
import model.builder.AudioBookBuilder;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMySQL;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");

        BookRepository bookRepository = new BookRepositoryMySQL(connectionWrapper.getConnection());

        bookRepository.removeAll();
        Book book = new BookBuilder()
                .setAuthor("', '', null); SLEEP(20); --")
                .setTitle("Fram Ursul Polar")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        bookRepository.save(book);

        Book eBook = new EBookBuilder()
                .setAuthor("a1")
                .setId(2L)
                .setTitle("dfg")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .asEBook().setFormat("pdf")
                .build();
        bookRepository.save(eBook);

        Book audioBook = new AudioBookBuilder()
                .setAuthor("a2")
                .setId(2L)
                .setTitle("dfg")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .asAudioBook().setRunTime(54)
                .build();
        bookRepository.save(eBook);

        //System.out.println(bookRepository.findById(27L));
        for(Book b : bookRepository.findAll()) {
            if(b instanceof EBook) {
                EBook bTemp = (EBook) b;
               bTemp.toString();
            } else if (b instanceof AudioBook) {
                AudioBook bTemp = (AudioBook)b;
                bTemp.toString();
            } else if (b instanceof Book) {
                System.out.println(b);
            }
        }
       //System.out.println(bookRepository.findAll());


    }
}
