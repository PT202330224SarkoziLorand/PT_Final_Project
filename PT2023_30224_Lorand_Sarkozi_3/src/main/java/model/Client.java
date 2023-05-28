package model;
/**
 *
 * The {@code Client} class represents a client entity with its unique identifier and name.
 * It provides methods to get and set the client's ID and name.
 *
 * @Author Sarkozi Lorand
 */
public class Client {
    private int id;
    private String name;

    /**
     * Constructs a new client with the specified ID and name.
     *
     * @param id   the unique identifier of the client
     * @param name the name of the client
     */
    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructs a new empty client.
     */
    public Client() {

    }

    /**
     * Retrieves the ID of the client.
     *
     * @return the ID of the client
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the client.
     *
     * @param id the ID to set for the client
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the client.
     *
     * @return the name of the client
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     *
     * @param name the name to set for the client
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the client.
     *
     * @return a string representation of the client
     */
    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + "]";
    }
}
