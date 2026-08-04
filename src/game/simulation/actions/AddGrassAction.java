package game.simulation.actions;

import game.ForestMap;
import game.simulation.Entity;
import game.simulation.fieldObjects.Grass;

public class AddGrassAction implements Action{
    private static final int GRASS_MINIMUM = 4;

    @Override
    public void execute(ForestMap forestMap) {
        int grassAmount = 0;
        for (Entity entity : forestMap.getMap().values()) {
            if (entity instanceof Grass) {
                grassAmount++;
            }
        }
        if (grassAmount < GRASS_MINIMUM) {
            int needToAddQuantity = GRASS_MINIMUM - grassAmount;
            for (int i = 0; i < needToAddQuantity; i++) {
                forestMap.addGrass();
            }
        }
    }
}
