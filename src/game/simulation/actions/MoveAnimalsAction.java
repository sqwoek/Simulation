package game.simulation.actions;

import game.simulation.SimulationContext;
import game.simulation.entities.Entity;
import game.simulation.entities.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class MoveAnimalsAction implements Action {
    @Override
    public void execute(SimulationContext simulationContext) {
        List<Creature> creatures = new ArrayList<>();
        for (Entity entity : simulationContext.getMap().values()) {
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }

        for (Creature creature : creatures) {
            if (simulationContext.getCoordinates(creature).isEmpty()) {
                continue;
            }
            if (creature.isAlive()) {
                creature.makeMove(simulationContext);
            }
        }
    }
}
