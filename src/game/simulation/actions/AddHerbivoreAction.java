package game.simulation.actions;

import game.ForestMap;
import game.simulation.Entity;
import game.simulation.creatures.Herbivore;

public class AddHerbivoreAction implements WorldAction {
    private static final int HERBIVORE_MINIMUM = 4;

    @Override
    public void execute(ForestMap forestMap) {
        int herbivoreAmount = 0;
        for (Entity entity : forestMap.getMap().values()) {
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