package game.simulation.creatures;

import game.simulation.Entity;
import game.simulation.GameContext;

public class Predator extends Creature {
    private int attack;

    public Predator(int speed, int health, int attack) {
        super(speed, health);
        this.attack = attack;
    }

    @Override
    public void devourTarget(GameContext context, Entity target) {
        if (!isFood(target)) {
            return;
        }
        if (target instanceof Creature) {
            ((Creature) target).takeDamage(attack);
            if (((Creature) target).getHealth() <= 0) {
                context.consume(this, target);
            }
        }
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    public String toString() {
        return "Predator";
    }
}
