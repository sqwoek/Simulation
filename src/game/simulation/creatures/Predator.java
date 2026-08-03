package game.simulation.creatures;

import game.simulation.Coordinates;
import game.simulation.Entity;

import java.util.Map;

public class Predator extends Creature {
    private int speed;
    private int health;
    private int attack;
    private Coordinates coordinates;

    public Predator(int speed, int attack, Coordinates coordinates) {
        this.speed = speed;
        this.health = 100;
        this.attack = attack;
        this.coordinates = coordinates;
    }

    @Override
    public void makeMove(Map<Coordinates, Entity> worldView) {
        // move or attack
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int damage) {

    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }
}
