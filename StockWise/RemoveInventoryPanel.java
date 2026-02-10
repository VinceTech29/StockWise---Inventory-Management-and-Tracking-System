package StockWise;

import javax.swing.*;
import java.awt.*;

public class RemoveInventoryPanel extends JPanel {
    public RemoveInventoryPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel infoLabel = new JLabel("Remove Inventory Section", SwingConstants.CENTER);
        infoLabel.setBounds(50, 50, 400, 50);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(infoLabel);
    }
}
 