package init;

import object.Graph;
import object.Order;
import object.OrderTable;
import object.Vehicle;

import java.util.ArrayList;
import java.util.List;

import static config.CONFIG.*;

public class InitSolutionGenerator {

    private Graph graph;
    private List<Order> orderList;
    private List<Vehicle> vehicleList;
    private int curBatch; // 0 .. 23

    public InitSolutionGenerator(Graph graph, List<Order> orderList, List<Vehicle> vehicleList, int curBatch) {
        this.graph = graph;
        this.orderList = orderList;
        this.vehicleList = vehicleList;
        this.curBatch = curBatch;
    }

    public List<Vehicle> getInitSolution() {
        System.out.println("Simulation ongoing..");

        boolean allocated;
        do {
            allocated = false;
            for (Vehicle veh : vehicleList) {
                orderList.sort((order1, order2) -> {
                    int startTime1 = startTime(veh, order1);
                    int startTime2 = startTime(veh, order2);
                    int cbmDiff = Double.compare(order2.getCbm(), order1.getCbm());
                    return startTime1 != startTime2 ? Integer.compare(startTime1, startTime2) : cbmDiff;
                });

                allocated |= vehCycle(veh);
            }
        } while (allocated);

        return vehicleList;
    }

    private int startTime(Vehicle veh, Order order) {
        int arrivalTime = (int) graph.getTime(veh.getVarLoc(), order.getDestIdx()) + veh.getVarTime();
        return StartTimeCalculator.startTimeCal(arrivalTime, order.getStart(), order.getEnd());
    }


    private boolean vehCycle(Vehicle veh) {
        boolean ret = false;
        double left = veh.getCapa();
        int when = veh.getVarTime();
        int where = veh.getVarLoc();
        int terminal = -1;
        int arrivalTime = -1;
        int startTime = -1;

        // Terminal determination
        for (Order order : orderList) {
            if (order.isAllocated() || left < order.getCbm() ||
                    graph.getTime(where, order.getTerminalIdx()) < 0 ||
                    graph.getTime(order.getTerminalIdx(), order.getDestIdx()) < 0) {
                continue;
            }

            arrivalTime = when + graph.getTime(where, order.getTerminalIdx()) +
                    graph.getTime(order.getTerminalIdx(), order.getDestIdx());
            startTime = StartTimeCalculator.startTimeCal(arrivalTime, order.getStart(), order.getEnd());

            // to terminal
            terminal = order.getTerminalIdx();
            when += graph.getTime(where, order.getTerminalIdx());
            where = order.getTerminalIdx();
            veh.addOrder(new Order(terminal));
            break;
        }

        // Remaining orders allocation
        while (true) {
            Order order = nextOrder(orderList, veh, where, when, left, terminal, curBatch != LAST_BATCH);
            if (order == null) break; // Can't allocate anymore
            ret |= true;
            veh.addOrder(order);

            if (order.getDestIdx() != where) {
                arrivalTime = when + graph.getTime(where, order.getDestIdx());
                startTime = StartTimeCalculator.startTimeCal(arrivalTime, order.getStart(), order.getEnd());
                when = startTime + order.getServiceTime();
            }

            int travelTime = graph.getTime(where, order.getDestIdx());
            where = order.getDestIdx();
            left -= order.getCbm();
            order.allocate();
        }

        veh.setVarTime(when);
        veh.setVarLoc(where);
        return ret;
    }

    private Order nextOrder(List<Order> batch, Vehicle veh, int where, int when, double left, int terminal, boolean carryOver) {
        Order ret = null;
        int bestStart = Integer.MAX_VALUE;

        for (Order order : batch) {
            if (order.isAllocated() || left < order.getCbm() || terminal != order.getTerminalIdx() ||
                    graph.getTime(where, order.getDestIdx()) < 0) {
                continue;
            }

            int arrivalTime = when + graph.getTime(where, order.getDestIdx());
            int startTime = StartTimeCalculator.startTimeCal(arrivalTime, order.getStart(), order.getEnd());

            if (startTime < bestStart) {
                ret = order;
                bestStart = startTime;
            }
        }

        return ret;
    }

}

