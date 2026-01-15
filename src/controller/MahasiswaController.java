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

  public MahasiswaController(MainView view, MahasiswaDAO dao) {
    this.view = view;
    this.dao = dao;

    this.view.btnMenuDashboard.addActionListener(e -> showDashboard());

    this.view.btnSave.addActionListener(e -> saveMahasiswa());
    this.view.btnDelete.addActionListener(e -> deleteMahasiswa());
    this.view.btnEdit.addActionListener(e -> editMahasiswa());

    this.view.btnMenuInsert.addActionListener(e -> {
      view.cardLayout.show(view.centerPanel, "INSERT");
      highlightMenu(view.btnMenuInsert, view.btnMenuView);
    });

    this.view.btnMenuView.addActionListener(e -> {
      refreshTable();
      view.cardLayout.show(view.centerPanel, "VIEW");
      highlightMenu(view.btnMenuView, view.btnMenuInsert);
    });

    this.view.txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(javax.swing.event.DocumentEvent e) { searchData(); }
      public void removeUpdate(javax.swing.event.DocumentEvent e) { searchData(); }
      public void changedUpdate(javax.swing.event.DocumentEvent e) { searchData(); }
    });

    showDashboard();
    refreshTable();
  }

  private void showDashboard() {
    view.lblTotal.setText("<html><center>Total Mahasiswa<br/><br/><span style='font-size:24px'>" + dao.getTotalMahasiswa() + "</span></center></html>");
    view.lblProdiDominan.setText("<html><center>Prodi Terbanyak<br/><br/><span style='font-size:24px'>" + dao.getProdiTerbanyak() + "</span></center></html>");
    view.cardLayout.show(view.centerPanel, "DASHBOARD");
    highlightMenu(view.btnMenuDashboard, view.btnMenuView);
  }

  private void highlightMenu(JButton active, JButton inactive) {
    active.setForeground(java.awt.Color.decode("#219e3d"));
    inactive.setForeground(java.awt.Color.WHITE);
  }

  private void saveMahasiswa() {
    try {
      String nim = view.txtNim.getText().trim();
      String nama = view.txtNama.getText().trim();
      java.util.Date rawDate = view.dateChooser.getDate();
      String prodi = view.cbProdi.getSelectedItem().toString();

      if (nim.isEmpty() || nama.isEmpty() || rawDate == null || prodi.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Semua kolom harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
      }

      if (dao.isNimExists(nim)) {
        JOptionPane.showMessageDialog(view, "NIM sudah terdaftar! Gunakan NIM lain.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
      }

      Date tglLahir = new Date(rawDate.getTime());
      Mahasiswa m = new Mahasiswa(nim, nama, tglLahir, prodi);
      dao.insert(m);
      JOptionPane.showMessageDialog(view, "Data Berhasil Disimpan!");

      clearForm();
      refreshTable();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void refreshTable() {
      view.tableModel.setRowCount(0);
      List<Mahasiswa> list = dao.getAll();
      for (Mahasiswa m : list) {
          view.tableModel.addRow(new Object[]{m.getNim(), m.getNama(), m.getTanggalLahir(), m.getProdi()});
      }
  }

  private void deleteMahasiswa() {
    int row = view.table.getSelectedRow();
    if (row != -1) {
      row = view.table.convertRowIndexToModel(row);
      String nim = (String) view.tableModel.getValueAt(row, 0);

      int confirm = JOptionPane.showConfirmDialog(view, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        try {
          dao.delete(nim);
          refreshTable();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(view, "Gagal Hapus: " + e.getMessage());
        }
      }
    } else {
      JOptionPane.showMessageDialog(view, "Pilih baris yang akan dihapus!");
    }
  }

  private void editMahasiswa() {
    int row = view.table.getSelectedRow();
    if (row != -1) {
      row = view.table.convertRowIndexToModel(row);
      String nim = (String) view.tableModel.getValueAt(row, 0);

      JTextField txtNama = new JTextField((String) view.tableModel.getValueAt(row, 1));
      JDateChooser txtTgl = new JDateChooser();
      txtTgl.setDateFormatString("yyyy-MM-dd");
      try {
        txtTgl.setDate((Date) view.tableModel.getValueAt(row, 2));
      } catch (Exception e) {}

      JComboBox<String> boxProdi = new JComboBox<>(new String[]{"Sistem Komputer", "Sistem Informasi", "Teknologi Informasi", "Bisnis Digital", "Manajemen Informasi"});
      boxProdi.setSelectedItem(view.tableModel.getValueAt(row, 3));

      Object[] message = {"Nama:", txtNama, "Tgl Lahir:", txtTgl, "Prodi:", boxProdi};

      int option = JOptionPane.showConfirmDialog(view, message, "Edit Data", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        try {
          if (txtTgl.getDate() == null) throw new IllegalArgumentException("Tanggal tidak boleh kosong");

          Mahasiswa m = new Mahasiswa(nim, txtNama.getText(),
          new Date(txtTgl.getDate().getTime()),
          boxProdi.getSelectedItem().toString());

          dao.update(m);
          refreshTable();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(view, "Gagal Edit: " + e.getMessage());
        }
      }
    } else {
      JOptionPane.showMessageDialog(view, "Pilih baris yang akan diedit!");
    }
  }

  private void searchData() {
    view.tableModel.setRowCount(0);
    String keyword = view.txtSearch.getText();
    List<Mahasiswa> list = dao.search(keyword);
    for (Mahasiswa m : list) {
      view.tableModel.addRow(new Object[]{m.getNim(), m.getNama(), m.getTanggalLahir(), m.getProdi()});
    }
  }

  private void clearForm() {
    view.txtNim.setText("");
    view.txtNama.setText("");
    view.dateChooser.setDate(null);
    view.cbProdi.setSelectedIndex(0);
  }
}
