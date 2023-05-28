package model;
/**
 * The {@code Orders} class represents an order entity with its unique identifier, client ID, product ID, and order amount.
 * It provides methods to get and set the order's ID, client ID, product ID, and order amount.
 *
 * @Author Sarkozi Lorand
 */
public class Orders {
    private int id;
    private int clientId;
    private int productId;
    private int orderAmm;

    /**
     * Constructs an {@code Orders} object with the specified ID, client ID, product ID, and order amount.
     *
     * @param id         the unique identifier of the order
     * @param clientId   the client ID associated with the order
     * @param productId  the product ID associated with the order
     * @param orderAmm   the order amount
     */
    public Orders(int id, int clientId, int productId, int orderAmm) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.orderAmm = orderAmm;
    }

    /**
     * Returns the ID of the order.
     *
     * @return the ID of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id the ID of the order to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the client ID associated with the order.
     *
     * @return the client ID associated with the order
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets the client ID associated with the order.
     *
     * @param clientId the client ID associated with the order to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Returns the product ID associated with the order.
     *
     * @return the product ID associated with the order
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the product ID associated with the order.
     *
     * @param productId the product ID associated with the order to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Returns the order amount.
     *
     * @return the order amount
     */
    public int getOrderAmm() {
        return orderAmm;
    }

    /**
     * Sets the order amount.
     *
     * @param orderAmm the order amount to set
     */
    public void setOrderAmm(int orderAmm) {
        this.orderAmm = orderAmm;
    }

    /**
     * Returns a string representation of the order.
     *
     * @return a string representation of the order
     */
    @Override
    public String toString() {
        return "Order [id=" + id + ", clientId=" + clientId + ", productId=" + productId + ", orderAmm=" + orderAmm + "]";
    }
}
