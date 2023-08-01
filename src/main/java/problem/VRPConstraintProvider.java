package problem;

import object.Vehicle;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class VRPConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                capacityConstraintHard(constraintFactory),
                timeConstarintMedium(constraintFactory),
                costConstraintSoft(constraintFactory),
        };
    }


    // Hard Constraint: Penalize vehicle capacity violations severely
    protected Constraint capacityConstraintHard(ConstraintFactory factory) {
        return factory.forEach(Vehicle.class)
                .filter(vehicle -> vehicle.getMaxCapa() > vehicle.getCapa())
                .penalize(HardMediumSoftScore.ONE_HARD,
                        vehicle -> (int) Math.ceil(vehicle.getMaxCapa() - vehicle.getCapa()))
                .asConstraint("CapacityConstarintHard");
    }

    // Medium Constraint: Penalize vehicle capacity violations moderately
    protected Constraint timeConstarintMedium(ConstraintFactory factory) {
        return factory.forEach(Vehicle.class)
                .penalize(HardMediumSoftScore.ONE_MEDIUM, vehicle -> vehicle.getWaitingTime() )
                .asConstraint("timeConstraintMedium");
    }

    // Soft Constraint: Penalize vehicle capacity violations slightly
    protected Constraint costConstraintSoft(ConstraintFactory factory) {
        return factory.forEach(Vehicle.class)
                .penalize(HardMediumSoftScore.ONE_SOFT, vehicle -> vehicle.getTotalCost() )
                .asConstraint("costConstraintSoft");
    }
}
