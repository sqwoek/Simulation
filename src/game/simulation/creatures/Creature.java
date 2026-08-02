package game.simulation.creatures;

import game.simulation.Coordinates;
import game.simulation.Entity;

public abstract class Creature extends Entity {
    private int speed;
    private int health;
    private Coordinates coordinates;

    public abstract void makeMove();

    public abstract int getSpeed();

    public abstract int getHealth();

    public abstract void setHealth(int damage);
}
