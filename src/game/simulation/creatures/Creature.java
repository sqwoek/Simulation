package game.simulation.creatures;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.GameContext;

import java.util.List;

public abstract class Creature extends Entity {
    private int speed;
    private int health;

    public Creature(int speed, int health) {
        this.speed = speed;
        this.health = health;
    }

    public void makeMove(GameContext context) {
        if (context.hasTarget(this)) {
            if (context.isTargetClose(this)) {
                context.devourEntity(this);
            } else {
                context.moveTowardsTarget(this);
            }
        } else {
            context.randomMove(this);
        }
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
    }

    public abstract void devourTarget(GameContext context, Entity target);

    public abstract boolean isFood(Entity entity);

    public abstract int getHealth();
}
