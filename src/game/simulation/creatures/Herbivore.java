package game.simulation.creatures;

import game.simulation.Entity;
import game.simulation.GameContext;
import game.simulation.fieldObjects.Grass;

public class Herbivore extends Creature {
    private int speed;
    private int health;

    public Herbivore(int speed, int health) {
        super(speed, health);
        this.speed = speed;
        this.health = health;
    }

    @Override
    public void devourTarget(GameContext context, Entity target) {
        if (isFood(target)) {
            return;
        }
        context.consume(this, target);
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
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
