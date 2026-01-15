package controller;

import model.MahasiswaDAO;
import model.UserDAO;
import view.LoginView;
import view.MainView;
import javax.swing.*;

public class LoginController {

  // Konstruktor ini menerima objek tampilan (view) agar bisa mengakses tombol & inputannya.
  public LoginController(LoginView view) {

    // 1. Mendaftarkan 'Event Listener' pada tombol Login.
    // Artinya: "Ketika tombol btnLogin diklik, lakukan kode di dalam kurung kurawal ini..."
    view.btnLogin.addActionListener(e -> {

      // 2. Mengambil data yang diketik user di kolom input.
      String user = view.txtUsername.getText();
      // Mengubah array karakter password menjadi String biasa.
      String pass = new String(view.txtPassword.getPassword());

      // 3. Memanggil Model (UserDAO) untuk mengecek kebenaran data.
      UserDAO userDAO = new UserDAO();

      // Jika login berhasil (metode .login mengembalikan true):
      if (userDAO.login(user, pass)) {

        // a. Tampilkan pesan sukses.
        JOptionPane.showMessageDialog(view, "\t\tLogin Berhasil!\nWelcome Back Admin....");

        try {
          // b. Tutup jendela login (buang dari memori).
          view.dispose();

          // c. Siapkan Dashboard Utama (MainView).
          MainView mainView = new MainView();

          // d. Siapkan logika untuk Dashboard (Model & Controller Mahasiswa).
          MahasiswaDAO dao = new MahasiswaDAO();
          new MahasiswaController(mainView, dao);

          // e. Tampilkan Dashboard ke layar.
          mainView.setVisible(true);

        } catch (Throwable ex) {
          // Tangani jika ada error saat membuka dashboard.
          ex.printStackTrace();
          JOptionPane.showMessageDialog(null, "Gagal membuka Dashboard: " + ex.getMessage());
        }

      } else {
        // 4. Jika login gagal, tampilkan pesan peringatan.
        JOptionPane.showMessageDialog(view, "Username/Password Salah!");
      }
    });
  }
}
