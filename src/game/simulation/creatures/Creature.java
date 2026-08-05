package game.simulation.creatures;

import game.simulation.Entity;
import game.simulation.GameContext;

public abstract class Creature extends Entity {
    private int speed;
    private int health;

    public Creature(int speed, int health) {
        this.speed = speed;
        this.health = health;
    }

    public void makeMove(GameContext context) {
        for (int step = 0; step < speed; step++) {
            Entity neighborFood = context.getNeighbourFood(this);
            if (neighborFood != null) {
                devourTarget(context, neighborFood);
                return;
            }
            if (context.hasTarget(this)) {
                context.moveTowardsTarget(this);
            } else {
                context.randomMove(this);
            }
        }
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
    }

    public boolean isAlive() {
        return getHealth() > 0;
    }

    public abstract void devourTarget(GameContext context, Entity target);

    public abstract boolean isFood(Entity entity);

    public int getHealth() {
        return health;
    }
}
