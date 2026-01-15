package controller;

import model.MahasiswaDAO;
import model.UserDAO;
import view.LoginView;
import view.MainView;
import javax.swing.*;

public class LoginController {
  public LoginController(LoginView view) {
    view.btnLogin.addActionListener(e -> {
      String user = view.txtUsername.getText();
      String pass = new String(view.txtPassword.getPassword());

      UserDAO userDAO = new UserDAO();
      if (userDAO.login(user, pass)) {
        JOptionPane.showMessageDialog(view, "        Login Berhasil!\nWelcome Back Admin....");
        try {
          view.dispose();
          MainView mainView = new MainView();
          MahasiswaDAO dao = new MahasiswaDAO();
          new MahasiswaController(mainView, dao);
          mainView.setVisible(true);
        } catch (Throwable ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(null, "Gagal membuka Dashboard: " + ex.getMessage());
        }

      } else {
        JOptionPane.showMessageDialog(view, "Username/Password Salah!");
      }
    });
  }
}
