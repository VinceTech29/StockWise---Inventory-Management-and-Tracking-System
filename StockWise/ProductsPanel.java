package StockWise;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductsPanel extends JPanel {
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private DashboardPanel dashboardPanel;

    public ProductsPanel(DashboardPanel dashboardPanel) {
        this.dashboardPanel = dashboardPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("PRODUCTS RECORD", SwingConstants.LEFT);
        titleLabel.setBounds(50, 20, 400, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        String[] columns = {"Item No.", "Product Name", "Price", "Quantity", "Category", "Availability"};
        tableModel = new DefaultTableModel(columns, 0);
        productsTable = new JTable(tableModel);
        productsTable.setRowHeight(50);
        productsTable.setShowGrid(false);
        productsTable.setIntercellSpacing(new Dimension(0, 0));
        productsTable.setBackground(Color.WHITE);
        productsTable.setForeground(Color.BLACK);
        productsTable.setFont(new Font("Arial", Font.BOLD, 14));
        productsTable.setBorder(null);
        productsTable.getColumnModel().getColumn(5).setCellRenderer(new AvailabilityColumnRenderer());


        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setBounds(50, 70, 1000, 500);
        scrollPane.setBorder(null);
        add(scrollPane);

        refreshButton = new JButton("REFRESH");
        refreshButton.setBounds(900, 580, 150, 40);
        refreshButton.setBackground(new Color(33, 150, 243));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setIcon(new ImageIcon("refresh-icon.png"));
        refreshButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        refreshButton.setIconTextGap(10);
        add(refreshButton);

        refreshButton.addActionListener(e -> {
            loadProducts();
            if (dashboardPanel != null) {
                dashboardPanel.refreshDashboard();
            } else {
                System.out.println("DashboardPanel instance is null! Check initialization.");
            }

        });

        loadProducts();
    }

    public ProductsPanel() {
        this(null);
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        int itemNumber = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 6) {
                    String[] row = {String.valueOf(itemNumber), data[1], data[2], data[3], data[4], data[5]};
                    tableModel.addRow(row);
                    itemNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class AvailabilityColumnRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                if (value.equals("Active")) {
                    c.setForeground(Color.GREEN);
                } else if (value.equals("Inactive")) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
            }
            return c;
        }
    }

    public int getTotalQuantityAvailable() {
        int total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int quantity = Integer.parseInt((String) tableModel.getValueAt(i, 3));
            String availability = (String) tableModel.getValueAt(i, 5);
            if (availability.equals("Active")) {
                total += quantity;
            }
        }
        return total;
    }

    public int getTotalQuantityOutOfStock() {
        int total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int quantity = Integer.parseInt((String) tableModel.getValueAt(i, 3));
            String availability = (String) tableModel.getValueAt(i, 5);
            if (availability.equals("Inactive")) {
                total += quantity;
            }
        }
        return total;
    }
}
