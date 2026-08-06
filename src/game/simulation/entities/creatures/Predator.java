package game.simulation.entities.creatures;

import game.simulation.entities.Entity;
import game.simulation.SimulationContext;

public class Predator extends Creature {
    private final int attack;

    public Predator(int speed, int health, int attack) {
        super(speed, health);
        this.attack = attack;
    }

    @Override
    public void devourTarget(SimulationContext context, Entity target) {
        if (!canInteract(context, target)) {
            return;
        }
        if (target instanceof Creature creature && creature.isAlive()) {
            context.attack(this, creature, attack);
        } else {
            context.consume(this, target);
        }
    }

    private boolean canInteract(SimulationContext context, Entity target) {
        if (!isFood(target)) {
            return false;
        }
        return context.isTargetClose(this, target);
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }
}
