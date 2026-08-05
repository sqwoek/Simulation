package game.simulation.creatures;

import game.simulation.Entity;
import game.simulation.GameContext;

public class Predator extends Creature {
    private int speed;
    private int health;
    private int attack;

    public Predator(int speed, int health, int attack) {
        super(speed, health);
        this.speed = speed;
        this.health = health;
        this.attack = attack;
    }

    @Override
    public void devourTarget(GameContext context, Entity target) {
        if (!isFood(target)) {
            return;
        }
        if (((Creature) target).getHealth() > 0) {
            ((Creature) target).takeDamage(attack);
            return;
        }
        context.consume(this, target);
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return "Predator";
    }
}
