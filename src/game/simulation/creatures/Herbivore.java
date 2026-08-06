package game.simulation.creatures;

import game.simulation.Entity;
import game.simulation.GameContext;
import game.simulation.fieldObjects.Grass;

public class Herbivore extends Creature {

    public Herbivore(int speed, int health) {
        super(speed, health);
    }

    @Override
    public void devourTarget(GameContext context, Entity target) {
        if (!isFood(target)) {
            return;
        }
        context.consume(this, target);
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }
}
