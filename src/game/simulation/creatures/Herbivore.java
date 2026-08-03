package game.simulation.creatures;

import game.simulation.fieldObjects.Grass;

public class Herbivore extends Creature {
    private int speed;
    private int health;

    public Herbivore(int speed, int health) {
        super(speed, health, Grass.class);
        this.speed = speed;
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
        health = health - damage;
        if (health <= 0) {
            // dead;
        }
    }

    @Override
    public String toString() {
        return "Herbivore";
    }
}
