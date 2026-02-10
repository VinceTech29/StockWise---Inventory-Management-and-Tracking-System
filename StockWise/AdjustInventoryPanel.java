package StockWise;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AdjustInventoryPanel extends JPanel {
	private JTable adjustInventoryTable;
	private DefaultTableModel tableModel;
	private JButton refreshButton, editButton, deleteButton;

	public AdjustInventoryPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel titleLabel = new JLabel("ADJUST INVENTORY", SwingConstants.LEFT);
		titleLabel.setBounds(50, 20, 400, 30);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		add(titleLabel);

		String[] columnNames = { "#", "Product Name", "Price", "Quantity", "Category", "Availability" };
		tableModel = new DefaultTableModel(columnNames, 0);
		adjustInventoryTable = new JTable(tableModel);
		adjustInventoryTable.setRowHeight(50);
		adjustInventoryTable.setShowGrid(false);
		adjustInventoryTable.setIntercellSpacing(new Dimension(0, 0));
		adjustInventoryTable.setBackground(Color.WHITE);
		adjustInventoryTable.setForeground(Color.BLACK);
		adjustInventoryTable.setFont(new Font("Arial", Font.BOLD, 14));
		adjustInventoryTable.setBorder(null);
		adjustInventoryTable.getColumnModel().getColumn(5)
		.setCellRenderer(new ProductsPanel.AvailabilityColumnRenderer());

		JScrollPane scrollPane = new JScrollPane(adjustInventoryTable);
		scrollPane.setBounds(50, 70, 1000, 500);
		scrollPane.setBorder(null);
		add(scrollPane);

		refreshButton = new JButton("REFRESH");
		refreshButton.setBounds(900, 580, 150, 40);
		refreshButton.setBackground(new Color(33, 150, 243));
		refreshButton.setForeground(Color.WHITE);
		add(refreshButton);

		editButton = new JButton("EDIT");
		editButton.setBounds(730, 580, 150, 40);
		editButton.setBackground(new Color(33, 150, 243));
		editButton.setForeground(Color.WHITE);
		add(editButton);

		deleteButton = new JButton("DELETE");
		deleteButton.setBounds(560, 580, 150, 40);
		deleteButton.setBackground(new Color(33, 150, 243));
		deleteButton.setForeground(Color.WHITE);
		add(deleteButton);

		refreshButton.addActionListener(e -> loadProducts());

		editButton.addActionListener(e -> {
			int selectedRow = adjustInventoryTable.getSelectedRow();
			if (selectedRow != -1) {
				String productName = (String) tableModel.getValueAt(selectedRow, 1);
				String price = (String) tableModel.getValueAt(selectedRow, 2);
				String quantity = (String) tableModel.getValueAt(selectedRow, 3);
				String category = (String) tableModel.getValueAt(selectedRow, 4);
				String availability = (String) tableModel.getValueAt(selectedRow, 5);

				EditInventoryDialog editDialog = new EditInventoryDialog(productName, price, quantity, category,
						availability, selectedRow);
				editDialog.setVisible(true);
				loadProducts();
			} else {
				JOptionPane.showMessageDialog(null, "Please select a product to edit.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		deleteButton.addActionListener(e -> {
			int selectedRow = adjustInventoryTable.getSelectedRow();
			if (selectedRow != -1) {
				String productNameToDelete = (String) tableModel.getValueAt(selectedRow, 1);

				tableModel.removeRow(selectedRow);
				deleteProductFromFile(productNameToDelete);
				JOptionPane.showMessageDialog(null, "Product Deleted Successfully!");
			} else {
				JOptionPane.showMessageDialog(null, "Please select a product to delete.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		loadProducts();
	}

	private void loadProducts() {
		tableModel.setRowCount(0);
		int itemNumber = 1;
		try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\|");
				if (data.length == 6) {
					tableModel.addRow(
							new Object[] { String.valueOf(itemNumber), data[1], data[2], data[3], data[4], data[5] });
					itemNumber++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading products!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteProductFromFile(String productNameToDelete) {
		File inputFile = new File("products.txt");
		File tempFile = new File("temp_products.txt");

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split("\\|");
				if (data.length == 6 && !data[1].equals(productNameToDelete)) {
					writer.write(line + System.lineSeparator());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error deleting product!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (inputFile.delete()) {
			tempFile.renameTo(inputFile);
		} else {
			JOptionPane.showMessageDialog(this, "Error updating inventory file!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private class EditInventoryDialog extends JDialog { 
		private JTextField nameField, priceField, quantityField;
		private JComboBox<String> categoryBox, availabilityBox;
		private JButton saveButton, cancelButton;
		private int selectedRow;

		public EditInventoryDialog(String productName, String price, String quantity, String category,
				String availability, int selectedRow) {
			this.selectedRow = selectedRow; // Store the selected row index
			setTitle("EDIT INVENTORY");
			setSize(400, 350);
			setLocationRelativeTo(null);
			setLayout(null);
			getContentPane().setBackground(Color.WHITE);
			setModal(true);

			// Title Label
			JLabel titleLabel = new JLabel("Edit Inventory");
			titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
			titleLabel.setBounds(140, 10, 200, 30);
			add(titleLabel);

			// Product Name
			JLabel nameLabel = new JLabel("Product Name:");
			nameLabel.setBounds(30, 50, 100, 25);
			add(nameLabel);
			nameField = new JTextField(productName);
			nameField.setBounds(140, 50, 200, 25);
			add(nameField);

			// Price
			JLabel priceLabel = new JLabel("Price:");
			priceLabel.setBounds(30, 90, 100, 25);
			add(priceLabel);
			priceField = new JTextField(price);
			priceField.setBounds(140, 90, 200, 25);
			add(priceField);

			// Quantity
			JLabel quantityLabel = new JLabel("Quantity:");
			quantityLabel.setBounds(30, 130, 100, 25);
			add(quantityLabel);
			quantityField = new JTextField(quantity);
			quantityField.setBounds(140, 130, 200, 25);
			add(quantityField);

			// Category
			JLabel categoryLabel = new JLabel("Category:");
			categoryLabel.setBounds(30, 170, 100, 25);
			add(categoryLabel);
			categoryBox = new JComboBox<>();
			loadCategories();
			categoryBox.setSelectedItem(category);
			categoryBox.setBounds(140, 170, 200, 25);
			add(categoryBox);

			// Availability
			JLabel availabilityLabel = new JLabel("Availability:");
			availabilityLabel.setBounds(30, 210, 100, 25);
			add(availabilityLabel);
			availabilityBox = new JComboBox<>(new String[]{"Yes", "No"});
			availabilityBox.setSelectedItem(availability);
			availabilityBox.setBounds(140, 210, 200, 25);
			add(availabilityBox);

			// Save Button
			saveButton = new JButton("SAVE");
			saveButton.setBackground(new Color(33, 150, 243));
			saveButton.setForeground(Color.WHITE);
			saveButton.setBounds(80, 260, 100, 30);
			add(saveButton);

			// Cancel Button
			cancelButton = new JButton("CANCEL");
			cancelButton.setBackground(new Color(255, 0, 0)); // Red color for cancel
			cancelButton.setForeground(Color.WHITE);
			cancelButton.setBounds(220, 260, 100, 30);
			add(cancelButton);

			// Action Listeners
			saveButton.addActionListener(e -> {
				String updatedName = nameField.getText();
				String updatedPrice = priceField.getText();
				String updatedQuantity = quantityField.getText();
				String updatedCategory = (String) categoryBox.getSelectedItem();
				String updatedAvailability = (String) availabilityBox.getSelectedItem();

				if (updatedAvailability.equals("Yes")) {
					updatedAvailability = "Active";
				} else {
					updatedAvailability = "Inactive";
				}

				// Update the table model
				tableModel.setValueAt(updatedName, selectedRow, 1);
				tableModel.setValueAt(updatedPrice, selectedRow, 2);
				tableModel.setValueAt(updatedQuantity, selectedRow, 3);
				tableModel.setValueAt(updatedCategory, selectedRow, 4);
				tableModel.setValueAt(updatedAvailability, selectedRow, 5);

				// Save the updated data to the file
				saveUpdatedProductToFile(selectedRow, updatedName, updatedPrice, updatedQuantity, updatedCategory, updatedAvailability);
				dispose();
			});

			cancelButton.addActionListener(e -> dispose());
		}

		private void loadCategories() {
			try (BufferedReader reader = new BufferedReader(new FileReader("categories.txt"))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split("\\|");
					if (parts.length > 0) {
						categoryBox.addItem(parts[0].trim()); 
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	

		private void saveUpdatedProductToFile(int rowIndex, String name, String price, String quantity, String category, String availability) {
			File inputFile = new File("products.txt");
			File tempFile = new File("temp_products.txt");

			try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

				String line;
				int currentRow = 0;
				while ((line = reader.readLine()) != null) {
					if (currentRow == rowIndex) {
						// Write the updated product information
						writer.write(String.join("|", String.valueOf(currentRow + 1), name, price, quantity, category, availability) + System.lineSeparator());
					} else {
						writer.write(line + System.lineSeparator());
					}
					currentRow++;
				}
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error saving updated product!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Replace the old file with the updated one
			if (inputFile.delete()) {
				tempFile.renameTo(inputFile);
			} else {
				JOptionPane.showMessageDialog(this, "Error updating inventory file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}