package game.simulation.creatures;

import game.simulation.Entity;
import game.simulation.GameContext;

public class Predator extends Creature {
    private final int attack;

    public Predator(int speed, int health, int attack) {
        super(speed, health);
        this.attack = attack;
    }

    @Override
    public void devourTarget(GameContext context, Entity target) {
        if (!canInteract(context, target)) {
            return;
        }
        if (target instanceof Creature creature && creature.isAlive()) {
            context.attack(this, creature, attack);
        } else {
            context.consume(this, target);
        }
    }

    private boolean canInteract(GameContext context, Entity target) {
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
