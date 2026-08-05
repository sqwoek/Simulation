package game.simulation.creatures;

import game.simulation.Entity;

public class Predator extends Creature {
    private int speed;
    private int health;
    private int attack;

    public Predator(int speed, int health, int attack) {
        super(speed, health);
        this.speed = speed;
        this.health = health;
        this.attack = attack;
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return "Predator";
    }
}
