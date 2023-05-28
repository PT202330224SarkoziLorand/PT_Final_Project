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
import model.Product;

/**
 * The {@code ProductDAO} class is responsible for performing CRUD operations on the "Product" table in the database.
 * It provides methods for finding, inserting, updating, and deleting product records.
 *
 * @Author Sarkozi Lorand
 */
public class ProductDAO {

    protected static final Logger LOGGER = Logger.getLogger(Product.class.getName());
    private static final String insertStatementString = "INSERT INTO Product (name, ammount) VALUES (?, ?)";
    private final static String findStatementString = "SELECT * FROM Product WHERE id = ?";
    private final static String findStatementStringAll = "SELECT * FROM Product";
    private static final String updateStatementString = "UPDATE Product SET name=?, ammount=? WHERE id=?";
    private static final String deleteStatementString = "DELETE FROM Product WHERE id = ?";

    /**
     * Retrieves a product record with the specified ID from the database.
     *
     * @param productId the ID of the product to retrieve
     * @return the retrieved product object, or {@code null} if not found
     */
    public static Product findById(int productId) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, productId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            Integer amount = rs.getInt("ammount");
            toReturn = new Product(productId, name, amount);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Updates a product record in the database.
     *
     * @param product the product object to update
     */
    public static void update(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, product.getName());
            updateStatement.setInt(2, product.getAmmount());
            updateStatement.setInt(3, product.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Inserts a new product record into the database.
     *
     * @param product the product object to insert
     * @return the ID of the inserted product record
     */
    public static int insert(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, product.getName());
            insertStatement.setInt(2, product.getAmmount());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Retrieves all product records from the database.
     *
     * @return a list of all product objects
     */
    public List<Product> findAll() {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        Statement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementStringAll);

            rs = findStatement.executeQuery(findStatementStringAll);

            return createObjects(rs);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return createObjects(rs);
    }

    /**
     * Creates a list of product objects from the given result set.
     *
     * @param resultSet the result set containing product records
     * @return a list of product objects
     */
    private List<Product> createObjects(ResultSet resultSet) {
        List<Product> list = new ArrayList<Product>();
        try {
            while (resultSet.next()) {
                Product product = new Product(
                        Integer.parseInt(resultSet.getObject("id").toString()),
                        resultSet.getObject("name").toString(),
                        Integer.parseInt(resultSet.getObject("ammount").toString())
                );
                list.add(product);
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

    /**
     * Deletes a product record from the database.
     *
     * @param product the product object to delete
     */
    public static void delete(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, product.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
}
