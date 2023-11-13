package model.builder;

import model.AudioBook;
import model.Book;
import model.EBook;

import java.time.LocalDate;
import java.util.Date;

public class BookBuilder {
    private Book book;

    public BookBuilder(){
        book = new Book();
    }

    public BookBuilder setId(Long id){
        book.setId(id);
        return this;
    }

    public BookBuilder setAuthor(String author){
        book.setAuthor(author);
        return this;
    }

    public BookBuilder setTitle(String title){
        book.setTitle(title);
        return this;
    }

    public BookBuilder setPublishedDate(LocalDate publishedDate){
        book.setPublishedDate(publishedDate);
        return this;
    }

    public EBookBuilder asEBook() {
        EBook eBook = new EBook();

        eBook.setId(book.getId());
        eBook.setAuthor(book.getAuthor());
        eBook.setTitle(book.getTitle());
        eBook.setPublishedDate(book.getPublishedDate());

        return new EBookBuilder((EBook)eBook);
    }

    public AudioBookBuilder asAudioBook() {
        AudioBook audioBook = new AudioBook();

        audioBook.setId(book.getId());
        audioBook.setAuthor(book.getAuthor());
        audioBook.setTitle(book.getTitle());
        audioBook.setPublishedDate(book.getPublishedDate());

        return new AudioBookBuilder((AudioBook)audioBook);
    }


    public Book build(){
        return book;
    }




}
