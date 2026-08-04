package game.simulation.creatures;

import game.ForestMap;
import game.simulation.Coordinates;
import game.simulation.fieldObjects.Grass;

public class Herbivore extends Creature {
    private int speed;
    private int health;

    public Herbivore(int speed, int health) {
        super(speed, health, Grass.class);
        this.speed = speed;
        this.health = health;
    }

    @Override
    void devourTarget(Coordinates targetCoords, ForestMap forestMap) {
        forestMap.removeEntity(targetCoords);
        forestMap.moveEntityTo(this, targetCoords);
        System.out.println("Rabbit ate a grass at: " + targetCoords);
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
    public void setHealth(int damage) {
    }

    @Override
    public String toString() {
        return "Herbivore";
    }
}
