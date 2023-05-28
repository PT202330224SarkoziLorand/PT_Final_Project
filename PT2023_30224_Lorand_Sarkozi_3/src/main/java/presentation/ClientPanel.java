package presentation;

import businessLayer.ClientBLL;
import dataAccessLayer.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ClientPanel} class represents the GUI panel for managing clients.
 * It allows adding, deleting, and updating client information.
 *
 * @Author Sarkozi Lorand
 */
public class ClientPanel extends JFrame {
    private DefaultTableModel tableModel;
    private JTable clientTable;
    private JScrollPane scrollPane;
    private JTextField nameTextField;
    private JTextField idTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton backButton;
    private List<Client> clientList;

    /**
     * Constructs a new instance of the {@code ClientPanel} class.
     * Initializes the GUI components and sets up the event listeners.
     */
    public ClientPanel() {
        setLayout(new BorderLayout());
        setSize(800, 200);

        clientList = new ArrayList<>();
        clientList.addAll(new ClientDAO().findAll());

        tableModel = RefelectionExample.retrieveProperties(clientList);
        clientTable = new JTable(tableModel);

        scrollPane = new JScrollPane(clientTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1));

        nameTextField = new JTextField(15);
        idTextField = new JTextField(5);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        backButton = new JButton("Back");

        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout());
        idPanel.add(new JLabel("ID:"));
        idPanel.add(idTextField);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        inputPanel.add(idPanel);
        inputPanel.add(namePanel);
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.WEST);
        add(backButton, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        updateTableModel();
    }

    /**
     * Adds a new client when the add button is clicked.
     * Retrieves the client information from the input fields, creates a new client object, and inserts it into the database.
     * Updates the table model and clears the input fields.
     */
    private void addClient() {
        String name = nameTextField.getText().trim();
        String idText = idTextField.getText().trim();
        if (!name.isEmpty() && !idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Client client = new Client(id, name);
            clientList.add(client);
            int insertedId = ClientBLL.insertClient(client);
            client.setId(insertedId);
            updateTableModel();
            clearInputFields();
        }
    }

    /**
     * Deletes a client when the delete button is clicked.
     * Retrieves the client ID from the input field, finds the corresponding client object, and deletes it from the database.
     * Updates the table model and clears the input fields.
     */
    private void deleteClient() {
        String idText = idTextField.getText().trim();
        if (!idText.isEmpty()) {
            int deleteId = Integer.parseInt(idText);
            Client clientToDelete = getClientById(deleteId);
            if (clientToDelete != null) {
                ClientBLL.deleteClient(clientToDelete);
                clientList.remove(clientToDelete);
                updateTableModel();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Client with ID " + deleteId + " not found.");
            }
        }
    }

    /**
     * Updates the information of a client when the update button is clicked.
     * Retrieves the client information from the input fields, finds the corresponding client object,
     * and updates its name in the database.
     * Updates the table model and clears the input fields.
     */
    private void updateClient() {
        String name = nameTextField.getText().trim();
        String idText = idTextField.getText().trim();
        if (!name.isEmpty() && !idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Client clientToUpdate = getClientById(id);
            if (clientToUpdate != null) {
                clientToUpdate.setName(name);
                ClientBLL.updateClient(clientToUpdate);
                updateTableModel();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Client with ID " + id + " not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid name and ID.");
        }
    }

    /**
     * Retrieves a client object by its ID from the client list.
     *
     * @param id the ID of the client to retrieve
     * @return the client object with the given ID, or {@code null} if not found
     */
    private Client getClientById(int id) {
        for (Client client : clientList) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    /**
     * Updates the table model with the current client list data.
     * Clears the table model and adds rows for each client in the client list.
     */
    private void updateTableModel() {
        tableModel.setRowCount(0);
        for (Client client : clientList) {
            tableModel.addRow(new Object[]{client.getId(), client.getName()});
        }
        tableModel.fireTableDataChanged();
    }

    /**
     * Clears the input fields for name and ID.
     */
    private void clearInputFields() {
        nameTextField.setText("");
        idTextField.setText("");
    }

    /**
     * Goes back to the main panel when the back button is clicked.
     * Creates an instance of the {@code MainPanel} and makes it visible.
     * Closes the current client panel.
     */
    private void goBack() {
        MainPanel mainPanel = new MainPanel();
        mainPanel.setVisible(true);
        dispose();
    }
}
