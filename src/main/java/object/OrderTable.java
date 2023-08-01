package object;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import object.Order;

import static config.CONFIG.ORDER_GROUP_SIZE;

public class OrderTable {
    private List<List<Order>> table = new ArrayList<>();

    public OrderTable() {
        for (int i = 0; i < ORDER_GROUP_SIZE; i++) {
            table.add(new ArrayList<>());
        }
    }

    public OrderTable(String fileDir, Graph graph) {
        this();
        if (fileDir != null && graph != null) {
            initialize(fileDir, graph);
        }
    }

    private void initialize(String fileDir, Graph graph) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileDir))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                String orderId = data[0];
                double latitude = Double.parseDouble(data[1]);
                double longitude = Double.parseDouble(data[2]);
                String terminalId = data[3];
                String destId = data[4];
                double cbm = Double.parseDouble(data[5]);
                int start = Integer.parseInt(data[6]);
                int end = Integer.parseInt(data[7]);
                int load = Integer.parseInt(data[8]); // serviceTime
                int group = Integer.parseInt(data[9]);

                int terminalIdx = graph.id2idx(terminalId);
                int destIdx = graph.id2idx(destId);

                table.get(group).add(new Order(orderId, terminalIdx, destIdx, cbm, load, group, start, end));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading the file.");
            System.exit(1);
        }
    }

    /**
     * Methods
     */

    public List<Order> getBatch(int batch) {

        if (ORDER_GROUP_SIZE < batch) {
            System.out.println(String.valueOf(batch) + "is not a valid batch.");
            System.exit(1);
        }
        return table.get(batch);
    }

}

