package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
  public JTextField txtUsername;
  public JPasswordField txtPassword;
  public JButton btnLogin;

  public LoginView() {
    setTitle("Login System");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());
    getContentPane().setBackground(new Color(33, 33, 33));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel lblTitle = new JLabel("LOGIN ADMIN");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
    lblTitle.setForeground(Color.WHITE);
    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    add(lblTitle, gbc);

    JLabel lblUser = new JLabel("Username:");
    lblUser.setForeground(Color.WHITE);
    gbc.gridy = 1; gbc.gridwidth = 1;
    add(lblUser, gbc);

    txtUsername = new JTextField(15);
    gbc.gridx = 1;
    add(txtUsername, gbc);

    JLabel lblPass = new JLabel("Password:");
    lblPass.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 2;
    add(lblPass, gbc);

    txtPassword = new JPasswordField(15);
    gbc.gridx = 1;
    add(txtPassword, gbc);

    btnLogin = new JButton("LOGIN");
    btnLogin.setBackground(Color.decode("#219e3d"));
    btnLogin.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
    add(btnLogin, gbc);
  }
}
