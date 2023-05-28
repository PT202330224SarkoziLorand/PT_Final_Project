package businessLayer;

import java.util.NoSuchElementException;

import dataAccessLayer.ClientDAO;
import model.Client;

/**
 * The {@code ClientBLL} class represents the business logic for client-related operations.
 * It provides methods for finding, inserting, deleting, and updating clients.
 *
 * @Author Sarkozi Lorand
 */
public class ClientBLL {

    /**
     * Constructs a new {@code ClientBLL} instance.
     */
    public ClientBLL() {
    }

    /**
     * Finds a client by the specified ID.
     *
     * @param id the ID of the client to find
     * @return the found client
     * @throws NoSuchElementException if the client with the given ID was not found
     */
    public Client findClientById(int id) {
        Client client = ClientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id=" + id + " was not found!");
        }
        return client;
    }

    /**
     * Inserts a new client.
     *
     * @param client the client to insert
     * @return the ID of the inserted client
     */
    public static int insertClient(Client client) {
        return ClientDAO.insert(client);
    }

    /**
     * Deletes a client.
     *
     * @param client the client to delete
     */
    public static void deleteClient(Client client) {
        ClientDAO.delete(client);
    }

    /**
     * Updates a client.
     *
     * @param client the client to update
     */
    public static void updateClient(Client client) {
        ClientDAO.update(client);
    }

}
