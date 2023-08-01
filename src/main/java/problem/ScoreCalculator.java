package problem;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import problem.VRPSolution;

import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<VRPSolution, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(VRPSolution vrpSolution) {
        return null;
    }
}
