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
        Coordinates targetCoords = context.findNearestTarget(this);
        if (targetCoords == null) {
            context.randomMove(this);
            return;
        }
        if (context.isTargetClose(this, targetCoords)) {
            devourTarget(targetCoords, context);
            return;
        }
        List<Coordinates> path = context.findPath(this, targetCoords);
        if (!path.isEmpty()) {
            System.out.println("I'm " + this + " gonna move to " + targetCoords);
            context.move(this, path.get(0));
        } else {
            System.out.println(this + " " + context.getCoordinates(this) + ": Path is empty");
        }
    }

    public void devourTarget(Coordinates targetCoords, GameContext context) {
        context.devourEntity(this, targetCoords);
    }

    public abstract boolean isFood(Entity entity);

    public abstract int getSpeed();

    public abstract int getHealth();
}
