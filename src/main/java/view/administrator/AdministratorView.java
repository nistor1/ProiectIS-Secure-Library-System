package view.administrator;

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
import model.User;

import java.util.List;

public class AdministratorView {
    private Button findAllButton;
    private Button addUserButton;
    private Button findUserButton;
    private Button updateUsernameButton;
    private Button updatePasswordButton;
    private Button deleteUserButton;
    private Button getReportButton;
    private Button logoutButton;
    private TextField usernameTextFieldInput = new TextField();
    private TextField passwordTextFieldInput = new TextField();
    private ChoiceBox<String> rolesBox = new ChoiceBox<>();
    private Stage primaryStage;
    private TableView<User> table = new TableView<User>();
    private ObservableList<User> data;
    private TableColumn authorCol;
    private TableColumn titleCol;
    private TableColumn publishedDateCol;
    private TableColumn stockCol;
    private Text actiontarget;

    public AdministratorView(Stage primaryStage) {
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
        Text sceneTitle = new Text("Administrator page");
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
        inputBookTextFields.getChildren().add(usernameTextFieldInput);
        inputBookTextFields.getChildren().add(passwordTextFieldInput);
        rolesBox.getItems().addAll("Administrator", "Employee", "Customer");
        gridPane.add(inputBookTextFields, 0, 3, 6, 1);
        gridPane.add(rolesBox, 3, 3, 6, 1);
    }

    private void setTableColumns() {
        authorCol = new TableColumn("Username");
        authorCol.setMinWidth(200);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("username"));

        titleCol = new TableColumn("Password");
        titleCol.setMinWidth(200);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password"));

        publishedDateCol = new TableColumn("Role");
        publishedDateCol.setMinWidth(100);
        publishedDateCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("role"));

        table.setItems(data);
        table.getColumns().addAll(authorCol, titleCol, publishedDateCol);
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

        addUserButton = new Button("Add user!");
        HBox addUserButtonHBox = new HBox(10);
        addUserButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        addUserButtonHBox.getChildren().add(addUserButton);
        //gridPane.add(sellBookButtonHBox, 0, 5);

        findUserButton = new Button("Find user");
        HBox findUserButtonHBox = new HBox(10);
        findUserButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        findUserButtonHBox.getChildren().add(findUserButton);
        // gridPane.add(findBookButtonHBox, 2, 5);
        leftVbox.getChildren().add(addUserButtonHBox);
        leftVbox.getChildren().add(findUserButtonHBox);
        gridPane.add(leftVbox, 0, 5);


        updateUsernameButton = new Button("Update Username");
        HBox updateUsernameButtonHBox = new HBox(10);
        updateUsernameButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        updateUsernameButtonHBox.getChildren().add(updateUsernameButton);
        //gridPane.add(addBookButtonHBox, 4, 5);

        findAllButton = new Button("All  users");
        HBox findAllButtonHBox = new HBox(10);
        findAllButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        findAllButtonHBox.getChildren().add(findAllButton);
        //gridPane.add(signInButtonHBox, 6, 5);
        centerVbox.getChildren().add(updateUsernameButtonHBox);
        centerVbox.getChildren().add(findAllButtonHBox);
        gridPane.add(centerVbox, 1, 5);


        updatePasswordButton = new Button("Update Password");
        HBox updatePasswordButtonHBox = new HBox(10);
        updatePasswordButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        updatePasswordButtonHBox.getChildren().add(updatePasswordButton);
        //gridPane.add(updateStockButtonHBox, 0, 6);

        deleteUserButton = new Button("Delete User");
        HBox deleteUserButtonHBox = new HBox(10);
        deleteUserButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        deleteUserButtonHBox.getChildren().add(deleteUserButton);
        // gridPane.add(deleteBookButtonHBox, 2, 6);
        rightVbox.getChildren().add(updatePasswordButtonHBox);
        rightVbox.getChildren().add(deleteUserButtonHBox);
        gridPane.add(rightVbox, 2, 5);

        getReportButton = new Button("Get Report");
        HBox getReportButtonHBox = new HBox(10);
        getReportButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        getReportButtonHBox.getChildren().add(getReportButton);
        gridPane.add(getReportButtonHBox, 3, 5);

        logoutButton = new Button("Logout");
        HBox logoutButtonHBox = new HBox(10);
        logoutButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        logoutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logoutButtonHBox, 4, 5);

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

    public void addAddUserButtonListener(EventHandler<ActionEvent> addUserButtonistener) {
        addUserButton.setOnAction(addUserButtonistener);
    }
    public void addFindUserButtonListener(EventHandler<ActionEvent> findUserButtonListener) {
        findUserButton.setOnAction(findUserButtonListener);
    }
    public void addUpdateUsernameButtonListener(EventHandler<ActionEvent> updateUsernameButtonListener) {
        updateUsernameButton.setOnAction(updateUsernameButtonListener);
    }
    public void addUpdatePasswordButtonListener(EventHandler<ActionEvent> updatePasswordButtonListener) {
        updatePasswordButton.setOnAction(updatePasswordButtonListener);
    }
    public void addDeleteUserButtonListener(EventHandler<ActionEvent> deleteUserButtonListener) {
        deleteUserButton.setOnAction(deleteUserButtonListener);
    }
    public void addGetReportButtonListener(EventHandler<ActionEvent> getReportButtonListener) {
        getReportButton.setOnAction(getReportButtonListener);
    }
    public User userSelected() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void setListOfUsers(List<User> data) {
        ObservableList<User> observableBookList = FXCollections.observableList(data);

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

    public String getUsernameTextInput() {
        return usernameTextFieldInput.getText();
    }

    public String getPasswordTextFieldInput() {
        return passwordTextFieldInput.getText();
    }

    public Long getRoleBox() {
        return Long.valueOf( rolesBox.getSelectionModel().getSelectedIndex());
    }
}
