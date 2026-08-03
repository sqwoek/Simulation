package game.simulation.creatures;

import game.simulation.Coordinates;
import game.simulation.Entity;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Predator extends Creature {
    private int speed;
    private int health;
    private int attack;

    public Predator(int speed, int health, int attack) {
        super(speed, health, Herbivore.class);
        this.speed = speed;
        this.health = health;
        this.attack = attack;
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
    public String toString() {
        return "Predator";
    }
}
