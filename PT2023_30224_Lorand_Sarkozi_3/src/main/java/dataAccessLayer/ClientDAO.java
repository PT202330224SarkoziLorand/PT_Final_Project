package dataAccessLayer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
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

/**
 * The {@code ClientDAO} class is responsible for performing CRUD operations on the "Client" table in the database.
 * It provides methods for finding, inserting, updating, and deleting client records.
 *
 * @Author Sarkozi Lorand
 */
public class ClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final String insertStatementString = "INSERT INTO Client (name) VALUES (?)";
    private final static String findStatementString = "SELECT * FROM Client WHERE id = ?";
    private final static String findStatementStringAll = "SELECT * FROM Client";

    /**
     * Retrieves a client record with the specified ID from the database.
     *
     * @param clientId the ID of the client to retrieve
     * @return the retrieved client object, or {@code null} if not found
     */
    public static Client findById(int clientId) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, clientId);
            rs = findStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                toReturn = new Client(clientId, name);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Inserts a new client record into the database.
     *
     * @param client the client object to insert
     * @return the ID of the inserted client record
     */
    public static int insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
            ConnectionFactory.close(rs);

            String updateIdStatementString = "UPDATE Client SET id = ? WHERE id = ?";
            PreparedStatement updateIdStatement = dbConnection.prepareStatement(updateIdStatementString);
            updateIdStatement.setInt(1, client.getId());
            updateIdStatement.setInt(2, insertedId);
            updateIdStatement.executeUpdate();
            ConnectionFactory.close(updateIdStatement);

            insertedId = client.getId(); // Set the inserted ID to the custom ID

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Deletes a client record from the database.
     *
     * @param client the client object to delete
     */
    public static void delete(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            String deleteStatementString = "DELETE FROM Client WHERE id = ?";
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, client.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Updates a client record in the database.
     *
     * @param client the client object to update
     */
    public static void update(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            String updateStatementString = "UPDATE Client SET name = ? WHERE id = ?";
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setInt(2, client.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Retrieves all client records from the database.
     *
     * @return a list of all client objects
     */
    public List<Client> findAll() {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        Statement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementStringAll);
            rs = findStatement.executeQuery(findStatementStringAll);

            return createObjects(rs);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return createObjects(rs);
    }

    /**
     * Creates a list of client objects from the given result set.
     *
     * @param resultSet the result set containing client records
     * @return a list of client objects
     */
    private List<Client> createObjects(ResultSet resultSet) {
        List<Client> list = new ArrayList<Client>();
        try {
            while (resultSet.next()) {
                Client client = new Client(Integer.parseInt(resultSet.getObject("id").toString()), resultSet.getObject("name").toString());
                list.add(client);
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
