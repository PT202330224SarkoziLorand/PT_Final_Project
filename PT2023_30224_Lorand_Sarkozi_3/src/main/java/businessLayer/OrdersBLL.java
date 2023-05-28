package businessLayer;

import java.util.ArrayList;
import java.util.List;
import businessLayer.validator.Validator;
import java.util.NoSuchElementException;
import dataAccessLayer.ProductDAO;
import dataAccessLayer.OrdersDAO;
import businessLayer.validator.OrdersAmmValidator;
import model.Product;
import model.Orders;

/**
 * The {@code OrdersBLL} class represents the business logic for order-related operations.
 * It provides methods for finding and inserting orders.
 *
 * @Author Sarkozi Lorand
 */
public class OrdersBLL {

    private List<Validator<Orders>> validators;

    /**
     * Constructs a new {@code OrdersBLL} instance.
     * Initializes the list of validators.
     */
    public OrdersBLL() {
        validators = new ArrayList<Validator<Orders>>();
        validators.add(new OrdersAmmValidator());
    }

    /**
     * Finds an order by the specified ID.
     *
     * @param id the ID of the order to find
     * @return the found order
     * @throws NoSuchElementException if the order with the given ID was not found
     */
    public Orders findOrdersById(int id) {
        Orders order = OrdersDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id=" + id + " was not found!");
        }
        return order;
    }

    /**
     * Inserts a new order.
     *
     * @param order the order to insert
     * @return the ID of the inserted order
     */
    public int insertOrder(Orders order) {
        for (Validator<Orders> validator : validators) {
            validator.validate(order);
        }
        return OrdersDAO.insert(order);
    }
}
