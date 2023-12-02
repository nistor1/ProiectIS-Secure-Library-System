package service.user.customer;

import model.Book;
import model.validator.Notification;
import view.LoginView;

import java.util.List;

public interface CustomerService {
    List<Book> viewAllBooks();
    Notification<Book> buyBook(Long idBook, Long stock, Long idUser);
    boolean logout();
}
