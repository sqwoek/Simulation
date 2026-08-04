package game.simulation.creatures;

import game.ForestMap;
import game.simulation.BreadthPathFinder;
import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.GameContext;

import java.util.List;
import java.util.Random;

public abstract class Creature extends Entity {
    private int speed;
    private int health;
    private final Class<? extends Entity> target;
    private final Random random = new Random();
    private final BreadthPathFinder pathFinder = new BreadthPathFinder();

    public Creature(int speed, int health, Class<? extends Entity> target) {
        this.speed = speed;
        this.health = health;
        this.target = target;
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
        if (path.isEmpty()) {
            context.randomMove(this);
            return;
        }
        context.canMove(this, path.get(0));
        context.move(this, path.get(0));
    }

    abstract void devourTarget(Coordinates targetCoords, GameContext context);

    public abstract int getSpeed();

    public abstract int getHealth();

    public abstract void setHealth(int damage);
}
