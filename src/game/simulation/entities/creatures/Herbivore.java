package game.simulation.entities.creatures;

import game.simulation.entities.Entity;
import game.simulation.SimulationContext;
import game.simulation.entities.fieldObjects.Grass;

public class Herbivore extends Creature {

    public Herbivore(int speed, int health) {
        super(speed, health);
    }

    @Override
    public void devourTarget(SimulationContext context, Entity target) {
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
