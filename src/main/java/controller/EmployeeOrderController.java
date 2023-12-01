package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.User;
import model.validator.Notification;
import view.EmployeeBookView;
import view.EmployeeOrderView;

import java.util.List;

public class EmployeeOrderController {
    private final EmployeeOrderView employeeView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public EmployeeOrderController(EmployeeOrderView employeeView, ComponentFactory componentFactory, Notification<User> user) {
        this.employeeView = employeeView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<Book> books = componentFactory.getBookRepository().findAll();

        employeeView.setListOfBooks(books);

        this.employeeView.addSellBookButtonListener(new EmployeeOrderController.SellBookListener());
        this.employeeView.addFindAllButtonListener(new EmployeeOrderController.FindAllButtonListener());
        this.employeeView.addLogoutButtonListener(new EmployeeOrderController.Logout());
        this.employeeView.addAddBookButtonListener(new EmployeeOrderController.AddBookButtonListener());
        this.employeeView.addDeleteBookButtonListener(new EmployeeOrderController.DeleteBookButtonListener());
        this.employeeView.addGetReportButtonListener(new EmployeeOrderController.GetReportButtonListener());
        this.employeeView.addFindBookButtonListener(new EmployeeOrderController.FindBookButtonListener());
        this.employeeView.addUpdateStockButtonListener(new EmployeeOrderController.UpdateStockButtonListener());

    }
    private class SellBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            if (employeeView.bookSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            } else {
                System.out.println("CUMPARA!");
            }

            componentFactory.getEmployeeService().sellBook(employeeView.bookSelected().getId(), employeeView.bookSelected().getStock(), user.getResult().getId());
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
