package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.User;
import model.validator.Notification;
import repository.book.BookRepository;
import service.user.CustomerService;
import view.CustomerView;

import java.util.List;

public class CustomerController {
    private final CustomerView customerView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public CustomerController(CustomerView customerView, ComponentFactory componentFactory, Notification<User> user) {
        this.customerView = customerView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<Book> books = componentFactory.getBookRepository().findAll();

        customerView.setListOfBooks(books);

        this.customerView.addBuyBookListener(new CustomerController.BuyBookListener());
        this.customerView.addFindAllButtonListener(new CustomerController.FindAllButtonListener());
        this.customerView.addLogoutButtonListener(new CustomerController.Logout());

    }

    private class BuyBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            if (customerView.bookSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            } else {
                System.out.println("CUMPARA");
            }

            componentFactory.getCustomerService().buyBook(customerView.bookSelected().getId(), customerView.bookSelected().getStock());
            List<Book> books = componentFactory.getBookRepository().findAll();

            customerView.setListOfBooks(books);
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTILE");
            List<Book> books = componentFactory.getBookRepository().findAll();

            customerView.setListOfBooks(books);
        }
    }

    private class Logout implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("LOGOUT");
            user.setResult(null);
            LoginComponentFactory loginComponentFactory = new LoginComponentFactory(componentFactory, customerView.getPrimaryStage());

        }
    }
}