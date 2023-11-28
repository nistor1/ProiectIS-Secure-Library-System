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
    private Notification<User> user;
    public CustomerController(CustomerView customerView, BookRepository bookRepository, CustomerService customerService,  Notification<User> user) {
        this.customerView = customerView;
        this.bookRepository = bookRepository;
        this.customerService = customerService;
        this.user = user;

        List<Book> books = bookRepository.findAll();

        customerView.setListOfBooks(books);

        this.customerView.addBuyBookListener(new CustomerController.BuyBookListener());
        this.customerView.addFindAllButtonListener(new CustomerController.FindAllButtonListener());
        this.customerView.addLogoutButtonListener(new CustomerController.Logout());

    }

    private class BuyBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            if(customerView.bookSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            } else {
                System.out.println("CUMPARA");
            }

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
    private class Logout implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("LOGOUT");
            user.setResult(null);
            ComponentFactory componentFactory = ComponentFactory.getInstance(false, customerView.getPrimaryStage());

        }
    }
}