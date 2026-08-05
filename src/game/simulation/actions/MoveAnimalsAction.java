package game.simulation.actions;

import game.simulation.Entity;
import game.simulation.GameContext;
import game.simulation.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class MoveAnimalsAction implements WorldAction {
    @Override
    public void execute(GameContext gameContext) {
        List<Creature> creatures = new ArrayList<>();
        for (Entity entity : gameContext.getMap().values()) {
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }

        for (Creature creature : creatures) {
            creature.makeMove(gameContext);
        }
    }
}
