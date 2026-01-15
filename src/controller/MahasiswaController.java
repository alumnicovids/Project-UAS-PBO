package controller;

import model.Mahasiswa;
import model.MahasiswaDAO;
import view.MainView;

import javax.swing.*;
import java.sql.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class MahasiswaController {
  private MainView view;
  private MahasiswaDAO dao;

  // Konstruktor Utama: Menghubungkan Tampilan (View) dan Data (DAO).
  public MahasiswaController(MainView view, MahasiswaDAO dao) {
    this.view = view;
    this.dao = dao;

    // 1. --- NAVIGASI MENU (SIDEBAR) ---
    // Mengatur apa yang terjadi saat tombol menu diklik:

    // Tombol Dashboard -> Tampilkan panel statistik.
    this.view.btnMenuDashboard.addActionListener(e -> showDashboard());

    // Tombol Input Data -> Tampilkan form input & ubah warna tombol menu jadi aktif.
    this.view.btnMenuInsert.addActionListener(e -> {
      view.cardLayout.show(view.centerPanel, "INSERT");
      highlightMenu(view.btnMenuInsert, view.btnMenuView);
    });

    // Tombol Lihat Data -> Segarkan tabel, tampilkan panel tabel.
    this.view.btnMenuView.addActionListener(e -> {
      refreshTable();
      view.cardLayout.show(view.centerPanel, "VIEW");
      highlightMenu(view.btnMenuView, view.btnMenuInsert);
    });

    // 2. --- AKSI TOMBOL CRUD (Create, Read, Update, Delete) ---
    this.view.btnSave.addActionListener(e -> saveMahasiswa());
    this.view.btnDelete.addActionListener(e -> deleteMahasiswa());
    this.view.btnEdit.addActionListener(e -> editMahasiswa());

    // 3. --- PENCARIAN REAL-TIME ---
    // Memasang pendengar pada kolom pencarian. Setiap ketikan akan langsung memicu pencarian.
    this.view.txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(javax.swing.event.DocumentEvent e) { searchData(); }
      public void removeUpdate(javax.swing.event.DocumentEvent e) { searchData(); }
      public void changedUpdate(javax.swing.event.DocumentEvent e) { searchData(); }
    });

    // Tampilkan dashboard saat pertama kali dibuka.
    showDashboard();
    refreshTable();
  }

  // Menampilkan Statistik di Halaman Dashboard
  private void showDashboard() {
    // Ambil data total & prodi terbanyak dari DAO, lalu pasang di Label.
    view.lblTotal.setText("<html><center>Total Mahasiswa<br/><br/><span style='font-size:24px'>" + dao.getTotalMahasiswa() + "</span></center></html>");
    view.lblProdiDominan.setText("<html><center>Prodi Terbanyak<br/><br/><span style='font-size:24px'>" + dao.getProdiTerbanyak() + "</span></center></html>");

    // Pindah kartu ke panel DASHBOARD.
    view.cardLayout.show(view.centerPanel, "DASHBOARD");
    highlightMenu(view.btnMenuDashboard, view.btnMenuView);
  }

  // Fungsi Kosmetik: Mengubah warna teks tombol menu agar terlihat mana yang sedang aktif.
  private void highlightMenu(JButton active, JButton inactive) {
    active.setForeground(java.awt.Color.decode("#219e3d")); // Hijau (Aktif)
    inactive.setForeground(java.awt.Color.WHITE);          // Putih (Tidak Aktif)
  }

  // Logika MENYIMPAN Data Baru
  private void saveMahasiswa() {
    try {
      // Ambil data dari form input.
      String nim = view.txtNim.getText().trim();
      String nama = view.txtNama.getText().trim();
      java.util.Date rawDate = view.dateChooser.getDate();
      String prodi = view.cbProdi.getSelectedItem().toString();

      // Validasi 1: Cek apakah ada kolom kosong?
      if (nim.isEmpty() || nama.isEmpty() || rawDate == null || prodi.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Semua kolom harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return; // Berhenti di sini, jangan lanjut simpan.
      }

      // Validasi 2: Cek apakah NIM sudah dipakai orang lain?
      if (dao.isNimExists(nim)) {
        JOptionPane.showMessageDialog(view, "NIM sudah terdaftar! Gunakan NIM lain.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
      }

      // Konversi tanggal dan bungkus data jadi objek Mahasiswa.
      Date tglLahir = new Date(rawDate.getTime());
      Mahasiswa m = new Mahasiswa(nim, nama, tglLahir, prodi);

      // Kirim ke DAO untuk disimpan ke database.
      dao.insert(m);
      JOptionPane.showMessageDialog(view, "Data Berhasil Disimpan!");

      // Bersihkan form dan update tabel.
      clearForm();
      refreshTable();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Logika MEMPERBARUI Tabel (Mengambil data terbaru dari DB)
  private void refreshTable() {
      view.tableModel.setRowCount(0); // Hapus semua baris lama di tabel.
      List<Mahasiswa> list = dao.getAll(); // Ambil semua data baru.
      for (Mahasiswa m : list) {
          // Masukkan satu per satu ke tabel tampilan.
          view.tableModel.addRow(new Object[]{m.getNim(), m.getNama(), m.getTanggalLahir(), m.getProdi()});
      }
  }

  // Logika MENGHAPUS Data
  private void deleteMahasiswa() {
    int row = view.table.getSelectedRow(); // Cek baris mana yang diklik user.
    if (row != -1) {
      // Ambil NIM dari baris tersebut (kolom ke-0).
      row = view.table.convertRowIndexToModel(row);
      String nim = (String) view.tableModel.getValueAt(row, 0);

      // Minta konfirmasi (Yes/No).
      int confirm = JOptionPane.showConfirmDialog(view, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        try {
          dao.delete(nim); // Hapus dari database.
          refreshTable();  // Update tabel.
        } catch (Exception e) {
          JOptionPane.showMessageDialog(view, "Gagal Hapus: " + e.getMessage());
        }
      }
    } else {
      JOptionPane.showMessageDialog(view, "Pilih baris yang akan dihapus!");
    }
  }

  // Logika MENGEDIT Data (Menampilkan Popup)
  private void editMahasiswa() {
    int row = view.table.getSelectedRow();
    if (row != -1) {
      row = view.table.convertRowIndexToModel(row);
      String nim = (String) view.tableModel.getValueAt(row, 0); // NIM (Kunci Utama)

      // Siapkan inputan popup dengan data lama yang sudah terisi.
      JTextField txtNama = new JTextField((String) view.tableModel.getValueAt(row, 1));
      JDateChooser txtTgl = new JDateChooser();
      txtTgl.setDateFormatString("yyyy-MM-dd");
      try {
        txtTgl.setDate((Date) view.tableModel.getValueAt(row, 2));
      } catch (Exception e) {}

      JComboBox<String> boxProdi = new JComboBox<>(new String[]{"Sistem Komputer", "Sistem Informasi", "Teknologi Informasi", "Bisnis Digital", "Manajemen Informasi"});
      boxProdi.setSelectedItem(view.tableModel.getValueAt(row, 3));

      Object[] message = {"Nama:", txtNama, "Tgl Lahir:", txtTgl, "Prodi:", boxProdi};

      // Tampilkan Popup Dialog.
      int option = JOptionPane.showConfirmDialog(view, message, "Edit Data", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        try {
          // Validasi tanggal tidak kosong.
          if (txtTgl.getDate() == null) throw new IllegalArgumentException("Tanggal tidak boleh kosong");

          // Bungkus data baru (NIM tetap sama, data lain berubah).
          Mahasiswa m = new Mahasiswa(nim, txtNama.getText(),
          new Date(txtTgl.getDate().getTime()),
          boxProdi.getSelectedItem().toString());

          dao.update(m); // Update ke database.
          refreshTable();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(view, "Gagal Edit: " + e.getMessage());
        }
      }
    } else {
      JOptionPane.showMessageDialog(view, "Pilih baris yang akan diedit!");
    }
  }

  // Logika PENCARIAN
  private void searchData() {
    view.tableModel.setRowCount(0); // Bersihkan tabel.
    String keyword = view.txtSearch.getText(); // Ambil kata kunci.
    List<Mahasiswa> list = dao.search(keyword); // Cari di database.
    for (Mahasiswa m : list) {
      view.tableModel.addRow(new Object[]{m.getNim(), m.getNama(), m.getTanggalLahir(), m.getProdi()});
    }
  }

  // Membersihkan formulir input setelah simpan berhasil.
  private void clearForm() {
    view.txtNim.setText("");
    view.txtNama.setText("");
    view.dateChooser.setDate(null);
    view.cbProdi.setSelectedIndex(0);
  }
}
