package game.simulation.creatures;

import game.ForestMap;
import game.simulation.Coordinates;
import game.simulation.fieldObjects.Grass;

public class Herbivore extends Creature {
    private int speed;
    private int health;

    public Herbivore(int speed, int health) {
        super(speed, health);
        this.speed = speed;
        this.health = health;
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getHealth() {
        System.out.println("Rabbit's health is: " + this.health);
        return this.health;
    }

    @Override
    public String toString() {
        return "Herbivore";
    }
}
