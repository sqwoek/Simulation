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
        for (int step = 0; step < speed; step++) {
            if (context.hasTarget(this)) {
                if (context.isTargetClose(this)) {
                    context.devourEntity(this);
                    break;
                }
                boolean moved = context.moveTowardsTarget(this);
                if (!moved) {
                    break;
                }
            } else {
                context.randomMove(this);
                break;
            }
        }
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
    }

    public abstract void devourTarget(GameContext context, Entity target);

    public abstract boolean isFood(Entity entity);

    public int getHealth() {
        System.out.println(this + " health is " + health);
        return health;
    }
}
