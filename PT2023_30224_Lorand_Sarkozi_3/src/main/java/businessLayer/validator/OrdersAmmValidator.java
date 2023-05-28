package businessLayer.validator;

import model.Orders;

/**
 * The {@code OrdersAmmValidator} class validates the amount of orders.
 * It implements the {@link Validator} interface with {@code Orders} as the type to validate.
 *
 * @Author Sarkozi Lorand
 */
public class OrdersAmmValidator implements Validator<Orders> {

    /**
     * Validates the amount of orders.
     *
     * @param orders the orders to validate
     * @throws IllegalArgumentException if the orders amount is less than 0
     */
    public void validate(Orders orders) {
        if (orders.getOrderAmm() < 0) {
            throw new IllegalArgumentException("The Orders Amount limit is not respected!");
        }
    }
}
