package game.simulation.entities.creatures;

import game.simulation.entities.Entity;
import game.simulation.SimulationContext;

import java.util.Optional;

public abstract class Creature extends Entity {
    private final int speed;
    private int health;

    public Creature(int speed, int health) {
        this.speed = speed;
        this.health = health;
    }

    public void makeMove(SimulationContext context) {
        for (int step = 0; step < speed; step++) {
            Optional<Entity> neighborFood = context.getNeighbourFood(this);
            if (neighborFood.isPresent()) {
                devourTarget(context, neighborFood.get());
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
        return health > 0;
    }

    public abstract void devourTarget(SimulationContext context, Entity target);

    public abstract boolean isFood(Entity entity);

    public int getHealth() {
        return health;
    }
}
