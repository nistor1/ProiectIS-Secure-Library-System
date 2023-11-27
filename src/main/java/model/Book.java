package model;

// Java Bean -

// POJO - Plain Old Java Object


import java.time.LocalDate;
import java.util.Date;

public class Book {

    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;

    private Long stock;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("Id: %d | Title: %s | Author: %s | Date: %s", this.id, this.title, this.author, this.publishedDate);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                author.equals(book.author) &&
                title.equals(book.title) &&
                publishedDate.equals(book.publishedDate);
    }
}
