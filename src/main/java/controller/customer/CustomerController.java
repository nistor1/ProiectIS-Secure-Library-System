package controller.customer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.User;
import model.validator.Notification;
import view.customer.CustomerView;

import java.util.List;

public class CustomerController {
    private final CustomerView customerView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public CustomerController(CustomerView customerView, ComponentFactory componentFactory, Notification<User> user) {
        this.customerView = customerView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<Book> books = componentFactory.getBookService().findAll();

        customerView.setListOfBooks(books);
        this.customerView.addBuyBookListener(new CustomerController.BuyBookListener());
        this.customerView.addFindAllButtonListener(new CustomerController.FindAllButtonListener());
        this.customerView.addLogoutButtonListener(new CustomerController.Logout());


    }

    private class BuyBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            if (customerView.bookSelected() == null) {
                customerView.setActionTargetText("Choose a book!");
            } else {
                System.out.println("CUMPARA");

                Notification<Book> bookNotification = new Notification<>();
                bookNotification.setResult(customerView.bookSelected());

                componentFactory.getCustomerService().buyBook(bookNotification.getResult().getId(), bookNotification.getResult().getStock(), user.getResult().getId());
                List<Book> books = componentFactory.getBookService().findAll();

                customerView.setListOfBooks(books);

                if (bookNotification.hasErrors()) {
                    customerView.setActionTargetText(bookNotification.getFormattedErrors());
                } else {
                    customerView.setActionTargetText("The order: " + bookNotification.getResult().toString() + " is waiting to be confirmed.");
                }
            }
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTILE");
            List<Book> books = componentFactory.getBookService().findAll();

            customerView.setListOfBooks(books);
            customerView.setActionTargetTextToNull();

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