import controller.LoginController;
import view.LoginView;
import javax.swing.SwingUtilities;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      LoginView loginView = new LoginView();
      new LoginController(loginView);
      loginView.setVisible(true);
    });
  }
}
