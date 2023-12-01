package service.user;

import model.Book;
import view.LoginView;

import java.util.List;

public interface CustomerService {
    List<Book> viewAllBooks();
    Book buyBook(Long idBook, Long stock, Long idUser);
    boolean logout();
}
