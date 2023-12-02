package view.customer;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;
import model.validator.Notification;

import java.util.List;

public class CustomerView {

    private Button buyBookButton;
    private Button findAllButton;
    private Button logoutButton;
    private Text actiontarget;
    private Stage primaryStage;
    private TableView<Book> table = new TableView<Book>();
    private ObservableList<Book> data;
    private TableColumn authorCol;
    private TableColumn titleCol;
    private TableColumn publishedDateCol;

    private TableColumn stockCol;

    public CustomerView(Stage primaryStage) {
        primaryStage.setTitle("Book Store");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 800, 500);
        primaryStage.setScene(scene);
        this.primaryStage = primaryStage;

        initializeSceneTitle(gridPane);
        initializeFields(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Customer page");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }

    private void initializeFields(GridPane gridPane) {
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        setTableColumns();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        gridPane.add(vbox, 0, 1, 3, 1);


        findAllButton = new Button("All  books");
        HBox signInButtonHBox = new HBox(10);
        signInButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        signInButtonHBox.getChildren().add(findAllButton);
        gridPane.add(signInButtonHBox, 2, 4);

        buyBookButton = new Button("Buy book");
        HBox logInButtonHBox = new HBox(10);
        logInButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        logInButtonHBox.getChildren().add(buyBookButton);
        gridPane.add(logInButtonHBox, 0, 4);

        logoutButton = new Button("Logout");
        HBox logoutButtonHBox = new HBox(10);
        logoutButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        logoutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logoutButtonHBox, 0, 5);

        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget, 1, 6);
    }

    private void setTableColumns() {
        authorCol = new TableColumn("Author");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("author"));

        titleCol = new TableColumn("Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("title"));

        publishedDateCol = new TableColumn("Published Date");
        publishedDateCol.setMinWidth(200);
        publishedDateCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("publishedDate"));

        stockCol = new TableColumn("Stock");
        stockCol.setMinWidth(200);
        stockCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("stock"));

        table.setItems(data);
        table.getColumns().addAll(authorCol, titleCol, publishedDateCol, stockCol);
    }

    public void setActionTargetText(String text) {
        this.actiontarget.setText(text);
    }
    public void setActionTargetTextToNull() {
        this.actiontarget.setText("");
    }
    public void addFindAllButtonListener(EventHandler<ActionEvent> findAllButtonListener) {
        findAllButton.setOnAction(findAllButtonListener);
    }

    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener) {
        logoutButton.setOnAction(logoutButtonListener);
    }

    public void addBuyBookListener(EventHandler<ActionEvent> buyBookListener) {
        buyBookButton.setOnAction(buyBookListener);
    }

    public Book bookSelected() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void setListOfBooks(List<Book> data) {
        ObservableList<Book> observableBookList = FXCollections.observableList(data);

        this.data = observableBookList;
        table.setItems(this.data);
        table.refresh();
        primaryStage.show();

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

