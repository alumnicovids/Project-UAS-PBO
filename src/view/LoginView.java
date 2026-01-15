package view;

import javax.swing.*;
import java.awt.*;

// Kelas ini mewarisi 'JFrame', artinya kelas ini adalah sebuah jendela aplikasi.
public class LoginView extends JFrame {

  // Komponen-komponen yang perlu diakses oleh Controller (dibuat 'public')
  public JTextField txtUsername;   // Kolom isi username
  public JPasswordField txtPassword; // Kolom isi password (teksnya disensor bintang/titik)
  public JButton btnLogin;         // Tombol Login

  public LoginView() {
    // 1. Konfigurasi Dasar Jendela
    setTitle("Login System");          // Judul di bar atas
    setSize(400, 300);                 // Ukuran jendela
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program mati total jika jendela ini ditutup
    setLocationRelativeTo(null);       // Posisi jendela di tengah layar monitor
    setLayout(new GridBagLayout());    // Menggunakan tata letak grid yang fleksibel (GridBag)
    getContentPane().setBackground(new Color(33, 33, 33)); // Warna latar belakang gelap (Dark Mode)

    // 2. Pengaturan Posisi Komponen (GridBagConstraints)
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Memberi jarak antar komponen (padding)
    gbc.fill = GridBagConstraints.HORIZONTAL; // Komponen akan melebar memenuhi ruang

    // 3. Menambahkan Judul "LOGIN ADMIN"
    JLabel lblTitle = new JLabel("LOGIN ADMIN");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20)); // Font tebal ukuran 20
    lblTitle.setForeground(Color.WHITE); // Warna teks putih
    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

    // Posisi (x=0, y=0), lebar 2 kolom (biar di tengah)
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    add(lblTitle, gbc);

    // 4. Menambahkan Label & Kolom Username
    JLabel lblUser = new JLabel("Username:");
    lblUser.setForeground(Color.WHITE);
    gbc.gridy = 1; gbc.gridwidth = 1; // Pindah baris ke-1
    add(lblUser, gbc);

    txtUsername = new JTextField(15); // Kolom input selebar 15 karakter
    gbc.gridx = 1; // Kolom sebelah kanan
    add(txtUsername, gbc);

    // 5. Menambahkan Label & Kolom Password
    JLabel lblPass = new JLabel("Password:");
    lblPass.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 2; // Pindah baris ke-2
    add(lblPass, gbc);

    txtPassword = new JPasswordField(15);
    gbc.gridx = 1;
    add(txtPassword, gbc);

    // 6. Menambahkan Tombol Login
    btnLogin = new JButton("LOGIN");
    btnLogin.setBackground(Color.decode("#219e3d")); // Warna tombol hijau
    btnLogin.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; // Baris ke-3, lebar penuh
    add(btnLogin, gbc);
  }
}
