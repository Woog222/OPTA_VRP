package problem;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import problem.VRPSolution;

import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<VRPSolution, HardSoftScore> {

    @Override
    public BendableScore calculateScore(VRPSolution vrpSolution) {
        int hardScore = calculateCBMViolationPenalty(vrpSolution);
        int softScore = calculateTotalCost(vrpsolution);
        return BendableScore.of(new int[] {hardScore}, new int[]{softScore});
    }

    private int calculateCBMViolationPenalty(VRPSolution solution) {
        //Penalty for CBM exceed
        return 0;
    }
    private int calculateTotalCost(VRPSolution solution) {
        int totalCost = 0;
        for (Vehicle vehicle: solution.getVehicles()) {
            totalCost += vehicle.getTotalCost();
        }
        return totalCost;
    }
}
