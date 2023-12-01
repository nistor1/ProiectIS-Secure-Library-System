package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.User;
import model.validator.Notification;
import view.CustomerView;
import view.EmployeeView;

import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public EmployeeController(EmployeeView employeeView, ComponentFactory componentFactory, Notification<User> user) {
        this.employeeView = employeeView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<Book> books = componentFactory.getBookRepository().findAll();

        employeeView.setListOfBooks(books);

        this.employeeView.addSellBookButtonListener(new EmployeeController.SellBookListener());
        this.employeeView.addFindAllButtonListener(new EmployeeController.FindAllButtonListener());
        this.employeeView.addLogoutButtonListener(new EmployeeController.Logout());
        this.employeeView.addAddBookButtonListener(new EmployeeController.AddBookButtonListener());
        this.employeeView.addDeleteBookButtonListener(new EmployeeController.DeleteBookButtonListener());
        this.employeeView.addGetReportButtonListener(new EmployeeController.GetReportButtonListener());
        this.employeeView.addFindBookButtonListener(new EmployeeController.FindBookButtonListener());
        this.employeeView.addUpdateStockButtonListener(new EmployeeController.UpdateStockButtonListener());

    }
    private class SellBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            if (employeeView.bookSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            } else {
                System.out.println("CUMPARA!");
            }

            componentFactory.getEmployeeService().sellBook(employeeView.bookSelected().getId(), employeeView.bookSelected().getStock());
            List<Book> books = componentFactory.getBookRepository().findAll();

            employeeView.setListOfBooks(books);
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTILE!");
            List<Book> books = componentFactory.getBookRepository().findAll();

            employeeView.setListOfBooks(books);
        }
    }

    private class Logout implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("LOGOUT");
            user.setResult(null);
            LoginComponentFactory loginComponentFactory = new LoginComponentFactory(componentFactory, employeeView.getPrimaryStage());

        }
    }

    private class AddBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("ADAUGA CARTE!");
            //List<Book> books = componentFactory.getBookRepository().findAll();

            //employeeView.setListOfBooks(books);
        }
    }
    private class DeleteBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("STERGE CARTE!");
            //List<Book> books = componentFactory.getBookRepository().findAll();

            //employeeView.setListOfBooks(books);
        }
    }
    private class GetReportButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI RAPORT!");
            //List<Book> books = componentFactory.getBookRepository().findAll();

            //employeeView.setListOfBooks(books);
        }
    }
    private class FindBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTE!");
           // List<Book> books = componentFactory.getBookRepository().findAll();

            //employeeView.setListOfBooks(books);
        }
    }
    private class UpdateStockButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("ACTUALIZEAZA STOC!");
            List<Book> books = componentFactory.getBookRepository().findAll();

            employeeView.setListOfBooks(books);
        }
    }
}
