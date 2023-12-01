package launcher.componentFactory;

import controller.LoginController;
import javafx.stage.Stage;
import view.LoginView;

public class LoginComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private static LoginComponentFactory instance;

    public LoginComponentFactory(ComponentFactory componentFactory, Stage stage) {
        this.loginView = new LoginView(stage);
        this.loginController = new LoginController(loginView, componentFactory);
    }
}
