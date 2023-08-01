import java.util.List;
import config.*;
import object.*;
import init.*;
import problem.*;
import org.optaplanner.core.api.solver.SolverFactory;

public class Main {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Working Directory: " + workingDir);

        Graph graph = new Graph(CONFIG.GRAPH_DIR);
        VehicleTable vehicles = new VehicleTable(CONFIG.VEHICLE_DIR, graph);
        OrderTable orders = new OrderTable(CONFIG.ORDER_DIR, graph);

        VRPSolution solutionInitializer = new InitSolutionGenerator(graph, orders, vehicles, 0);
        List<Vehicle> initialSolution = solutionInitializer.getInitSolution();

        SolverFactory<VRPSolution> solverFactory = SolverFactory.createFromXmlResource("solverConfig.xml");
        Solver<VRPSolution> solver = solverFactory.buildSolver();

        VRPSolution finalSolution = solver.solve(initialSolution);
    }
}
