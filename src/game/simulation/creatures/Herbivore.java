package game.simulation.creatures;

public class Herbivore extends Creature {
    private int speed;
    private int health;

    public Herbivore(int speed) {
        this.speed = speed;
        this.health = 100;
    }

    @Override
    public void makeMove() {
        // move or eat
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
}
