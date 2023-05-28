package presentation;

import businessLayer.ClientBLL;
import businessLayer.OrdersBLL;
import businessLayer.ProductBLL;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code OrdersPanel} class represents the GUI panel for creating orders.
 * It allows the user to enter client ID, product ID, and amount to create an order.
 * The created orders are displayed in a text area.
 *
 * @Author Sarkozi Lorand
 */
public class OrdersPanel extends JFrame {
    private JTextField clientIdTextField;
    private JTextField productIdTextField;
    private JTextField amountTextField;
    private JTextArea ordersTextArea;
    private JButton backButton;
    private List<Orders> ordersList;

    /**
     * Constructs a new instance of the {@code OrdersPanel} class.
     * Initializes the GUI components and sets up the event listeners.
     */
    public OrdersPanel() {
        setLayout(new BorderLayout());
        setSize(800, 200);
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        JLabel clientIdLabel = new JLabel("Client ID:");
        clientIdTextField = new JTextField();
        inputPanel.add(clientIdLabel);
        inputPanel.add(clientIdTextField);

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdTextField = new JTextField();
        inputPanel.add(productIdLabel);
        inputPanel.add(productIdTextField);

        JLabel amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField();
        inputPanel.add(amountLabel);
        inputPanel.add(amountTextField);

        JButton createButton = new JButton("Create");
        inputPanel.add(createButton);

        backButton = new JButton("Back");
        inputPanel.add(backButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        add(inputPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        ordersTextArea = new JTextArea();
        ordersTextArea.setEditable(false);
        scrollPane.setViewportView(ordersTextArea);

        add(scrollPane, BorderLayout.CENTER);

        ordersList = new ArrayList<>();

        displayOrders();
    }

    /**
     * Creates a new order when the create button is clicked.
     * Retrieves the client ID, product ID, and amount from the input fields.
     * Checks if the client and product exist and if there is sufficient quantity of the product.
     * Inserts the order into the database, updates the product quantity, generates a bill, and displays the order.
     * Clears the input fields.
     */
    private void createOrder() {
        int clientId = Integer.parseInt(clientIdTextField.getText());
        int productId = Integer.parseInt(productIdTextField.getText());
        int amount = Integer.parseInt(amountTextField.getText());

        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();

        if (clientBLL.findClientById(clientId) != null && productBLL.findProductById(productId) != null) {
            Product product = productBLL.findProductById(productId);
            if (amount <= product.getAmmount()) {
                Orders order = new Orders(0, clientId, productId, amount);
                OrdersBLL ordersBLL = new OrdersBLL();
                ordersBLL.insertOrder(order);
                JOptionPane.showMessageDialog(this, "Order created successfully.");

                product.setAmmount(product.getAmmount() - amount);
                productBLL.updateProduct(product);

                Bill bill = new Bill(order);
                bill.generateBill();

                ordersList.add(order);
                displayOrders();
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient quantity of the product.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid client ID or product ID.");
        }

        clearInputFields();
    }

    /**
     * Displays the orders in the ordersTextArea.
     * Clears the text area and appends each order's information.
     */
    private void displayOrders() {
        ordersTextArea.setText("");
        for (Orders order : ordersList) {
            ordersTextArea.append(order.toString() + "\n");
        }
    }

    /**
     * Clears the input fields for client ID, product ID, and amount.
     */
    private void clearInputFields() {
        clientIdTextField.setText("");
        productIdTextField.setText("");
        amountTextField.setText("");
    }

    /**
     * Goes back to the main panel when the back button is clicked.
     * Creates an instance of the {@code MainPanel} and makes it visible.
     * Closes the current orders panel.
     */
    private void goBack() {
        MainPanel mainPanel = new MainPanel();
        mainPanel.setVisible(true);
        dispose();
    }
}
