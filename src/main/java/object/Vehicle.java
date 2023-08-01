package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import init.StartTimeCalculator;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningListVariable;

import static config.CONFIG.ORDER_ID_NULL;

@PlanningEntity
public class Vehicle implements Comparable<Vehicle> {

    /**
     * FIXED INFOS
     */
    private double capa, fc, vc, vehTon,  travelDistance;
    private String veh_num;
    private int startCenter, freeTime, varTime, varLoc;


    private Graph graph;



    /**
     * FOR STATISTICS (changes during progress)
     * must include terminal loading process
     */
    private List<Order> orderList;


    public Vehicle( int freeTime, double capa, double fc, double vc, double vehTon, String veh_num, int startCenter, Graph graph) {
        this.capa = capa;
        this.fc = fc;
        this.vc = vc;
        this.vehTon = vehTon;
        this.veh_num = veh_num;
        this.startCenter = startCenter;
        this.graph = graph;
        this.freeTime = this.varTime = freeTime;
    }

    public double getCapa() {
        return capa;
    }

    public double getFc() {
        return fc;
    }

    public double getVc() {
        return vc;
    }

    public double getVehTon() {
        return vehTon;
    }

    public String getVeh_num() {
        return veh_num;
    }

    public int getStartCenter() {
        return startCenter;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public int getVarTime() {
        return varTime;
    }

    public void setVarTime(int varTime) {
        this.varTime = varTime;
    }

    public int getFreeTime() {
        return freeTime;
    }

    public Graph getGraph() {
        return graph;
    }
    public int getVarLoc() {
        return varLoc;
    }

    public void setVarLoc(int varLoc) {
        this.varLoc = varLoc;
    }


    /**
     * Complex Methods
     */

    @Override
    public String toString() {
        return String.format("capa: %f, vc: %f, veh_num: %s, free_time: %f", capa, vc, veh_num, freeTime);
    }

    /**
     * For Sorting
     *  1. earlier freeTime
     *  2. larger capa
     *  3. smaller cost
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Vehicle other) {
        if (this.freeTime == other.freeTime) {
            if (this.capa == other.capa) {
                return Double.compare(this.vc, other.vc);
            }
            return Double.compare(other.capa, this.capa);
        }
        return Double.compare(this.freeTime, other.freeTime);
    }

    public List<Integer> getRoute() {
        if (orderList.isEmpty()) return Collections.emptyList();

        List<Integer> route = new ArrayList<Integer>();
        // start
        route.add(startCenter);
        for (Order order : orderList) {
            route.add(order.getDestIdx());
        }
        // route.add(startCenter); have to be back?

        return route;
    }

    public double getMaxCapa() {
        if (orderList.isEmpty()) return 0;


        double ret = 0, temp = 0;
        for (Order order : orderList) {
            if (order.getOrderId().equals(ORDER_ID_NULL)) {
                ret = (ret>=temp) ? ret : temp;
                temp = 0;
            }
            temp += order.getCbm();
        }
        ret = (ret>=temp) ? ret : temp;

        return ret;
    }

    public int getCount() {
        int ret = 0;
        for (Order order : orderList) {
            if (order.getOrderId().equals(ORDER_ID_NULL)) continue;
            ret++;
        }
        return ret;
    }

    public int getTravelDistance() {
        int ret = 0;

        int cur = startCenter;
        for (Order order : orderList) {
            int next = order.getDestIdx();
            ret += graph.getDist(cur, next);
            cur = next;
        }

        return ret;
    }

    public int getTravelTime() {
        int ret = 0;

        int cur = startCenter;
        for (Order order : orderList) {
            int next = order.getDestIdx();
            ret += graph.getTime(cur, next);
            cur = next;
        }

        return ret;
    }

    public int getWorkTime() {
        int ret = 0;
        for (Order order: orderList)
            ret += order.getServiceTime();
        return ret;
    }

    public int getTotalCost() {
        double ret = fc + vc * getTravelDistance();
        return (int) Math.ceil(ret);
    }

    public int getWaitingTime() {
        int ret = 0;
        int curTime = freeTime, curLoc = startCenter;

        for (Order order : orderList) {
            int arrivalTime = curTime + graph.getTime(curLoc, order.getDestIdx());
            curLoc = order.getDestIdx();

            // TERMINAL LOADING
            if (order.getOrderId().equals(ORDER_ID_NULL)) {
                curTime = arrivalTime;
            }
            // DEST UNLOADING
            else {
                curTime = StartTimeCalculator.startTimeCal(arrivalTime, order.getStart(), order.getEnd()) + order.getServiceTime();
                ret += order.getServiceTime();
            }
        }

        return ret;
    }

    public int getSpentTime() {
        int curTime = freeTime, curLoc = startCenter;

        for (Order order : orderList) {
            int arrivalTime = curTime + graph.getTime(curLoc, order.getDestIdx());
            curLoc = order.getDestIdx();

            // TERMINAL LOADING
            if (order.getOrderId().equals(ORDER_ID_NULL)) {
                curTime = arrivalTime;
            }
            // DEST UNLOADING
            else {
                curTime = StartTimeCalculator.startTimeCal(arrivalTime, order.getStart(), order.getEnd()) + order.getServiceTime();
            }
        }

        return curTime - freeTime;
    }

    /**
     * Once OrderList allocated..
     */
    public void updateOrders() {
        int curTime = freeTime, curLoc = startCenter;

        for (Order order : orderList) {
            int arrivalTime = curTime + graph.getTime(curLoc, order.getDestIdx());
            curLoc = order.getDestIdx();
            order.update(curTime);
        }
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }
}

