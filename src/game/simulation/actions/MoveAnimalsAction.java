package game.simulation.actions;

import game.ForestMap;
import game.simulation.Entity;
import game.simulation.GameContext;
import game.simulation.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class MoveAnimalsAction implements WorldAction {
    private final GameContext gameContext;

    public MoveAnimalsAction(GameContext gameContext) {
        this.gameContext = gameContext;
    }
    @Override
    public void execute(ForestMap forestMap) {
        List<Creature> creatures = new ArrayList<>();
        for (Entity entity : forestMap.getMap().values()) {
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }

        for (Creature creature : creatures) {
            creature.makeMove(gameContext);
        }
    }
}
