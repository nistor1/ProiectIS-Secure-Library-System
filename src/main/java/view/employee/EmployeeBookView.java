package view.employee;

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

import java.time.LocalDate;
import java.util.List;

public class EmployeeBookView {
    private Button findAllButton;
    private Button goToOrderPageButton;
    private Button findBookButton;
    private Button addBookButton;
    private Button updateStockButton;
    private Button deleteBookButton;
    private Button logoutButton;
    private TextField authorTextFieldInput = new TextField();
    private TextField titleTextFieldInput = new TextField();
    private DatePicker publishedDateTextFieldInput = new DatePicker();
    private TextField stockTextFieldInput = new TextField();
    private Stage primaryStage;
    private TableView<Book> table = new TableView<Book>();
    private ObservableList<Book> data;
    private TableColumn authorCol;
    private TableColumn titleCol;
    private TableColumn publishedDateCol;
    private TableColumn stockCol;
    private Text actiontarget;

    public EmployeeBookView(Stage primaryStage) {
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
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Employee page");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }

    private void initializeFields(GridPane gridPane) {
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        setTableColumns();
        table.setPrefHeight(200);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        gridPane.add(vbox, 0, 1, 6, 1);

        gridPane = createButtons(gridPane);

        HBox inputBookTextFields = new HBox(10);
        inputBookTextFields.getChildren().add(titleTextFieldInput);
        inputBookTextFields.getChildren().add(authorTextFieldInput);
        inputBookTextFields.getChildren().add(publishedDateTextFieldInput);
        inputBookTextFields.getChildren().add(stockTextFieldInput);
        gridPane.add(inputBookTextFields, 0, 3, 6, 1);
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

    private GridPane createButtons(GridPane gridPane) {


        final VBox leftVbox = new VBox();
        leftVbox.setSpacing(5);
        leftVbox.setPadding(new Insets(10, 0, 0, 10));
        final VBox centerVbox = new VBox();
        centerVbox.setSpacing(5);
        centerVbox.setPadding(new Insets(10, 0, 0, 10));
        final VBox rightVbox = new VBox();
        rightVbox.setSpacing(5);
        rightVbox.setPadding(new Insets(10, 0, 0, 10));

        goToOrderPageButton = new Button("Go to orders!");
        HBox goToOrderPageButtonHBox = new HBox(10);
        goToOrderPageButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        goToOrderPageButtonHBox.getChildren().add(goToOrderPageButton);
        //gridPane.add(sellBookButtonHBox, 0, 5);

        findBookButton = new Button("Find Book");
        HBox findBookButtonHBox = new HBox(10);
        findBookButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        findBookButtonHBox.getChildren().add(findBookButton);
        // gridPane.add(findBookButtonHBox, 2, 5);
        leftVbox.getChildren().add(goToOrderPageButtonHBox);
        leftVbox.getChildren().add(findBookButtonHBox);
        gridPane.add(leftVbox, 0, 5);


        addBookButton = new Button("Add Book");
        HBox addBookButtonHBox = new HBox(10);
        addBookButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        addBookButtonHBox.getChildren().add(addBookButton);
        //gridPane.add(addBookButtonHBox, 4, 5);

        findAllButton = new Button("All  books");
        HBox findAllButtonHBox = new HBox(10);
        findAllButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        findAllButtonHBox.getChildren().add(findAllButton);
        //gridPane.add(signInButtonHBox, 6, 5);
        centerVbox.getChildren().add(addBookButton);
        centerVbox.getChildren().add(findAllButtonHBox);
        gridPane.add(centerVbox, 1, 5);


        updateStockButton = new Button("Update Stock");
        HBox updateStockButtonHBox = new HBox(10);
        updateStockButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        updateStockButtonHBox.getChildren().add(updateStockButton);
        //gridPane.add(updateStockButtonHBox, 0, 6);

        deleteBookButton = new Button("Delete Book");
        HBox deleteBookButtonHBox = new HBox(10);
        deleteBookButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        deleteBookButtonHBox.getChildren().add(deleteBookButton);
        // gridPane.add(deleteBookButtonHBox, 2, 6);
        rightVbox.getChildren().add(updateStockButtonHBox);
        rightVbox.getChildren().add(deleteBookButtonHBox);
        gridPane.add(rightVbox, 2, 5);


        logoutButton = new Button("Logout");
        HBox logoutButtonHBox = new HBox(10);
        logoutButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        logoutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logoutButtonHBox, 3, 5);

        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget, 1, 6);

        return gridPane;
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

    public void addGoToOrdersPageButtonListener(EventHandler<ActionEvent> goToOrdersPageButtonListener) {
        goToOrderPageButton.setOnAction(goToOrdersPageButtonListener);
    }
    public void addFindBookButtonListener(EventHandler<ActionEvent> findBookButtonListener) {
        findBookButton.setOnAction(findBookButtonListener);
    }
    public void addAddBookButtonListener(EventHandler<ActionEvent> addBookButtonListener) {
        addBookButton.setOnAction(addBookButtonListener);
    }
    public void addUpdateStockButtonListener(EventHandler<ActionEvent> updateStockButtonListener) {
        updateStockButton.setOnAction(updateStockButtonListener);
    }
    public void addDeleteBookButtonListener(EventHandler<ActionEvent> deleteBookButtonListener) {
        deleteBookButton.setOnAction(deleteBookButtonListener);
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

    public Button getFindAllButton() {
        return findAllButton;
    }

    public String getTitleInput() {
        return authorTextFieldInput.getText();
    }

    public String getAuthorInput() {
        return titleTextFieldInput.getText();
    }

    public LocalDate getPublishedDateInput() {
        return publishedDateTextFieldInput.getValue();
    }

    public Long getStockInput() {
        try {
            String inputText = stockTextFieldInput.getText();
            return Long.parseLong(inputText);
        } catch (NumberFormatException e) {
            new IllegalAccessException("Invalid stock value!");
            return null;
        }
    }
}
