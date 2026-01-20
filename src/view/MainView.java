package view;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainView extends JFrame {
  public JButton btnMenuDashboard;
  public JDateChooser dateChooser;
  public JComboBox<String> cbProdi;
  public JLabel lblTotal, lblProdiDominan;

  public JTextField txtSearch;
  public CardLayout cardLayout;
  public JPanel centerPanel;
  public JPanel sidebarPanel;

  public JButton btnMenuInsert;
  public JButton btnMenuView;

  public JTextField txtNim, txtNama, txtTanggalLahir, txtProdi;
  public JButton btnSave, btnEdit, btnDelete;

  public JTable table;
  public DefaultTableModel tableModel;

  private final Color colorBg = new Color(33, 33, 33);
  private final Color colorSidebar = new Color(45, 45, 45);
  private final Color colorAccent = Color.decode("#2ecc71");
  private final Color colorAccentEnd = Color.decode("#1abc9c");

  public MainView() {
    setTitle("Project UAS - Dashboard Mahasiswa");
    setSize(1000, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    initSidebar();
    initCenterPanel();

    add(sidebarPanel, BorderLayout.WEST);
    add(centerPanel, BorderLayout.CENTER);
  }

  private void initCenterPanel() {
    cardLayout = new CardLayout();
    centerPanel = new JPanel(cardLayout);
    centerPanel.setBackground(colorBg);

    centerPanel.add(createDashboardPanel(), "DASHBOARD");
    centerPanel.add(createInsertPanel(), "INSERT");
    centerPanel.add(createViewPanel(), "VIEW");
  }

  private void initSidebar() {
    sidebarPanel = new JPanel();
    sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
    sidebarPanel.setBackground(colorSidebar);
    sidebarPanel.setLayout(new GridLayout(10, 1, 0, 10));
    sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

    JLabel lblTitle = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
    lblTitle.setForeground(colorAccent);
    sidebarPanel.add(lblTitle);

    btnMenuDashboard = createSidebarButton("Dashboard");
    btnMenuInsert = createSidebarButton("Input Data");
    btnMenuView = createSidebarButton("Data Mahasiswa");

    sidebarPanel.add(btnMenuDashboard);
    sidebarPanel.add(btnMenuInsert);
    sidebarPanel.add(btnMenuView);
  }

  private JButton createSidebarButton(String text) {
    JButton btn = new JButton(text);
    btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
    btn.setForeground(Color.WHITE);
    btn.setBackground(colorSidebar);
    btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return btn;
  }

  private JPanel createDashboardPanel() {
    JPanel panel = new JPanel(new GridLayout(1, 2, 30, 0));
    panel.setBackground(new Color(33, 33, 33));
    panel.setBorder(new EmptyBorder(40, 40, 40, 40));

    lblTotal = new JLabel("0", SwingConstants.CENTER);
    lblProdiDominan = new JLabel("-", SwingConstants.CENTER);

    JPanel cardTotal = createGradientCard("Total Mahasiswa", lblTotal, colorAccent, colorAccentEnd);
    JPanel cardProdi = createGradientCard("Prodi Terbanyak", lblProdiDominan, new Color(45, 45, 45), new Color(60, 60, 60));

    panel.add(cardTotal);
    panel.add(cardProdi);
    return panel;
  }

  private JPanel createGradientCard(String title, JLabel valueLabel, Color colorStart, Color colorEnd) {
    JPanel card = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, colorStart, getWidth(), getHeight(), colorEnd);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        super.paintComponent(g);
      }
    };
    card.setOpaque(false);
    card.setBorder(new EmptyBorder(20, 20, 20, 20));

    JLabel lblTitle = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblTitle.setForeground(new Color(255, 255, 255, 200));

    valueLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
    valueLabel.setForeground(Color.WHITE);

    card.add(lblTitle, BorderLayout.NORTH);
    card.add(valueLabel, BorderLayout.CENTER);

    return card;
  }

  private JPanel createInsertPanel() {
    JPanel panel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(colorBg);
        g2.fillRect(0, 0, getWidth(), getHeight());

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

    JLabel lblTitle = new JLabel("<html><div style='text-align:left;'>INPUT<br>DATA<br>BARU</div></html>");
    lblTitle.setFont(new Font("SansSerif", Font.BOLD, 48));
    lblTitle.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 0;
    gbc.weightx = 0.4;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 50, 0, 0);
    panel.add(lblTitle, gbc);

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setOpaque(false);

    GridBagConstraints fgbc = new GridBagConstraints();
    fgbc.fill = GridBagConstraints.HORIZONTAL;
    fgbc.insets = new Insets(10, 0, 10, 0);
    fgbc.weightx = 1.0;
    fgbc.gridx = 0;

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
    fgbc.insets = new Insets(40, 0, 0, 0);
    fgbc.fill = GridBagConstraints.NONE;
    fgbc.anchor = GridBagConstraints.EAST;
    formPanel.add(btnSave, fgbc);

    gbc.gridx = 1;
    gbc.weightx = 0.6;
    gbc.insets = new Insets(0, 0, 0, 80);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(formPanel, gbc);

    return panel;
  }

  private void addGeometricLabel(JPanel p, String text, int y, GridBagConstraints gbc) {
    JLabel l = new JLabel(text);
    l.setFont(new Font("SansSerif", Font.BOLD, 12));
    l.setForeground(Color.GRAY);
    gbc.gridy = y;
    p.add(l, gbc);
  }

  private JTextField addGeometricTextField(JPanel p, int y, GridBagConstraints gbc) {
    JTextField tf = new JTextField();
    tf.setOpaque(false);
    tf.setForeground(Color.WHITE);
    tf.setCaretColor(colorAccent);
    tf.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY),
      BorderFactory.createEmptyBorder(5, 0, 10, 0)
    ));
    tf.setFont(new Font("SansSerif", Font.PLAIN, 16));
    gbc.gridy = y;
    p.add(tf, gbc);
    return tf;
  }

  private JPanel createViewPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(new Color(33, 33, 33));
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.setBackground(new Color(33, 33, 33));
    JLabel lblSearch = new JLabel("Cari Data:");
    lblSearch.setForeground(Color.WHITE);
    txtSearch = new JTextField(20);
    searchPanel.add(lblSearch);
    searchPanel.add(txtSearch);
    panel.add(searchPanel, BorderLayout.NORTH);

    String[] columns = {"NIM", "Nama", "Tgl Lahir", "Prodi"};
    tableModel = new DefaultTableModel(columns, 0);

    table = new JTable(tableModel) {
      @Override
      public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
          c.setBackground(row % 2 == 0 ? new Color(50, 50, 50) : new Color(60, 60, 60));
          c.setForeground(Color.WHITE);
        } else {
          c.setBackground(Color.decode("#219e3d"));
          c.setForeground(Color.WHITE);
        }
        return c;
      }
    };

    table.setAutoCreateRowSorter(true);
    styleTable(table);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.getViewport().setBackground(new Color(33, 33, 33));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.setBackground(new Color(33, 33, 33));
    btnEdit = createGradientButton("Edit Data");
    btnDelete = createGradientButton("Hapus Data");
    btnPanel.add(btnEdit);
    btnPanel.add(btnDelete);
    panel.add(btnPanel, BorderLayout.SOUTH);

    return panel;
  }

  private void styleTable(JTable t) {
    t.setRowHeight(30);
    t.setShowVerticalLines(false);
    t.setIntercellSpacing(new Dimension(0, 0));
    t.getTableHeader().setBackground(colorSidebar);
    t.getTableHeader().setForeground(colorAccent);
    t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
    t.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, colorAccent));

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    t.setDefaultRenderer(String.class, centerRenderer);
  }

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
}
