package game.simulation.actions;

import game.ForestMap;
import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.creatures.Herbivore;

import java.util.Map;

public class AddHerbivoreAction implements Action {
    private static final int HERBIVORE_MINIMUM = 4;

    @Override
    public void execute(ForestMap forestMap) {
        Map<Coordinates, Entity> map = forestMap.getMap();

        int herbivoreAmount = 0;
        for (Entity entity : map.values()) {
            if (entity instanceof Herbivore) {
                herbivoreAmount++;
            }
        }

        if (herbivoreAmount < HERBIVORE_MINIMUM) {
            int needToAddQuantity = HERBIVORE_MINIMUM - herbivoreAmount;
            for (int i = 0; i < needToAddQuantity; i++) {
                forestMap.addHerbivore();
            }
        }
    }
}