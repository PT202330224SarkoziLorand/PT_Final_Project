package businessLayer.validator;

import model.Product;

/**
 * The {@code ProductAmmValidator} class validates the amount of products.
 * It implements the {@link Validator} interface with {@code Product} as the type to validate.
 *
 * @Author Sarkozi Lorand
 */
public class ProductAmmValidator implements Validator<Product> {
    private static final int MIN_AMM = 0;

    /**
     * Validates the amount of products.
     *
     * @param product the product to validate
     * @throws IllegalArgumentException if the product amount is less than the minimum amount
     */
    public void validate(Product product) {
        if (product.getAmmount() < MIN_AMM) {
            throw new IllegalArgumentException("The Products Amount limit is not respected!");
        }
    }
}
