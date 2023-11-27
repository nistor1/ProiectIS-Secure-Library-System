package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.Book;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import service.user.CustomerService;
import view.CustomerView;
import view.LoginView;

import java.util.EventListener;
import java.util.List;

public class CustomerController {
    private final CustomerView customerView;
    private final BookRepository bookRepository;
    private CustomerService customerService;
    public CustomerController(CustomerView customerView, BookRepository bookRepository, CustomerService customerService) {
        this.customerView = customerView;
        this.bookRepository = bookRepository;
        this.customerService = customerService;

        List<Book> books = bookRepository.findAll();

        customerView.setListOfBooks(books);

        this.customerView.addBuyBookListener(new CustomerController.BuyBookListener());
        this.customerView.addFindAllButtonListener(new CustomerController.FindAllButtonListener());

    }

    private class BuyBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {

            System.out.println("CUMPARA");
            customerService.buyBook(customerView.bookSelected().getId(), customerView.bookSelected().getStock());
            List<Book> books = bookRepository.findAll();

            customerView.setListOfBooks(books);
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTILE");
            List<Book> books = bookRepository.findAll();

            customerView.setListOfBooks(books);
        }
    }
}