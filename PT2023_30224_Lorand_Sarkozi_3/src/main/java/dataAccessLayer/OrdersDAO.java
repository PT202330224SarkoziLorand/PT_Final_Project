package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;
import model.Orders;

/**
 * The {@code OrdersDAO} class is responsible for performing CRUD operations on the "Orders" table in the database.
 * It provides methods for finding, inserting, and retrieving orders records.
 *
 * @Author Sarkozi Lorand
 */
public class OrdersDAO {

    protected static final Logger LOGGER = Logger.getLogger(Orders.class.getName());
    private static final String insertStatementString = "INSERT INTO Orders (client_id,product_id,ammount) VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM Orders WHERE id = ?";
    private final static String findStatementStringAll = "SELECT * FROM Orders";

    /**
     * Retrieves an order record with the specified ID from the database.
     *
     * @param ordersId the ID of the order to retrieve
     * @return the retrieved order object, or {@code null} if not found
     */
    public static Orders findById(int ordersId) {
        Orders toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, ordersId);
            rs = findStatement.executeQuery();
            rs.next();

            int client_id = rs.getInt("client_id");
            int product_id = rs.getInt("product_id");
            int ammount = rs.getInt("ammount");
            toReturn = new Orders(ordersId, client_id, product_id, ammount);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrdersDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Inserts a new order record into the database.
     *
     * @param orders the order object to insert
     * @return the ID of the inserted order record
     */
    public static int insert(Orders orders) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, orders.getClientId());
            insertStatement.setInt(2, orders.getProductId());
            insertStatement.setInt(3, orders.getOrderAmm());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrdersDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Retrieves all order records from the database.
     *
     * @return a list of all order objects
     */
    public List<Orders> findAll() {
        Orders toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        Statement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementStringAll);
            rs = findStatement.executeQuery(findStatementStringAll);

            return createObjects(rs);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrdersDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return createObjects(rs);
    }

    /**
     * Creates a list of order objects from the given result set.
     *
     * @param resultSet the result set containing order records
     * @return a list of order objects
     */
    private List<Orders> createObjects(ResultSet resultSet) {
        List<Orders> list = new ArrayList<Orders>();
        try {
            while (resultSet.next()) {
                Orders order = new Orders(
                        Integer.parseInt(resultSet.getObject("id").toString()),
                        Integer.parseInt(resultSet.getObject("client_id").toString()),
                        Integer.parseInt(resultSet.getObject("product_id").toString()),
                        Integer.parseInt(resultSet.getObject("ammount").toString())
                );
                list.add(order);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
