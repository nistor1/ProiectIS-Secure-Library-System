package view;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static javafx.application.Application.launch;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Application;

public class ViewTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CustomerView customerView = new CustomerView(stage);
        stage.show();
    }

    @Test
    public void testCustomerView() {
        // Aici puteți adăuga codul dvs. de testare
        Stage primaryStage = new Stage();
        CustomerView customerView = new CustomerView(primaryStage);
    }
}