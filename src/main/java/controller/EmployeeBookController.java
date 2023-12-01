package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.EmployeeBookComponentFactory;
import launcher.componentFactory.EmployeeOrderComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.User;
import model.builder.BookBuilder;
import model.validator.Notification;
import view.EmployeeBookView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBookController {
    private final EmployeeBookView employeeView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public EmployeeBookController(EmployeeBookView employeeView, ComponentFactory componentFactory, Notification<User> user) {
        this.employeeView = employeeView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<Book> books = componentFactory.getBookRepository().findAll();

        employeeView.setListOfBooks(books);

        this.employeeView.addGoToOrdersPageButtonListener(new EmployeeBookController.ToOrdersPageButtonListener());
        this.employeeView.addFindAllButtonListener(new EmployeeBookController.FindAllButtonListener());
        this.employeeView.addLogoutButtonListener(new EmployeeBookController.Logout());
        this.employeeView.addAddBookButtonListener(new EmployeeBookController.AddBookButtonListener());
        this.employeeView.addDeleteBookButtonListener(new EmployeeBookController.DeleteBookButtonListener());
        this.employeeView.addFindBookButtonListener(new EmployeeBookController.FindBookButtonListener());
        this.employeeView.addUpdateStockButtonListener(new EmployeeBookController.UpdateStockButtonListener());

    }
    private class ToOrdersPageButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            System.out.println("MERGI LA PAGINA PENTRU COMENZI!");

            new EmployeeOrderComponentFactory(componentFactory, employeeView, user);
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
            String title = employeeView.getTitleInput();
            if(title.length() > 0) {
                new IllegalAccessException("Title has too many characters!");
                return;
            }
            String author = employeeView.getAuthorInput();
            if(author.length() > 500) {
                new IllegalAccessException("Author has too many characters!");
                return;
            }
            LocalDate publishedDate = employeeView.getPublishedDateInput();
            if(publishedDate == null) {
                new IllegalAccessException("Invalid published date!");
                return;
            }
            Long stock = employeeView.getStockInput();
            if(stock == null) {
                new IllegalAccessException("Invalid stock input!");
                return;
            }
            System.out.println("ADAUGA CARTE!");
            Book book = new BookBuilder()
                    .setAuthor(author)
                    .setTitle(title)
                    .setPublishedDate(publishedDate)
                    .setStock(stock)
                    .build();

            componentFactory.getEmployeeService().addBook(book);
            List<Book> books = componentFactory.getBookRepository().findAll();

            employeeView.setListOfBooks(books);
        }
    }
    private class DeleteBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (employeeView.bookSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            } else {
                System.out.println("STERGE CARTE!");
            }

            componentFactory.getEmployeeService().deleteBookById(employeeView.bookSelected().getId());

            List<Book> books = componentFactory.getBookRepository().findAll();
            employeeView.setListOfBooks(books);
        }
    }
    private class FindBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTE!");

            List<Book> books = new ArrayList<>();
            books.add(componentFactory.getBookService().findById(employeeView.bookSelected().getId()));

            employeeView.setListOfBooks(books);
        }
    }
    private class UpdateStockButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = employeeView.getTitleInput();
            if(title.length() > 0) {
                new IllegalAccessException("Space for title should be empty!");
                return;
            }
            String author = employeeView.getAuthorInput();
            if(author.length() > 0) {
                new IllegalAccessException("Space for author should be empty!");
                return;
            }

            Long stock = employeeView.getStockInput();
            if(stock == null) {
                new IllegalAccessException("Invalid stock input!");
                return;
            }
            System.out.println("ACTUALIZEAZA STOC!");
            Book book = componentFactory.getBookService().findById(employeeView.bookSelected().getId());
            book.setStock((stock++));

            componentFactory.getBookService().updateStockById(book.getId(), book.getStock());

            List<Book> books = componentFactory.getBookRepository().findAll();

            employeeView.setListOfBooks(books);
        }
    }
}
