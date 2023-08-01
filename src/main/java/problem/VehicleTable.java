package problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import object.*;

public class VehicleTable {
    private List<Vehicle> table = new ArrayList<>();

    public VehicleTable(String file_dir, Graph graph) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_dir))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String vehNum = parts[0];
                double vehTon = Double.parseDouble(parts[1]);
                double capa = Double.parseDouble(parts[4]);
                double fc = Double.parseDouble(parts[6]);
                double vc = Double.parseDouble(parts[7]);
                int startCenter = graph.id2idx(parts[5]);
                Vehicle vehicle = new Vehicle(0, capa, fc, vc, vehTon, vehNum, startCenter, graph);
                table.add(vehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(table);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vehicle vehicle : table) {
            sb.append(vehicle).append("\n");
        }
        return sb.toString();
    }
}

