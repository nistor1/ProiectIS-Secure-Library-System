package controller.employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.EmployeeBookComponentFactory;
import launcher.componentFactory.LoginComponentFactory;
import model.Order;
import model.User;
import model.validator.Notification;
import view.employee.EmployeeOrderView;

import java.util.List;

import static view.employee.PDFReportGenerator.generatePDFReport;

public class EmployeeOrderController {
    private final EmployeeOrderView employeeView;
    private final ComponentFactory componentFactory;
    private Notification<User> user;

    public EmployeeOrderController(EmployeeOrderView employeeView, ComponentFactory componentFactory, Notification<User> user) {
        this.employeeView = employeeView;
        this.componentFactory = componentFactory;
        this.user = user;

        List<Order> orders = componentFactory.getEmployeeService().viewAllOrders();

        employeeView.setListOfBooks(orders);

        this.employeeView.addSellBookButtonListener(new EmployeeOrderController.SellBookListener());
        this.employeeView.addFindAllButtonListener(new EmployeeOrderController.FindAllButtonListener());
        this.employeeView.addLogoutButtonListener(new EmployeeOrderController.Logout());
        this.employeeView.addGetReportButtonListener(new EmployeeOrderController.GetReportButtonListener());
        this.employeeView.addDeleteOrderButtonListener(new EmployeeOrderController.DeleteOrderButtonListener());
        this.employeeView.addGoBackButtonListener(new EmployeeOrderController.GoBackButtonListener());

    }
    private class SellBookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            if (employeeView.orderSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            }
            Long id = employeeView.orderSelected().getId();
            Order order = componentFactory.getEmployeeService().findOrderById(id);
            if(order.employeeIdIsZero() == false) {
                System.out.println("Nu poti modifica!");
                return;
            }
            System.out.println("VINDE!");

            Long stock = componentFactory.getBookService().findById(employeeView.orderSelected().getBookId()).getStock();
            Long employeeId =  user.getResult().getId();

            componentFactory.getEmployeeService().sellBook(id, (stock - 1), employeeId);

            List<Order> orders = componentFactory.getEmployeeService().viewAllOrders();
            employeeView.setListOfBooks(orders);
        }
    }

    private class FindAllButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI CARTILE!");
            List<Order> orders = componentFactory.getEmployeeService().viewAllOrders();

            employeeView.setListOfBooks(orders);
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

    private class DeleteOrderButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("STERGE COMANDA!");

            if (employeeView.orderSelected().equals(null)) {
                System.out.println("SELECT BOOK!");
            } else {
                System.out.println("STERGE CARTE!");
            }

            componentFactory.getEmployeeService().deleteOrderById(employeeView.orderSelected().getId());

            List<Order> orders = componentFactory.getEmployeeService().viewAllOrders();
            employeeView.setListOfBooks(orders);
        }
    }
    private class GoBackButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("MERGI INAPOI!");
            new EmployeeBookComponentFactory(componentFactory, employeeView.getPrimaryStage(), user);

            //employeeView.setListOfBooks(books);
        }
    }
    private class GetReportButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("VEZI RAPORT!");
            List<Order> orders = componentFactory.getEmployeeService().viewAllOrders();
            generatePDFReport(orders);
        }
    }
}
