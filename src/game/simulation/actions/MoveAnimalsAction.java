package game.simulation.actions;

import game.ForestMap;
import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class MoveAnimalsAction implements Action{
    @Override
    public void execute(ForestMap forestMap) {
        List<Creature> creatures = new ArrayList<>();
        for (Entity entity : forestMap.getMap().values()) {
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }

        for (Creature creature : creatures) {
            Coordinates currentCoords = forestMap.getCurrentCoords(creature);
            if (currentCoords == null) {
                continue;
            }
            creature.makeMove(forestMap);
            Coordinates newCoords = forestMap.getCurrentCoords(creature);
            if (!currentCoords.equals(newCoords)) {
                forestMap.moveEntityTo(creature, newCoords);
            }
        }
    }
}
