package controller.employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.EmployeeOrderComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Book;
import model.User;
import model.builder.BookBuilder;
import model.validator.Notification;
import view.employee.EmployeeBookView;

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

            new EmployeeOrderComponentFactory(componentFactory, employeeView.getPrimaryStage(), user);
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTILE!");
            List<Book> books = componentFactory.getBookService().findAll();
            employeeView.setActionTargetTextToNull();

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
            int error = 0;
            if (title.length() > 500 || title.length() < 1) {
                new IllegalAccessException("Title is empty or has too many characters!");
                employeeView.setActionTargetText("Title is empty or has too many characters!");
                error++;
            }
            String author = employeeView.getAuthorInput();
            if (author.length() > 500 || author.length() < 1) {
                new IllegalAccessException("Author is empty or has too many characters!");
                employeeView.setActionTargetText("Author is empty or has too many characters!");
                error++;
            }
            LocalDate publishedDate = employeeView.getPublishedDateInput();
            if (publishedDate == null) {
                new IllegalAccessException("Invalid published date!");
                employeeView.setActionTargetText("Invalid published date!");
                error++;
            }
            Long stock = employeeView.getStockInput();
            if (stock == null) {
                new IllegalAccessException("Invalid stock input!");
                employeeView.setActionTargetText("Invalid stock input!");
                error++;
            }
            if (error > 0) {
                return;
            }

            System.out.println("ADAUGA CARTE!");
            Book book = new BookBuilder()
                    .setAuthor(author)
                    .setTitle(title)
                    .setPublishedDate(publishedDate)
                    .setStock(stock)
                    .build();

            Notification<Book> bookNotification = componentFactory.getEmployeeService().addBook(book);
            List<Book> books = componentFactory.getBookService().findAll();

            employeeView.setListOfBooks(books);
            if (bookNotification.hasErrors()) {
                employeeView.setActionTargetText(bookNotification.getFormattedErrors());
            }else {
                employeeView.setActionTargetTextToNull();
            }
        }
    }

    private class DeleteBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (employeeView.bookSelected() == null) {
                employeeView.setActionTargetText("Choose a book!");
            } else {
                System.out.println("STERGE CARTE!");
                Book book = employeeView.bookSelected();

                if(!componentFactory.getEmployeeService().deleteBookById(book.getId())) {
                    employeeView.setActionTargetText("Something went wrong in deleting the book!");
                }else {
                    employeeView.setActionTargetTextToNull();
                }

                List<Book> books = componentFactory.getBookService().findAll();
                employeeView.setListOfBooks(books);
            }
        }
    }

    private class FindBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (employeeView.bookSelected() == null) {
                employeeView.setActionTargetText("Choose a book!");
            } else {
                System.out.println("VEZI CARTE!");

                List<Book> books = new ArrayList<>();
                Book book = employeeView.bookSelected();
                books.add(componentFactory.getBookService().findById(book.getId()));

                employeeView.setListOfBooks(books);

                employeeView.setActionTargetTextToNull();

            }
        }
    }

    private class UpdateStockButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = employeeView.getTitleInput();
            int error = 0;
            if (title.length() > 0) {
                new IllegalAccessException("Space for title should be empty!");
                employeeView.setActionTargetText("Space for title should be empty!");

                error++;
            }
            String author = employeeView.getAuthorInput();
            if (author.length() > 0) {
                new IllegalAccessException("Space for author should be empty!");
                employeeView.setActionTargetText("Space for author should be empty!");

                error++;
            }

            Long stock = employeeView.getStockInput();
            if (stock == null) {
                new IllegalAccessException("Invalid stock input!");
                employeeView.setActionTargetText("Invalid stock input!");

                error++;
            }
            if (error > 0) {
                return;
            }
            if (employeeView.bookSelected() == null) {
                employeeView.setActionTargetText("Choose a book!");
            } else {
                System.out.println("ACTUALIZEAZA STOC!");
                Notification<Book> notificationBook = new Notification<>();

                Book book = componentFactory.getBookService().findById(employeeView.bookSelected().getId());
                book.setStock((stock));

                if (!componentFactory.getBookService().updateStockById(book.getId(), book.getStock())) {
                    employeeView.setActionTargetText("Something went wrong in DataBase!");
                } else {
                    employeeView.setActionTargetTextToNull();
                }
                List<Book> books = componentFactory.getBookService().findAll();

                employeeView.setListOfBooks(books);
            }
        }
    }
}
