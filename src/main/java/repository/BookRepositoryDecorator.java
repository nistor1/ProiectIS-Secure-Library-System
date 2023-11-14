package repository;

import model.Book;

public abstract class BookRepositoryDecorator implements BookRepository<Book>{

    protected BookRepository decoratedRepository;

    public BookRepositoryDecorator(BookRepository bookRepository){
        this.decoratedRepository = bookRepository;
    }
}
