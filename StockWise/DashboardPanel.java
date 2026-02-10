package StockWise;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel availProdLabel, outProdLabel;
    private ProductsPanel productsPanel;
    private BarChartPanel barChartPanel;
    private MonthlyTrendsChart monthlyTrendsChart;

    public DashboardPanel(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setBounds(50, 20, 400, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        add(titleLabel);

        JPanel availProd = new JPanel();
        availProd.setBounds(50, 100, 500, 200);
        availProd.setBackground(new Color(224, 229, 255));
        availProd.setLayout(null);
        add(availProd);

        JLabel availTitle = new JLabel("Total Quantity of Available Products");
        availTitle.setBounds(20, 20, 300, 30);
        availTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        availTitle.setForeground(Color.DARK_GRAY);
        availProd.add(availTitle);

        availProdLabel = new JLabel(productsPanel.getTotalQuantityAvailable() + " Items");
        availProdLabel.setBounds(20, 100, 400, 50);
        availProdLabel.setFont(new Font("Arial", Font.BOLD, 30));
        availProd.add(availProdLabel);

        JPanel outProd = new JPanel();
        outProd.setBounds(600, 100, 500, 200);
        outProd.setBackground(new Color(224, 229, 255));
        outProd.setLayout(null);
        add(outProd);

        JLabel outTitle = new JLabel("Total Out of Stock Products");
        outTitle.setBounds(20, 20, 300, 30);
        outTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        outTitle.setForeground(Color.DARK_GRAY);
        outProd.add(outTitle);

        outProdLabel = new JLabel(productsPanel.getTotalQuantityOutOfStock() + " Products");
        outProdLabel.setBounds(20, 100, 400, 50);
        outProdLabel.setFont(new Font("Arial", Font.BOLD, 30));
        outProd.add(outProdLabel);

        barChartPanel = new BarChartPanel();
        barChartPanel.setBounds(50, 350, 400, 300);
        add(barChartPanel);

        monthlyTrendsChart = new MonthlyTrendsChart();
        monthlyTrendsChart.setBounds(500, 350, 650, 300);
        add(monthlyTrendsChart);
    }

    public void setProductsPanel(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;
    }

    public void refreshDashboard() {
        if (productsPanel != null) {
            availProdLabel.setText(productsPanel.getTotalQuantityAvailable() + " Items");
            outProdLabel.setText(productsPanel.getTotalQuantityOutOfStock() + " Products");
            barChartPanel.repaint();
            monthlyTrendsChart.repaint();
        }
    }

    private class BarChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int totalAvailable = productsPanel.getTotalQuantityAvailable();
            int totalOutOfStock = productsPanel.getTotalQuantityOutOfStock();
            int maxValue = Math.max(totalAvailable, totalOutOfStock);

            if (maxValue == 0) return;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int barWidth = 100;
            int chartHeight = 200;
            int xStart = 100;
            int yBase = 250;

            for (int i = 0; i <= 5; i++) {
                int y = yBase - (i * chartHeight / 5);
                g2d.setColor(Color.GRAY);
                g2d.drawLine(80, y, 300, y);
                g2d.drawString(String.valueOf(i * maxValue / 5), 50, y);
            }

            int availBarHeight = (int) ((double) totalAvailable / maxValue * chartHeight);
            int outBarHeight = (int) ((double) totalOutOfStock / maxValue * chartHeight);

            g2d.setColor(new Color(15, 107, 174));
            g2d.fillRect(xStart, yBase - availBarHeight, barWidth, availBarHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Available", xStart + 10, yBase + 20);

            g2d.setColor(new Color(36, 139, 214));
            g2d.fillRect(xStart + 150, yBase - outBarHeight, barWidth, outBarHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Out of Stock", xStart + 150 + 10, yBase + 20);
        }
    }

    private class MonthlyTrendsChart extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int yBase = 250;
            for (int i = 0; i <= 5; i++) {
                int y = yBase - (i * 40);
                g2d.setColor(Color.GRAY);
                g2d.drawLine(30, y, 650, y);
                g2d.drawString(String.valueOf(i * 20), 10, y);
            }

            String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            int[] availableData = {90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35};
            int[] outOfStockData = {10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};

            int xStart = 50;
            int barWidth = 30;
            int spacing = 50;

            for (int i = 0; i < months.length; i++) {
                int availHeight = availableData[i] * 2;
                int outHeight = outOfStockData[i] * 2;

                g2d.setColor(new Color(15, 107, 174));
                g2d.fillRect(xStart + (i * spacing), yBase - availHeight, barWidth, availHeight);
                g2d.setColor(new Color(36, 139, 214));
                g2d.fillRect(xStart + (i * spacing) + barWidth + 5, yBase - outHeight, barWidth, outHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawString(months[i], xStart + (i * spacing) + 10, yBase + 20);
            }
        }
    }
}
