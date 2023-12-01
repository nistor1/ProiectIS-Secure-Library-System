package launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import launcher.componentFactory.ComponentFactory;
import launcher.componentFactory.LoginComponentFactory;

public class Launcher extends Application {
    public static void main(String[] args){
        launch(args);
    }

    // Iterative Programming
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory componentFactory = ComponentFactory.getInstance(false);

        LoginComponentFactory loginComponentFactory = new LoginComponentFactory(componentFactory, primaryStage);
      //  LoginView loginView =  new LoginView(primaryStage);
        //EmployeeBookComponentFactory employeeComponentFactory = new EmployeeBookComponentFactory(componentFactory, loginView, null);
    }
}
