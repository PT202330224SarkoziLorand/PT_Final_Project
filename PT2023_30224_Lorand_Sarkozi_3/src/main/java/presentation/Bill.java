package presentation;

import businessLayer.ClientBLL;
import businessLayer.ProductBLL;
import model.Orders;

import java.io.FileWriter;
import java.io.IOException;

public record Bill(Orders order) {

    /**
     * Generates a bill for the given order.
     *
     * @Author Sarkozi Lorand
     */
    public void generateBill() {
        String billContent = createBillContent();
        writeBillToFile(billContent);
    }

    private String createBillContent() {
        StringBuilder content = new StringBuilder();
        content.append("Order id: ").append(order.getId()).append("\n");

        ClientBLL clientBLL = new ClientBLL();
        String clientName = clientBLL.findClientById(order.getClientId()).getName();
        content.append("Client: ").append(clientName).append("\n");

        ProductBLL productBLL = new ProductBLL();
        String productName = productBLL.findProductById(order.getProductId()).getName();
        content.append("Product bought: ").append(productName).append("\n");
        content.append("Amount: ").append(order.getOrderAmm()).append("\n");

        return content.toString();
    }

    private void writeBillToFile(String billContent) {
        String FILE_PATH = "Order" + order.getId() + ".txt";
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(billContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
