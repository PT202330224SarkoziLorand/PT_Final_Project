package businessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import dataAccessLayer.ProductDAO;
import businessLayer.validator.ProductAmmValidator;
import businessLayer.validator.Validator;
import model.Product;

/**
 * The {@code ProductBLL} class represents the business logic for product-related operations.
 * It provides methods for finding, inserting, updating, and deleting products.
 *
 * @Author Sarkozi Lorand
 */
public class ProductBLL {

    private static List<Validator<Product>> validators = new ArrayList<>();

    /**
     * Constructs a new {@code ProductBLL} instance.
     * Initializes the list of validators.
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new ProductAmmValidator());
    }

    /**
     * Finds a product by the specified ID.
     *
     * @param id the ID of the product to find
     * @return the found product
     * @throws NoSuchElementException if the product with the given ID was not found
     */
    public Product findProductById(int id) {
        Product product = ProductDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }

    /**
     * Inserts a new product.
     *
     * @param product the product to insert
     * @return the ID of the inserted product
     */
    public int insertProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        return ProductDAO.insert(product);
    }

    /**
     * Updates an existing product.
     *
     * @param product the product to update
     */
    public static void updateProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        ProductDAO.update(product);
    }

    /**
     * Deletes an existing product.
     *
     * @param product the product to delete
     */
    public static void deleteProduct(Product product) {
        ProductDAO.delete(product);
    }
}
