package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import businessLayer.ClientBLL;
import dataAccessLayer.ClientDAO;
import model.Client;
import model.Product;
import dataAccessLayer.ProductDAO;
import businessLayer.ProductBLL;

/**
 * The {@code ProductPanel} class represents the GUI panel for managing products.
 * It displays a table with product information and provides functionality to add, delete, and update products.
 *
 * @Author Sarkozi Lorand
 */
public class ProductPanel extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField nameTextField;
    private JTextField amountTextField;
    private JTextField idTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton backButton;
    private List<Product> productList;

    /**
     * Constructs a new instance of the {@code ProductPanel} class.
     * Initializes the GUI components, sets up the table, input fields, and buttons.
     * Retrieves the product data from the database and updates the table model.
     */
    public ProductPanel() {
        setLayout(new BorderLayout());
        setSize(800, 300);
        productList = new ArrayList<>();
        productList.addAll(new ProductDAO().findAll());
        initTable();
        initInputFields();
        initButtons();

        updateTableModel();
    }

    /**
     * Initializes the table component and sets up the table model.
     * Uses reflection to retrieve the properties of the product objects and create the table model.
     * Adds the table to a scroll pane and sets it as the center component of the panel.
     */
    private void initTable() {
        tableModel = RefelectionExample.retrieveProperties(productList);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Initializes the input fields for ID, name, and amount.
     * Adds the input fields to a panel and sets it as the west component of the panel.
     */
    private void initInputFields() {
        JPanel inputPanel = new JPanel(new GridLayout(6, 1));

        JLabel idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField();

        inputPanel.add(idLabel);
        inputPanel.add(idTextField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountTextField);

        add(inputPanel, BorderLayout.WEST);
    }

    /**
     * Initializes the buttons for add, delete, update, and back.
     * Adds the buttons to a panel and sets it as the north component of the panel.
     * Sets up the event listeners for each button.
     */
    private void initButtons() {
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        backButton = new JButton("Back");

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(backButton, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
    }

    /**
     * Adds a new product based on the input field values.
     * Creates a new {@code Product} object and inserts it into the database.
     * If the insertion is successful, updates the table model and clears the input fields.
     */
    private void addProduct() {
        String name = nameTextField.getText();
        int amount = Integer.parseInt(amountTextField.getText());

        Product product = new Product(name, amount);
        ProductBLL productBLL = new ProductBLL();

        int insertedId = productBLL.insertProduct(product);
        if (insertedId != -1) {
            product.setId(insertedId);
            productList.add(product);
            updateTableModel();
            clearInputFields();
        }
    }

    /**
     * Deletes a product based on the input field value (ID).
     * Retrieves the product from the list by ID and deletes it from the database.
     * If the deletion is successful, updates the table model and clears the input fields.
     */
    private void deleteProduct() {
        String deleteIdText = idTextField.getText().trim();
        if (!deleteIdText.isEmpty()) {
            int deleteId = Integer.parseInt(deleteIdText);
            Product productToDelete = getProductById(deleteId);
            if (productToDelete != null) {
                ProductBLL.deleteProduct(productToDelete);
                productList.remove(productToDelete);
                updateTableModel();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Product with ID " + deleteId + " not found.");
            }
        }
    }

    /**
     * Updates a product based on the input field values (ID, name, amount).
     * Retrieves the product from the list by ID and updates its name and amount in the database.
     * If the update is successful, updates the table model and clears the input fields.
     */
    private void updateProduct() {
        String idText = idTextField.getText().trim();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            String name = nameTextField.getText().trim();
            int amount = Integer.parseInt(amountTextField.getText().trim());

            if (!name.isEmpty()) {
                Product productToUpdate = getProductById(id);
                if (productToUpdate != null) {
                    productToUpdate.setName(name);
                    productToUpdate.setAmmount(amount);
                    ProductBLL.updateProduct(productToUpdate);
                    updateTableModel();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Product with ID " + id + " not found.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter an ID.");
        }
    }

    /**
     * Updates the table model with the current product list.
     * Clears the previous data and adds each product's ID, name, and amount as a row to the table model.
     */
    private void updateTableModel() {
        tableModel.setRowCount(0); // Clear previous data

        for (Product product : productList) {
            Object[] rowData = {product.getId(), product.getName(), product.getAmmount()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Clears the input fields for name, amount, and ID.
     */
    private void clearInputFields() {
        nameTextField.setText("");
        amountTextField.setText("");
        idTextField.setText("");
    }

    /**
     * Retrieves a product from the list by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the {@code Product} object with the specified ID, or {@code null} if not found
     */
    private Product getProductById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /**
     * Closes the current panel and goes back to the main panel.
     */
    private void goBack() {
        MainPanel mainPanel = new MainPanel();
        mainPanel.setVisible(true);
        dispose();
    }
}
