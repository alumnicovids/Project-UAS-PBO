package view;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainView extends JFrame {

  // --- DEKLARASI KOMPONEN UI ---
  // Komponen ini dibuat 'public' agar Controller bisa mengaksesnya (misal: mengambil teks input).

  // Navigasi Sidebar
  public JButton btnMenuDashboard;
  public JButton btnMenuInsert;
  public JButton btnMenuView;

  // Komponen Halaman Dashboard (Statistik)
  public JLabel lblTotal, lblProdiDominan;

  // Komponen Halaman Input Data
  public JTextField txtNim, txtNama;
  public JDateChooser dateChooser; // Kalender
  public JComboBox<String> cbProdi; // Pilihan Dropdown
  public JButton btnSave;

  // Komponen Halaman View (Tabel)
  public JTextField txtSearch;
  public JTable table;
  public DefaultTableModel tableModel; // Model data tabel (baris & kolom)
  public JButton btnEdit, btnDelete;

  // Layout Manager utama untuk ganti-ganti halaman
  public CardLayout cardLayout;
  public JPanel centerPanel; // Panel tengah yang isinya berubah-ubah
  public JPanel sidebarPanel;

  // Warna Tema Aplikasi (Memudahkan jika ingin ganti tema warna nantinya)
  private final Color colorBg = new Color(33, 33, 33);      // Abu-abu gelap (Background)
  private final Color colorSidebar = new Color(45, 45, 45); // Abu-abu sedikit terang (Sidebar)
  private final Color colorAccent = Color.decode("#2ecc71");// Hijau terang (Aksen utama)
  private final Color colorAccentEnd = Color.decode("#1abc9c"); // Hijau toska (Gradasi)

  // --- KONSTRUKTOR UTAMA ---
  public MainView() {
    setTitle("Project UAS - Dashboard Mahasiswa");
    setSize(1000, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout()); // Layout utama: Kiri (Sidebar) + Tengah (Isi)

    // 1. Membangun Sidebar (Menu Kiri)
    initSidebar();

    // 2. Membangun Panel Tengah (Isi Halaman)
    initCenterPanel();

    add(sidebarPanel, BorderLayout.WEST);
    add(centerPanel, BorderLayout.CENTER);
  }

  // --- BAGIAN 1: SIDEBAR ---
  private void initSidebar() {
    sidebarPanel = new JPanel();
    sidebarPanel.setPreferredSize(new Dimension(220, getHeight())); // Lebar 220px
    sidebarPanel.setBackground(colorSidebar);
    sidebarPanel.setLayout(new GridLayout(10, 1, 0, 10)); // Susun menu ke bawah
    sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10)); // Jarak pinggir

    // Judul Aplikasi di Pojok Kiri Atas
    JLabel lblTitle = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
    lblTitle.setForeground(colorAccent);
    sidebarPanel.add(lblTitle);

    // Membuat Tombol-tombol Menu
    btnMenuDashboard = createSidebarButton("Dashboard");
    btnMenuInsert = createSidebarButton("Input Data");
    btnMenuView = createSidebarButton("Data Mahasiswa");

    sidebarPanel.add(btnMenuDashboard);
    sidebarPanel.add(btnMenuInsert);
    sidebarPanel.add(btnMenuView);
  }

  // --- BAGIAN 2: PANEL TENGAH (CONTAINER) ---
  private void initCenterPanel() {
    // CardLayout memungkinkan kita menumpuk panel seperti kartu remi.
    // Kita bisa memilih kartu mana yang mau ditampilkan (DASHBOARD, INSERT, atau VIEW).
    cardLayout = new CardLayout();
    centerPanel = new JPanel(cardLayout);
    centerPanel.setBackground(colorBg);

    // Menambahkan halaman-halaman ke tumpukan kartu
    centerPanel.add(createDashboardPanel(), "DASHBOARD");
    centerPanel.add(createInsertPanel(), "INSERT");
    centerPanel.add(createViewPanel(), "VIEW");
  }

  // --- BAGIAN 3: HALAMAN DASHBOARD (STATISTIK) ---
  private JPanel createDashboardPanel() {
    JPanel panel = new JPanel(new GridLayout(1, 2, 30, 0)); // 1 Baris, 2 Kolom (Kartu)
    panel.setBackground(new Color(33, 33, 33));
    panel.setBorder(new EmptyBorder(40, 40, 40, 40));

    lblTotal = new JLabel("0", SwingConstants.CENTER);
    lblProdiDominan = new JLabel("-", SwingConstants.CENTER);

    // Membuat Kartu Statistik dengan latar belakang gradasi warna
    JPanel cardTotal = createGradientCard("Total Mahasiswa", lblTotal, colorAccent, colorAccentEnd);
    JPanel cardProdi = createGradientCard("Prodi Terbanyak", lblProdiDominan, new Color(45, 45, 45), new Color(60, 60, 60));

    panel.add(cardTotal);
    panel.add(cardProdi);
    return panel;
  }

  // Helper untuk membuat Kartu Gradasi yang cantik
  private JPanel createGradientCard(String title, JLabel valueLabel, Color colorStart, Color colorEnd) {
    JPanel card = new JPanel(new BorderLayout()) {
        // Override paintComponent untuk menggambar warna gradasi custom
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            // Aktifkan Antialiasing agar grafik halus (tidak pecah-pecah)
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, colorStart, getWidth(), getHeight(), colorEnd);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Sudut membulat
            super.paintComponent(g);
        }
    };
    card.setOpaque(false); // Transparan agar bentuk bulat terlihat
    card.setBorder(new EmptyBorder(20, 20, 20, 20));

    // Judul Kartu (Kecil di atas)
    JLabel lblTitle = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblTitle.setForeground(new Color(255, 255, 255, 200));

    // Angka Nilai (Besar di tengah)
    valueLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
    valueLabel.setForeground(Color.WHITE);

    card.add(lblTitle, BorderLayout.NORTH);
    card.add(valueLabel, BorderLayout.CENTER);

    return card;
  }

  // --- BAGIAN 4: HALAMAN INPUT DATA ---
  private JPanel createInsertPanel() {
    // Panel ini menggunakan custom painting untuk menggambar hiasan segitiga hijau di latar belakang.
    JPanel panel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Warna dasar
        g2.setColor(colorBg);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Menggambar bentuk geometris hijau di kiri panel
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, colorAccent, w * 0.6f, h, colorAccentEnd);
        g2.setPaint(gp);
        Polygon poly = new Polygon();
        poly.addPoint(0, 0);
        poly.addPoint(0, h);
        poly.addPoint((int) (w * 0.35), h);
        poly.addPoint((int) (w * 0.75), 0);
        g2.fillPolygon(poly);
      }
    };

    GridBagConstraints gbc = new GridBagConstraints();

    // Judul Besar "INPUT DATA BARU" di sebelah kiri
    JLabel lblTitle = new JLabel("<html><div style='text-align:left;'>INPUT<br>DATA<br>BARU</div></html>");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 48));
    lblTitle.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 0;
    gbc.weightx = 0.4;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 50, 0, 0);
    panel.add(lblTitle, gbc);

    // Formulir Input di sebelah kanan
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setOpaque(false);

    GridBagConstraints fgbc = new GridBagConstraints();
    fgbc.fill = GridBagConstraints.HORIZONTAL;
    fgbc.insets = new Insets(10, 0, 10, 0);
    fgbc.weightx = 1.0;
    fgbc.gridx = 0;

    // Menambah elemen form satu per satu
    addGeometricLabel(formPanel, "NIM", 0, fgbc);
    txtNim = addGeometricTextField(formPanel, 1, fgbc);

    addGeometricLabel(formPanel, "Nama Lengkap", 2, fgbc);
    txtNama = addGeometricTextField(formPanel, 3, fgbc);

    addGeometricLabel(formPanel, "Tanggal Lahir", 4, fgbc);
    dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");
    dateChooser.setBackground(colorBg);
    fgbc.gridy = 5;
    formPanel.add(dateChooser, fgbc);

    addGeometricLabel(formPanel, "Program Studi", 6, fgbc);
    String[] prodiList = {"Sistem Komputer", "Sistem Informasi", "Teknologi Informasi", "Bisnis Digital", "Manajemen Informasi"};
    cbProdi = new JComboBox<>(prodiList);
    fgbc.gridy = 7;
    formPanel.add(cbProdi, fgbc);

    btnSave = createGradientButton("SIMPAN DATA");
    fgbc.gridy = 8;
    fgbc.insets = new Insets(40, 0, 0, 0); // Jarak lebih lebar di atas tombol
    fgbc.fill = GridBagConstraints.NONE;
    fgbc.anchor = GridBagConstraints.EAST; // Tombol rapat kanan
    formPanel.add(btnSave, fgbc);

    // Menempelkan formPanel ke panel utama
    gbc.gridx = 1;
    gbc.weightx = 0.6;
    gbc.insets = new Insets(0, 0, 0, 80);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(formPanel, gbc);

    return panel;
  }

  // --- BAGIAN 5: HALAMAN VIEW (TABEL) ---
  private JPanel createViewPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(new Color(33, 33, 33));
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));

    // Panel Pencarian (Atas)
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.setBackground(new Color(33, 33, 33));
    JLabel lblSearch = new JLabel("Cari Data:");
    lblSearch.setForeground(Color.WHITE);
    txtSearch = new JTextField(20);
    searchPanel.add(lblSearch);
    searchPanel.add(txtSearch);
    panel.add(searchPanel, BorderLayout.NORTH);

    // Tabel Data (Tengah)
    String[] columns = {"NIM", "Nama", "Tgl Lahir", "Prodi"};
    tableModel = new DefaultTableModel(columns, 0);

    // Kustomisasi Tabel agar baris selang-seling warnanya
    table = new JTable(tableModel) {
      @Override
      public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
          c.setBackground(row % 2 == 0 ? new Color(50, 50, 50) : new Color(60, 60, 60)); // Zebra striping
          c.setForeground(Color.WHITE);
        } else {
          c.setBackground(Color.decode("#219e3d")); // Warna saat diklik
          c.setForeground(Color.WHITE);
        }
        return c;
      }
    };

    table.setAutoCreateRowSorter(true); // Fitur urutkan data saat header diklik
    styleTable(table); // Panggil fungsi styling tambahan

    JScrollPane scrollPane = new JScrollPane(table); // Agar bisa discroll
    scrollPane.getViewport().setBackground(new Color(33, 33, 33));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    panel.add(scrollPane, BorderLayout.CENTER);

    // Tombol Aksi Bawah (Edit & Hapus)
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.setBackground(new Color(33, 33, 33));
    btnEdit = createGradientButton("Edit Data");
    btnDelete = createGradientButton("Hapus Data");
    btnPanel.add(btnEdit);
    btnPanel.add(btnDelete);
    panel.add(btnPanel, BorderLayout.SOUTH);

    return panel;
  }

  // --- BAGIAN 6: FUNGSI-FUNGSI HELPER (PEMBANTU) ---

  // Membuat Label form dengan gaya konsisten
  private void addGeometricLabel(JPanel p, String text, int y, GridBagConstraints gbc) {
    JLabel l = new JLabel(text);
    l.setFont(new Font("SansSerif", Font.BOLD, 12));
    l.setForeground(Color.GRAY);
    gbc.gridy = y;
    p.add(l, gbc);
  }

  // Membuat TextField inputan dengan garis bawah saja (tanpa kotak)
  private JTextField addGeometricTextField(JPanel p, int y, GridBagConstraints gbc) {
    JTextField tf = new JTextField();
    tf.setOpaque(false); // Transparan
    tf.setForeground(Color.WHITE);
    tf.setCaretColor(colorAccent); // Warna kursor ketik
    tf.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY), // Garis bawah
        BorderFactory.createEmptyBorder(5, 0, 10, 0) // Padding
    ));
    tf.setFont(new Font("SansSerif", Font.PLAIN, 16));
    gbc.gridy = y;
    p.add(tf, gbc);
    return tf;
  }

  // Membuat tombol sidebar sederhana
  private JButton createSidebarButton(String text) {
    JButton btn = new JButton(text);
    btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
    btn.setForeground(Color.WHITE);
    btn.setBackground(colorSidebar);
    btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Garis pemisah
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Kursor berubah jadi tangan
    return btn;
  }

  // Membuat tombol gradasi hijau yang cantik
  private JButton createGradientButton(String text) {
    JButton btn = new JButton(text) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, colorAccent, 0, getHeight(), colorAccent.darker());
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(g);
      }
    };
    btn.setForeground(Color.WHITE);
    btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    btn.setContentAreaFilled(false);
    btn.setFocusPainted(false);
    btn.setBorder(new EmptyBorder(10, 20, 10, 20));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return btn;
  }

  // Mengatur gaya header tabel
  private void styleTable(JTable t) {
    t.setRowHeight(30);
    t.setShowVerticalLines(false);
    t.setIntercellSpacing(new Dimension(0, 0));
    t.getTableHeader().setBackground(colorSidebar);
    t.getTableHeader().setForeground(colorAccent);
    t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
    t.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, colorAccent));

    // Rata tengah isi tabel
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    t.setDefaultRenderer(String.class, centerRenderer);
  }
}
