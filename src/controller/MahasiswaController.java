package controller;

import model.Mahasiswa;
import model.MahasiswaDAO;
import view.MainView;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

public class MahasiswaController {
    private MainView view;
    private MahasiswaDAO dao;

    public MahasiswaController(MainView view, MahasiswaDAO dao) {
        this.view = view;
        this.dao = dao;

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

        refreshTable();
    }

    private void highlightMenu(JButton active, JButton inactive) {
        active.setForeground(java.awt.Color.decode("#219e3d"));
        inactive.setForeground(java.awt.Color.WHITE);
    }

    private void saveMahasiswa() {
        try {
            String nim = view.txtNim.getText().trim();
            String nama = view.txtNama.getText().trim();
            String tglStr = view.txtTanggalLahir.getText().trim();
            String prodi = view.txtProdi.getText().trim();

            if (nim.isEmpty() || nama.isEmpty() || tglStr.isEmpty() || prodi.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Semua kolom harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (dao.isNimExists(nim)) {
                JOptionPane.showMessageDialog(view, "NIM sudah terdaftar! Gunakan NIM lain.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date tglLahir = Date.valueOf(tglStr);
            Mahasiswa m = new Mahasiswa(nim, nama, tglLahir, prodi);
            dao.insert(m);

            JOptionPane.showMessageDialog(view, "Data Berhasil Disimpan!");
            clearForm();
            refreshTable();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, "Format Tanggal Salah (YYYY-MM-DD)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
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
            int confirm = JOptionPane.showConfirmDialog(view, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String nim = (String) view.table.getValueAt(row, 0);
                dao.delete(nim);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Pilih baris yang akan dihapus!");
        }
    }

    private void editMahasiswa() {
        int row = view.table.getSelectedRow();
        if (row != -1) {
            String nim = (String) view.table.getValueAt(row, 0);
            String nama = JOptionPane.showInputDialog(view, "Edit Nama:", view.table.getValueAt(row, 1));
            String tglStr = JOptionPane.showInputDialog(view, "Edit Tgl Lahir (YYYY-MM-DD):", view.table.getValueAt(row, 2));
            String prodi = JOptionPane.showInputDialog(view, "Edit Prodi:", view.table.getValueAt(row, 3));

            if (nama != null && tglStr != null && prodi != null) {
                try {
                    Mahasiswa m = new Mahasiswa(nim, nama, Date.valueOf(tglStr), prodi);
                    dao.update(m);
                    refreshTable();
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(view, "Format Tanggal Salah");
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
        view.txtTanggalLahir.setText("");
        view.txtProdi.setText("");
    }
}
