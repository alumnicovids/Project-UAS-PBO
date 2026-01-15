package model;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

  // Method ini berfungsi untuk memvalidasi login admin.
  // Menerima input username dan password, lalu mengembalikan 'true' jika data benar.
  public boolean login(String username, String password) {

    // 1. Menyiapkan query SQL.
    // Tanda tanya (?) adalah placeholder untuk mencegah SQL Injection (peretasan database).
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

    // 2. Membuka koneksi ke database dan menyiapkan statement.
    // 'try-with-resources' (dalam kurung) otomatis menutup koneksi setelah selesai.
    try (Connection conn = Database.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {

      // 3. Mengisi tanda tanya (?) dengan data asli dari parameter.
      stmt.setString(1, username);
      stmt.setString(2, password);

      // 4. Menjalankan query dan menampung hasilnya di ResultSet.
      ResultSet rs = stmt.executeQuery();

      // 5. Cek apakah ada data yang ditemukan?
      // rs.next() akan bernilai true jika ada baris data (artinya login sukses).
      return rs.next();

    } catch (Exception e) {
      // Jika terjadi error koneksi, tampilkan pesan error di konsol dan anggap login gagal.
      e.printStackTrace();
      return false;
    }
  }
}
