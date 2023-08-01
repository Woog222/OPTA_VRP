package problem;

import object.*;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.List;

@PlanningSolution
public class VRPSolution {

    private String name;

    @ProblemFactCollectionProperty
    private Graph graph;
    @ProblemFactCollectionProperty
    private List<Location> locationList;

    @ProblemFactCollectionProperty
    private List<Terminal> terminalList;

    @PlanningEntityCollectionProperty
    private List<Vehicle> vehicleList;

    @ProblemFactCollectionProperty
//     @ValueRangeProvider
    private List<Dest> destList;

    @PlanningScore
    private HardMediumSoftLongScore score;

    public VRPSolution() {
    }

    public VRPSolution(String name,
                                  List<Location> locationList, List<Terminal> terminalList, List<Vehicle> vehicleList, List<Dest> destList,
                                  Location southWestCorner, Location northEastCorner) {
        this.name = name;
        this.locationList = locationList;
        this.terminalList = terminalList;
        this.vehicleList = vehicleList;
        this.destList = destList;
    }

    /*
    public static VRPSolution empty() {
        VRPSolution problem = DemoDataBuilder.builder().setMinDemand(1).setMaxDemand(2)
                .setVehicleCapacity(77).setCustomerCount(77).setVehicleCount(7).setDepotCount(1)
                .setSouthWestCorner(new Location(0L, 51.44, -0.16))
                .setNorthEastCorner(new Location(0L, 51.56, -0.01)).build();

        problem.setScore(HardSoftLongScore.ZERO);

        return problem;
    }
    */


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Dest> getDestList() {
        return destList;
    }

    public void setDestList(List<Dest> destList) {
        this.destList = destList;
    }

    public HardMediumSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftLongScore score) {
        this.score = score;
    }

}
