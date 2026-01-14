package model;

import config.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {
    public void insert(Mahasiswa m) throws SQLException {
        String sql = "INSERT INTO mahasiswa (nim, nama, tanggal_lahir, prodi) VALUES (?, ?, ?, ?)";
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNim());
            stmt.setString(2, m.getNama());
            stmt.setDate(3, m.getTanggalLahir());
            stmt.setString(4, m.getProdi());
            stmt.executeUpdate();
        }
    }

    public void update(Mahasiswa m) {
        String sql = "UPDATE mahasiswa SET nama=?, tanggal_lahir=?, prodi=? WHERE nim=?";
        try (Connection conn = Database.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNama());
            stmt.setDate(2, m.getTanggalLahir());
            stmt.setString(3, m.getProdi());
            stmt.setString(4, m.getNim());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim=?";
        try (Connection conn = Database.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mahasiswa> getAll() {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa";
        try (Connection conn = Database.getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(sql)) {
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

    public boolean isNimExists(String nim) {
        String sql = "SELECT count(*) FROM mahasiswa WHERE nim = ?";
        try (Connection conn = Database.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Mahasiswa> search(String keyword) {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ? OR prodi LIKE ?";
        try (Connection conn = Database.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
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
}
