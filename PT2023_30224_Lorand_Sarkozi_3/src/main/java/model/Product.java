package model;

/**
 * The {@code Product} class represents a product entity with its unique identifier, name, and quantity.
 *
 * @Author Sarkozi Lorand
 */
public class Product {
    private int id;
    private String name;
    private int ammount;

    /**
     * Constructs a new {@code Product} object with the specified ID, name, and quantity.
     *
     * @param id      the unique identifier of the product
     * @param name    the name of the product
     * @param ammount the quantity of the product
     */
    public Product(int id, String name, int ammount) {
        super();
        this.id = id;
        this.name = name;
        this.ammount = ammount;
    }

    /**
     * Constructs a new {@code Product} object with the specified name and quantity.
     *
     * @param name    the name of the product
     * @param ammount the quantity of the product
     */
    public Product(String name, int ammount) {
        super();
        this.name = name;
        this.ammount = ammount;
    }

    /**
     * Returns the ID of the product.
     *
     * @return the ID of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id the ID of the product to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name of the product to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return the quantity of the product
     */
    public int getAmmount() {
        return ammount;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param ammount the quantity of the product to set
     */
    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    /**
     * Returns a string representation of the product.
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", ammount=" + ammount + "]";
    }
}
