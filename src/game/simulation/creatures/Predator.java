package game.simulation.creatures;

import game.ForestMap;
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
    void devourTarget(Coordinates targetCoords, ForestMap forestMap) {
        Entity entity = forestMap.getEntity(targetCoords);
        if (entity instanceof Herbivore herbivore) {
            herbivore.takeDamage(attack);
            System.out.println("Predator attacked rabbit at: " + targetCoords);
            if (herbivore.getHealth() <= 0) {
                forestMap.removeEntity(targetCoords);
                forestMap.moveCreatureTo(this, targetCoords);
                System.out.println("Rabbit dead at: " + targetCoords);
            }
        }
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
