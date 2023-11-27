package service.user;

import model.Book;

import java.util.List;

public interface CustomerService {
    List<Book> viewAllBooks();
    Book buyBook(Long id, Long stock);
}
