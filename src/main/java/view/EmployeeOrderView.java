package view;

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
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;
        import model.Book;
        import model.Order;

        import java.util.List;

public class EmployeeOrderView {
    private Button findAllButton;
    private Button sellBookButton;
    private Button goBackButton;
    private Button deleteOrderButton;
    private Button getReportButton;
    private Button logoutButton;
    private Stage primaryStage;
    private TableView<Order> table = new TableView<Order>();
    private ObservableList<Order> data;
    private TableColumn authorCol;
    private TableColumn titleCol;
    private TableColumn publishedDateCol;
    private TableColumn customerCol;
    private TableColumn employeeCol;
    private Text actiontarget;

    public EmployeeOrderView(Stage primaryStage) {
        primaryStage.setTitle("Book Store");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
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

    }

    private void setTableColumns() {
        authorCol = new TableColumn("Author");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<Order, String>("author"));

        titleCol = new TableColumn("Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Order, String>("title"));

        publishedDateCol = new TableColumn("Published Date");
        publishedDateCol.setMinWidth(100);
        publishedDateCol.setCellValueFactory(
                new PropertyValueFactory<Order, String>("publishedDate"));

        customerCol = new TableColumn("Customer");
        customerCol.setMinWidth(100);
        customerCol.setCellValueFactory(
                new PropertyValueFactory<Order, String>("customerUsername"));
        employeeCol = new TableColumn("Employee");
        employeeCol.setMinWidth(100);
        employeeCol.setCellValueFactory(
                new PropertyValueFactory<Order, String>("employeeUsername"));

        table.setItems(data);
        table.getColumns().addAll(authorCol, titleCol, publishedDateCol, customerCol, employeeCol);
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

        sellBookButton = new Button("Sell Book");
        HBox sellBookButtonHBox = new HBox(10);
        sellBookButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        sellBookButtonHBox.getChildren().add(sellBookButton);
        //gridPane.add(sellBookButtonHBox, 0, 5);

        goBackButton = new Button("Go Back!");
        HBox goBackButtonHBox = new HBox(10);
        goBackButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        goBackButtonHBox.getChildren().add(goBackButton);
        // gridPane.add(findBookButtonHBox, 2, 5);
        leftVbox.getChildren().add(sellBookButtonHBox);
        leftVbox.getChildren().add(goBackButtonHBox);
        gridPane.add(leftVbox, 0, 5);


        deleteOrderButton = new Button("Delete order");
        HBox deleteOrderButtonHBox = new HBox(10);
        deleteOrderButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        deleteOrderButtonHBox.getChildren().add(deleteOrderButton);
        //gridPane.add(addBookButtonHBox, 4, 5);

        findAllButton = new Button("All  orders");
        HBox findAllButtonHBox = new HBox(10);
        findAllButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        findAllButtonHBox.getChildren().add(findAllButton);
        //gridPane.add(signInButtonHBox, 6, 5);
        centerVbox.getChildren().add(deleteOrderButton);
        centerVbox.getChildren().add(findAllButtonHBox);
        gridPane.add(centerVbox, 1, 5);

        getReportButton = new Button("Get Report");
        HBox getRaportButtonHBox = new HBox(10);
        getRaportButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        getRaportButtonHBox.getChildren().add(getReportButton);
       // gridPane.add(getRaportButtonHBox, 3, 5);

        rightVbox.getChildren().add(getRaportButtonHBox);
        gridPane.add(rightVbox, 2, 5);


        logoutButton = new Button("Logout");
        HBox logoutButtonHBox = new HBox(10);
        logoutButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        logoutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logoutButtonHBox, 5, 5);

        return gridPane;
    }


    public void setActionTargetText(String text) {
        this.actiontarget.setText(text);
    }

    public void addFindAllButtonListener(EventHandler<ActionEvent> findAllButtonListener) {
        findAllButton.setOnAction(findAllButtonListener);
    }

    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener) {
        logoutButton.setOnAction(logoutButtonListener);
    }

    public void addSellBookButtonListener(EventHandler<ActionEvent> sellBookButtonListener) {
        sellBookButton.setOnAction(sellBookButtonListener);
    }
    public void addGoBackButtonListener(EventHandler<ActionEvent> goBackButtonListener) {
        goBackButton.setOnAction(goBackButtonListener);
    }
    public void addDeleteOrderButtonListener(EventHandler<ActionEvent> deleteOrderButtonListener) {
        deleteOrderButton.setOnAction(deleteOrderButtonListener);
    }
    public void addGetReportButtonListener(EventHandler<ActionEvent> getReportButtonListener) {
        getReportButton.setOnAction(getReportButtonListener);
    }
    public Order orderSelected() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void setListOfBooks(List<Order> data) {
        ObservableList<Order> observableOrderList = FXCollections.observableList(data);

        this.data = observableOrderList;
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
