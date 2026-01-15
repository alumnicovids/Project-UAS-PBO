package model;

import config.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {

  // --- BAGIAN 1: MANIPULASI DATA (CRUD) ---

  // Method untuk MENAMBAH (Insert) data mahasiswa baru ke database.
  public void insert(Mahasiswa m) throws SQLException {
    // Query SQL untuk memasukkan data. Tanda '?' adalah tempat kosong yang akan diisi nanti.
    String sql = "INSERT INTO mahasiswa (nim, nama, tanggal_lahir, prodi) VALUES (?, ?, ?, ?)";

    Connection conn = Database.getConnection();
    // Menggunakan PreparedStatement agar lebih aman dari hack (SQL Injection) & lebih cepat.
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      // Mengisi tanda '?' urut dari 1 sampai 4 dengan data dari objek Mahasiswa 'm'.
      stmt.setString(1, m.getNim());
      stmt.setString(2, m.getNama());
      stmt.setDate(3, m.getTanggalLahir());
      stmt.setString(4, m.getProdi());

      // Eksekusi perintah (Kirim ke database).
      stmt.executeUpdate();
    }
  }

  // Method untuk MENGEDIT (Update) data mahasiswa yang sudah ada berdasarkan NIM.
  public void update(Mahasiswa m) throws SQLException {
    String sql = "UPDATE mahasiswa SET nama=?, tanggal_lahir=?, prodi=? WHERE nim=?";
    Connection conn = Database.getConnection();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, m.getNama());
      stmt.setDate(2, m.getTanggalLahir());
      stmt.setString(3, m.getProdi());
      stmt.setString(4, m.getNim()); // NIM dipakai sebagai kunci pencarian (WHERE nim=?)
      stmt.executeUpdate();
    }
  }

  // Method untuk MENGHAPUS (Delete) data mahasiswa berdasarkan NIM.
  public void delete(String nim) throws SQLException {
    String sql = "DELETE FROM mahasiswa WHERE nim=?";
    Connection conn = Database.getConnection();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, nim);
      stmt.executeUpdate();
    }
  }

  // --- BAGIAN 2: PENGAMBILAN DATA (READ) ---

  // Method untuk mengambil SEMUA data mahasiswa untuk ditampilkan di tabel.
  public List<Mahasiswa> getAll() {
    List<Mahasiswa> list = new ArrayList<>(); // Siapkan wadah list kosong.
    String sql = "SELECT * FROM mahasiswa";

    try (Connection conn = Database.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql)) { // executeQuery dipakai untuk SELECT (baca data).

        // Loop: Selama masih ada baris data (next), ambil datanya.
        while (rs.next()) {
          // Buat objek Mahasiswa dari data baris tersebut, lalu masukkan ke list.
          list.add(new Mahasiswa(
            rs.getString("nim"),
            rs.getString("nama"),
            rs.getDate("tanggal_lahir"),
            rs.getString("prodi")
          ));
        }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list; // Kembalikan daftar mahasiswa yang sudah penuh.
  }

  // Method PENCARIAN data berdasarkan kata kunci (bisa NIM, Nama, atau Prodi).
  public List<Mahasiswa> search(String keyword) {
    List<Mahasiswa> list = new ArrayList<>();
    // Menggunakan 'LIKE' untuk pencarian mirip (partial match).
    String sql = "SELECT * FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ? OR prodi LIKE ?";

    try (Connection conn = Database.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Tambahkan tanda '%' di kiri kanan agar mencari teks yang "mengandung" kata kunci tersebut.
        String key = "%" + keyword + "%";
        stmt.setString(1, key);
        stmt.setString(2, key);
        stmt.setString(3, key);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          list.add(new Mahasiswa(
            rs.getString("nim"),
            rs.getString("nama"),
            rs.getDate("tanggal_lahir"),
            rs.getString("prodi")
          ));
        }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  // --- BAGIAN 3: UTILITAS & STATISTIK (DASHBOARD) ---

  // Cek apakah NIM sudah terdaftar? (Dipakai saat Input agar tidak ada NIM kembar).
  public boolean isNimExists(String nim) {
    String sql = "SELECT count(*) FROM mahasiswa WHERE nim = ?";
    try (Connection conn = Database.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nim);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
          // Jika hitungannya lebih dari 0, berarti sudah ada.
          return rs.getInt(1) > 0;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    return false;
  }

  // Menghitung TOTAL mahasiswa untuk ditampilkan di kartu Dashboard.
  public int getTotalMahasiswa() {
    try (Connection conn = Database.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM mahasiswa")) {
        if (rs.next()) return rs.getInt(1);
      } catch (Exception e) { e.printStackTrace(); }
    return 0;
  }

  // Mencari PRODI TERBANYAK untuk ditampilkan di kartu Dashboard.
  // Logikanya: Kelompokkan berdasarkan prodi, hitung jumlahnya, urutkan dari terbesar, ambil 1 teratas.
  public String getProdiTerbanyak() {
    String sql = "SELECT prodi, COUNT(*) as cnt FROM mahasiswa GROUP BY prodi ORDER BY cnt DESC LIMIT 1";
    try (Connection conn = Database.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) return rs.getString("prodi");
      } catch (Exception e) { e.printStackTrace(); }
    return "-";
  }
}
