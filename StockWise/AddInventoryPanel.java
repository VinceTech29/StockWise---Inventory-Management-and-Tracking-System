package StockWise;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddInventoryPanel extends JPanel {
    private JTextField productNameField, priceField, quantityField;
    private JComboBox<String> categoryCombo, availabilityCombo;
    private JButton refreshButton;

    public AddInventoryPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("ADD INVENTORY", SwingConstants.LEFT);
        titleLabel.setBounds(50, 20, 1000, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(50, 80, 200, 30);
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(productNameLabel);

        productNameField = new JTextField();
        productNameField.setBounds(50, 110, 1000, 40);
        add(productNameField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 170, 200, 30);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(50, 200, 1000, 40);
        add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 260, 200, 30);
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(50, 290, 1000, 40);
        add(quantityField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 350, 200, 30);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(categoryLabel);

        categoryCombo = new JComboBox<>();
        categoryCombo.setBounds(50, 380, 1000, 40);
        add(categoryCombo);
        loadCategories();

        JLabel availabilityLabel = new JLabel("Availability:");
        availabilityLabel.setBounds(50, 440, 200, 30);
        availabilityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(availabilityLabel);

        String[] availability = {"Yes", "No"};
        availabilityCombo = new JComboBox<>(availability);
        availabilityCombo.setBounds(50, 470, 1000, 40);
        add(availabilityCombo);

        JButton addButton = new JButton("ADD PRODUCT");
        addButton.setBounds(850, 540, 200, 50);
        addButton.setBackground(new Color(33, 150, 243));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(addButton);

        addButton.addActionListener(e-> {
        	
        	
        	try{
        		saveProduct();
        		
        	}catch (Exception ex) {
        		JOptionPane.showMessageDialog(null, "Unexpected erro occured!", "Error", JOptionPane.ERROR_MESSAGE);
        		ex.printStackTrace();
        	}
        });
         

        refreshButton = new JButton("REFRESH");
        refreshButton.setBounds(630, 540, 200, 50);
        refreshButton.setBackground(new Color(33, 150, 243));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(refreshButton);

        refreshButton.addActionListener(e -> {
            try {
                categoryCombo.removeAllItems();
                loadCategories();
                JOptionPane.showMessageDialog(null, "Categories Refreshed Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error refreshing categories!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    private void loadCategories() {
        try (BufferedReader br = new BufferedReader(new FileReader("categories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length > 0) {
                    categoryCombo.addItem(data[0]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading categories!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveProduct() throws Exception {
        String productName = productNameField.getText().trim();
        String price = priceField.getText().trim();
        String quantity = quantityField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        String availability = (String) availabilityCombo.getSelectedItem();
        String status = availability.equals("Yes") ? "Active" : "Inactive";

        if (productName.isEmpty() || price.isEmpty() || quantity.isEmpty() || category == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Double.parseDouble(price);
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price or quantity format!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String data = "1001|" + productName + "|" + price + "|" + quantity + "|" + category + "|" + status;

        try (FileWriter fw = new FileWriter("products.txt", true)) {
            fw.write(data + "\n");
            JOptionPane.showMessageDialog(null, "Product added successfully!");
            clearFields();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error adding product!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        productNameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        if (categoryCombo.getItemCount() > 0) {
            categoryCombo.setSelectedIndex(0);
        }
        availabilityCombo.setSelectedIndex(0);
    }
}
