package StockWise;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class CategoryPanel extends JPanel {
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;

    public CategoryPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("CATEGORY", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(50, 20, 400, 30);
        add(titleLabel);

        tableModel = new DefaultTableModel(new Object[]{"Category Name", "Status"}, 0);
        categoryTable = new JTable(tableModel);
        categoryTable.setRowHeight(50);
        categoryTable.setShowGrid(false);
        categoryTable.setIntercellSpacing(new Dimension(0, 0));
        categoryTable.setBackground(Color.WHITE);
        categoryTable.setForeground(Color.BLACK);
        categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
        categoryTable.setBorder(null);

        categoryTable.getColumnModel().getColumn(1).setCellRenderer(new StatusColumnRenderer());

        JScrollPane scrollPane = new JScrollPane(categoryTable);
        scrollPane.setBounds(50, 60, 1000, 500);
        scrollPane.setBorder(null);
        add(scrollPane);

        btnAdd = new JButton("ADD CATEGORY");
        btnAdd.setBackground(new Color(33, 150, 243));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBounds(750, 580, 150, 40);
        add(btnAdd);

        btnEdit = new JButton("EDIT");
        btnEdit.setBackground(new Color(33, 150, 243));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setBounds(550, 580, 150, 40);
        add(btnEdit);

        btnDelete = new JButton("DELETE");
        btnDelete.setBackground(new Color(33, 150, 243));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBounds(350, 580, 150, 40);
        add(btnDelete);

        loadCategories();

        btnAdd.addActionListener(e -> showCategoryDialog(null, -1));
        btnEdit.addActionListener(e -> {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow != -1) {
                String category = (String) tableModel.getValueAt(selectedRow, 0);
                String status = (String) tableModel.getValueAt(selectedRow, 1);
                showCategoryDialog(new String[]{category, status}, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                saveCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });
    }

    private void showCategoryDialog(String[] data, int row) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Category", true);
        dialog.setSize(500, 300);
        dialog.setLayout(null);

        JLabel lblCategory = new JLabel("Category Name:");
        lblCategory.setBounds(50, 50, 100, 30);
        dialog.add(lblCategory);

        JTextField txtCategory = new JTextField();
        txtCategory.setBounds(160, 50, 250, 30);
        dialog.add(txtCategory);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(50, 100, 100, 30);
        dialog.add(lblStatus);

        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Active", "Inactive"});
        cmbStatus.setBounds(160, 100, 250, 30);
        dialog.add(cmbStatus);

        JButton btnSave = new JButton("SAVE");
        btnSave.setBounds(160, 180, 100, 40);
        btnSave.setBackground(new Color(33, 150, 243));
        btnSave.setForeground(Color.WHITE);
        dialog.add(btnSave);

        JButton btnClose = new JButton("CLOSE");
        btnClose.setBounds(310, 180, 100, 40);
        btnClose.setBackground(new Color(33, 150, 243));
        btnClose.setForeground(Color.WHITE);
        dialog.add(btnClose);

        if (data != null) {
            txtCategory.setText(data[0]);
            cmbStatus.setSelectedItem(data[1]);
        }

        btnSave.addActionListener(e -> {
            String category = txtCategory.getText();
            String status = (String) cmbStatus.getSelectedItem();
            if (row == -1) {
                tableModel.addRow(new Object[]{category, status});
            } else {
                tableModel.setValueAt(category, row, 0);
                tableModel.setValueAt(status, row, 1);
            }
            saveCategories();
            dialog.dispose();
        });

        btnClose.addActionListener(e -> dialog.dispose());
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadCategories() {
        try (BufferedReader reader = new BufferedReader(new FileReader("categories.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                tableModel.addRow(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCategories() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categories.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.write(tableModel.getValueAt(i, 0) + "|" + tableModel.getValueAt(i, 1));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class StatusColumnRenderer extends DefaultTableCellRenderer {
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
}
