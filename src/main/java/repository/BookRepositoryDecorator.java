package repository;

import model.Book;

public abstract class BookRepositoryDecorator {

    protected BookRepository decoratedRepository;

    public BookRepositoryDecorator(BookRepository bookRepository){
        this.decoratedRepository = bookRepository;
    }
}
