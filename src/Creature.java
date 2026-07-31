public abstract class Creature extends Entity{
    protected final int speed;
    protected final Food foodType;
    protected int HP;

    public Creature(int speed, Food foodType, int HP) {
        this.speed = speed;
        this.foodType = foodType;
        this.HP = HP;
    }

    public abstract void makeMove();
    public abstract void eat();

    protected int getHP() {
        return HP;
    }

    protected void setHP(int HP) {
        this.HP = HP;
    }
}
