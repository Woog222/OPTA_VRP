package config;

public class CONFIG {

    /**
     * CONSTANTS
     */

    public static final int ORDER_GROUP_SIZE = 30;
    public static final int GRAPH_SIZE = 1600;
    public static final int HOUR = 60;
    public static final int DAY = HOUR*24;
    public static final int WEEK = DAY*7;

    public static final int MAX = 987654321;
    public static final int LAST_BATCH = 23;
    public static final int GROUP_INTERVAL = 360;
    public static final int MAX_START_TIME = WEEK - 60;
    public static final int INT_NULL = -1;

    /**
     *
     */
    public static final String ORDER_ID_NULL = "Null";
    public static final String STRING_NULL = "Null";

    /**
     * FILE_DIR
     */
    public static final String GRAPH_DIR = "data/od_matrix.txt";
    public static final String ORDER_DIR = "data/orders.txt";
    public static final String VEHICLE_DIR = "data/vehicles.txt";
    public static final String TERMINAL_DIR = "data/terminals.txt";

}
