package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  // Variabel ini bersifat 'static' agar koneksinya tersimpan di memori dan bisa dipakai bersama
  // tanpa perlu login ulang ke database berkali-kali.
  private static Connection connection;

  // Method utama untuk meminta akses/koneksi ke database.
  public static Connection getConnection() {
    try {
      // Logika Pengecekan (Singleton Pattern):
      // Kita cek dulu, apakah koneksi belum pernah dibuat (null) ATAU sudah terputus (closed)?
      if (connection == null || connection.isClosed()) {

        // Jika ya, kita siapkan data login ke MySQL:
        // - URL: alamat database (localhost) dan nama database (Data_Mahasiswa)
        // - User: root (default XAMPP)
        // - Password: kosong (default XAMPP)
        String url = "jdbc:mysql://localhost:3306/Data_Mahasiswa";
        String user = "root";
        String password = "";

        // Perintah untuk membuka gerbang koneksi baru.
        connection = DriverManager.getConnection(url, user, password);
      }
    } catch (SQLException e) {
      // Jika database mati atau salah password, error-nya akan dicetak di sini.
      e.printStackTrace();
    }
    // Mengembalikan objek koneksi yang sudah siap dipakai.
    return connection;
  }
}
