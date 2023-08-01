package object;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import static config.CONFIG.GRAPH_SIZE;

class Edge {
    private final int time, dist;

    public Edge(int time_, int dist_) {
        this.time = time_;
        this.dist = dist_;
    }

    // default
    public Edge() {
        this.time = -1;
        this.dist = -1;
    }

    public int getTime() {
        return time;
    }

    public int getDist() {
        return dist;
    }
}

public class Graph {

    public final Edge[][] table;
    private Map<String, Integer> IDX;
    private Map<Integer, String> ID;
    private int idx = 0;

    public Graph(String fileDir) {
        table = new Edge[GRAPH_SIZE][GRAPH_SIZE];
        IDX = new HashMap<>();
        ID = new HashMap<>();

        for (int i = 0; i < GRAPH_SIZE; i++) {
            for (int j = 0; j < GRAPH_SIZE; j++) {
                table[i][j] = null;
            }
            table[i][i] = new Edge(0,0);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileDir))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String origin = parts[0];
                String dest = parts[1];
                double dist = Double.parseDouble(parts[2]);
                double time = Double.parseDouble(parts[3]);

                // Index setting
                int from_, to_;
                if (!IDX.containsKey(origin)) {
                    IDX.put(origin, idx);
                    from_ = idx++;
                } else {
                    from_ = IDX.get(origin);
                }
                if (!IDX.containsKey(dest)) {
                    IDX.put(dest, idx);
                    to_ = idx++;
                } else {
                    to_ = IDX.get(dest);
                }

                // Adjacency matrix
                table[from_][to_] = new Edge((int) Math.ceil(time), (int) Math.ceil(dist));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // ID setting
        for (Map.Entry<String, Integer> entry : IDX.entrySet()) {
            ID.put(entry.getValue(), entry.getKey());
        }

        // to csv
        for (Map.Entry<Integer, String> entry : ID.entrySet()) {

        }
    }



    public int id2idx(String id) {
        if (!IDX.containsKey(id)) {
            System.out.println(id + " is not in od_matrix");
            System.exit(1);
        }
        return IDX.get(id);
    }

    public String idx2id(int idx) {
        if (!ID.containsKey(idx)) {
            System.out.println(idx + " is not a valid index.");
            System.exit(1);
        }
        return ID.get(idx);
    }

    public int getDist(int from_, int to_) {
        Edge e = table[from_][to_];
        return (e!=null) ? e.getDist() : -1;
    }

    public int getTime(int from_, int to_) {
        Edge e = table[from_][to_];
        return (e!=null) ? e.getTime() : -1;
    }

    public int getSize() {
        return idx;
    }
}
