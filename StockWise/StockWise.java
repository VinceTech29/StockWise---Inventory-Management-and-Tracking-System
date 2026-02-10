package StockWise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StockWise {
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        JFrame frame = new JFrame("Login Form");
        frame.setBounds(0, 0, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0x0F6BAE));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(500, 500));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);
//    	loginPanel.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/dashboard-monitor.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
   	

        ImageIcon icon = new ImageIcon("boxes.png");
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(280, 280, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
    

        JLabel logoLabel = new JLabel(icon);
        logoLabel.setBounds(110, 10, 280, 280);
        logoLabel.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/Screenshot_2025-03-13_095554-removebg-preview.png")).getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH)));
        loginPanel.add(logoLabel);

        JTextField adminField = new JTextField("Admin");
        adminField.setBounds(100, 250, 300, 35);
        adminField.setForeground(Color.GRAY);
        addPlaceholderEffect(adminField, "Admin");

        JPasswordField passwordField = new JPasswordField("Password");
        passwordField.setBounds(100, 300, 300, 35);
        passwordField.setForeground(Color.GRAY);
        addPlaceholderEffect(passwordField, "Password");

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(195, 350, 100, 35);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Verdana", Font.BOLD, 20));
        loginButton.setBackground(new Color(0x0248BD6));
        loginButton.setBorder(BorderFactory.createEmptyBorder());

        loginPanel.add(adminField);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        frame.add(loginPanel, gbc);

        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String admin = adminField.getText();
            String pass = String.valueOf(passwordField.getPassword());

            if (admin.equals("admin") && pass.equals("12345")) {
                frame.dispose();
                openDashboard();
            } else {
                JOptionPane.showMessageDialog(null, "Please enter correct user and password!");
            }
        });
    }
 
    private static void openDashboard() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        JFrame dashboardFrame = new JFrame("Dashboard");
        dashboardFrame.setBounds(0, 0, width, height);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLayout(null);
        dashboardFrame.getContentPane().setBackground(Color.decode("#F5F5F5"));

        int panelMargin = 20;

        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(panelMargin, panelMargin, width - 2 * panelMargin, 150);
        headerPanel.setBackground(new Color(0xA5B4FC));
        dashboardFrame.add(headerPanel);

        JPanel sidePanel = new JPanel();
        sidePanel.setBounds(panelMargin, 180, 220, height - 220);
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setLayout(new GridLayout(6, 1, 10, 10));
        dashboardFrame.add(sidePanel);

        ProductsPanel productsPanel = new ProductsPanel();
        DashboardPanel dashboardPanel = new DashboardPanel(productsPanel);
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(panelMargin + 240, 180, width - 280, height - 200);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(dashboardPanel, BorderLayout.CENTER);
        dashboardFrame.add(mainPanel);

        String[] buttonLabels = {"DASHBOARD", "PRODUCTS", "CATEGORY", "ADD INVENTORY", "ADJUST INVENTORY", "ADJUST INVENTORY"};
        JPanel[] panels = {dashboardPanel, productsPanel, new CategoryPanel(), new AddInventoryPanel(),
                 new AdjustInventoryPanel(), new RemoveInventoryPanel()};
        

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            int index = i;
            
            if(i == 0) {
            	button.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/dashboard-monitor.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            	 button.setFocusPainted(false);
            }else if(i == 1) {
            	button.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/boxes.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            	 button.setFocusPainted(false);
            }else if(i==2) {
            	button.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/category.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            	 button.setFocusPainted(false);
            }else if(i==3) {
            	button.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/square-plus.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            	 button.setFocusPainted(false);
            }else if(i==4) {
         
            	button.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/edit.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            	 button.setFocusPainted(false);
            	 
            }else if(i==5) {
            	button.setIcon(new ImageIcon(new ImageIcon(StockWise.class.getResource("/edit.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            	 button.setFocusPainted(false);
            	 button.setVisible(false);
            	
            }
            
           
            
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Verdana", Font.BOLD, 16));
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(new Color(0xD6E0FF));
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    if (!button.getBackground().equals(new Color(0xA5B4FC))) {
                        button.setBackground(Color.WHITE);
                    }
                }
            });

            button.addActionListener(e -> {
                for (Component comp : sidePanel.getComponents()) {
                    if (comp instanceof JButton) {
                        comp.setBackground(Color.WHITE);
                    }
                }

                button.setBackground(new Color(0xA5B4FC));
                mainPanel.removeAll();
                mainPanel.add(panels[index], BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();

                if (panels[index] instanceof DashboardPanel) {
                    ((DashboardPanel) panels[index]).refreshDashboard();
                }
            });

            sidePanel.add(button);
        }

        dashboardFrame.setVisible(true);
    }

    private static void addPlaceholderEffect(JTextField field, String placeholder) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
}
