package presentation;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.List;

public class RefelectionExample {

    /**
     * Retrieves the properties of objects in a list and constructs a DefaultTableModel.
     *
     * @param objects the list of objects
     * @return a DefaultTableModel representing the properties of the objects
     *
     * @Author Sarkozi Lorand
     */
    public static DefaultTableModel retrieveProperties(List<?> objects) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setRowCount(0);

        // Prepare header
        String[] header = new String[objects.get(0).getClass().getDeclaredFields().length];
        int i = 0;
        for (Field field : objects.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            header[i] = field.getName();
            i++;
        }

        // Prepare data
        Object[][] data = new Object[objects.size()][header.length];
        i = 0;
        for (Object object : objects) {
            int j = 0;
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    data[i][j] = field.get(object);
                    j++;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }

        // Create and return the DefaultTableModel
        tableModel = new DefaultTableModel(data, header);
        return tableModel;
    }
}
