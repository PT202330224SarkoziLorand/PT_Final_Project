package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code MainPanel} class represents the main GUI panel of the application.
 * It provides buttons to navigate to different panels: Client, Product, and Orders.
 *
 * @Author Sarkozi Lorand
 */
public class MainPanel extends JFrame {
    private JButton clientButton;
    private JButton productButton;
    private JButton ordersButton;

    /**
     * Constructs a new instance of the {@code MainPanel} class.
     * Initializes the GUI components and sets up the event listeners.
     */
    public MainPanel() {
        setTitle("Main Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 200);
        setLocationRelativeTo(null);

        clientButton = new JButton("Client");
        productButton = new JButton("Product");
        ordersButton = new JButton("Orders");

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientPanel();
            }
        });

        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductPanel();
            }
        });

        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrdersPanel();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(clientButton);
        panel.add(productButton);
        panel.add(ordersButton);

        getContentPane().add(panel);
    }

    /**
     * Opens the client panel when the client button is clicked.
     */
    private void openClientPanel() {
        ClientPanel clientPanel = new ClientPanel();
        clientPanel.setVisible(true);
        revalidate();
        repaint();
        dispose();
    }

    /**
     * Opens the product panel when the product button is clicked.
     */
    private void openProductPanel() {
        ProductPanel productPanel = new ProductPanel();
        productPanel.setVisible(true);
        revalidate();
        repaint();
        dispose();
    }

    /**
     * Opens the orders panel when the orders button is clicked.
     */
    private void openOrdersPanel() {
        OrdersPanel ordersPanel = new OrdersPanel();
        ordersPanel.setVisible(true);
        revalidate();
        repaint();
        dispose();
    }

    /**
     * The entry point of the application.
     * Creates an instance of the {@code MainPanel} and makes it visible.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainPanel mainPanel = new MainPanel();
                mainPanel.setVisible(true);
            }
        });
    }
}
