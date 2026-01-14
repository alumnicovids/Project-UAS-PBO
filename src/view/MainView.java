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
    private final Color colorAccent = Color.decode("#219e3d");
    private final Color colorText = Color.WHITE;

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

    private void initCenterPanel() {
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBackground(colorBg);

        centerPanel.add(createDashboardPanel(), "DASHBOARD");
        centerPanel.add(createInsertPanel(), "INSERT");
        centerPanel.add(createViewPanel(), "VIEW");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(new Color(33, 33, 33));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        lblTotal = createStatCard("Total Mahasiswa", "0");
        lblProdiDominan = createStatCard("Prodi Terbanyak", "-");

        panel.add(lblTotal);
        panel.add(lblProdiDominan);
        return panel;
    }

    private JLabel createStatCard(String title, String value) {
        JLabel lbl = new JLabel("<html><center>" + title + "<br/><br/><span style='font-size:24px'>" + value + "</span></center></html>", SwingConstants.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(45, 45, 45));
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createLineBorder(Color.decode("#219e3d"), 2));
        return lbl;
    }

    private JPanel createInsertPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(colorBg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabel(panel, "NIM", 0, 0, gbc);
        txtNim = addRoundedTextField(panel, 1, 0, gbc);

        addLabel(panel, "Nama Lengkap", 0, 1, gbc);
        txtNama = addRoundedTextField(panel, 1, 1, gbc);

        addLabel(panel, "Tanggal Lahir", 0, 2, gbc);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(dateChooser, gbc);

        addLabel(panel, "Program Studi", 0, 3, gbc);
        String[] prodiList = {"Sistem Komputer", "Sistem Informasi", "Teknologi Informasi", "Bisnis Digital", "Manajemen Informasi"};
        cbProdi = new JComboBox<>(prodiList);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(cbProdi, gbc);

        btnSave = createGradientButton("Simpan Data");
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnSave, gbc);

        return panel;
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

    private JTextField addRoundedTextField(JPanel p, int x, int y, GridBagConstraints gbc) {
        JTextField tf = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        tf.setOpaque(false);
        tf.setBorder(new EmptyBorder(5, 10, 5, 10));
        tf.setForeground(Color.BLACK);
        gbc.gridx = x; gbc.gridy = y;
        p.add(tf, gbc);
        return tf;
    }

    private void addLabel(JPanel p, String text, int x, int y, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(colorText);
        gbc.gridx = x; gbc.gridy = y;
        p.add(label, gbc);
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
}
