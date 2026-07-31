public class Predator extends Creature{
    private int attack;

    public Predator(int speed, Food foodType, int HP, int attack) {
        super(speed, foodType, HP);
        this.attack = attack;
    }

    @Override
    public void makeMove() {

    }

    @Override
    public void eat() {

    }
}
