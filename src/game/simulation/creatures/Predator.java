package game.simulation.creatures;

public class Predator extends Creature {
    private int speed;
    private int health;
    private int attack;

    public Predator(int speed, int attack) {
        this.speed = speed;
        this.health = 100;
        this.attack = attack;
    }

    @Override
    public void makeMove() {
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
}
