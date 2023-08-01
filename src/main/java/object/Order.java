package object;

import config.CONFIG;
import init.StartTimeCalculator;

import static config.CONFIG.*;

public class Order {

    // allocated order (Actual unique ID)
    private static long generalSequence = 0;

    /**
     * INFOS
     */
    private String orderId; // terminal loading -> "-1"
    private int terminalIdx; // if terminal loading, "-1";
    private int destIdx; // if terminal loading, terminal Idx
    private final double cbm; // capa constraint
    private final int group; // batch (0 1 .. 23)
    private int start, end; // time window



    private final int serviceTime; //service time (load)

    /**
     * determined after allocated
     */
    private long sequence;
    private int arrivalTime;
    private String vehicleId;
    private boolean allocated= false, delivered = false;

    public void allocate(int arrivalTime, String vehicleId) {
        this.allocated=true;
        this.vehicleId = vehicleId;
        this.arrivalTime = arrivalTime;
        sequence = ++generalSequence;
    }

    public void allocate() { this.allocated=true; }

    public boolean isAllocated() { return this. allocated; }

    public void update(int curTime) {
        if (getDepartureTime() <= curTime)
            delivered = true;
    }

    public void reset() {
        allocated = false; delivered = false;
    }

    // Generalq

    public Order(String orderId, int terminalIdx, int destIdx, double cbm, int serviceTime, int group, int start, int end) {
        this.orderId = orderId;
        this.terminalIdx = terminalIdx;
        this.destIdx = destIdx;
        this.cbm = cbm;
        this.serviceTime = serviceTime;
        this.group = group;
        this.start = start;
        this.end = end;
    }

    // Terminal Loading
    public Order(int terminalIdx) {
        this.orderId = ORDER_ID_NULL;
        this.destIdx = this.terminalIdx = terminalIdx;
        this.cbm = -1;
        this.serviceTime = 0;
        this.group = -1;
        this.start = 0;
        this.end = DAY;
    }

    /**
     * Trivial Getter and Setter
     */
    public String getOrderId() {
        return orderId;
    }

    public int getTerminalIdx() {
        return terminalIdx;
    }

    public int getDestIdx() {
        return destIdx;
    }

    public double getCbm() {
        return cbm;
    }

    public int getGroup() {
        return group;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getStartTime() {
        return StartTimeCalculator.startTimeCal(getArrivalTime(), start, end);
    }

    public int getWaitingTime() {
        return getStartTime() - getArrivalTime();
    }

    public int getDepartureTime() {
        return getStartTime() + getServiceTime();
    }


    @Override
    public String toString() {
        String seperator = ",";
        StringBuilder sb = new StringBuilder();
        boolean termianlOrder = orderId.equals(ORDER_ID_NULL);

        sb.append(orderId).append(seperator)
                .append(vehicleId).append(seperator)
                .append(sequence).append(seperator)
                .append(destIdx).append(seperator)
                .append(delivered ? getArrivalTime() : STRING_NULL).append(seperator)
                .append(delivered ? getWaitingTime() : STRING_NULL).append(sequence)
                .append(delivered ? getServiceTime() : STRING_NULL).append(sequence)
                .append(delivered ? getDepartureTime() : STRING_NULL).append(sequence)
                .append(termianlOrder ? STRING_NULL : (delivered ? "Yes" : "No") )
                .append('\n');

        return sb.toString();
    }
}


