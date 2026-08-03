package game.simulation.creatures;

import game.simulation.Coordinates;
import game.simulation.Entity;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Predator extends Creature {
    private Random random = new Random();
    private int speed;
    private int health;
    private int attack;
    private Coordinates coordinates;

    public Predator(int speed, int attack, Coordinates coordinates) {
        this.speed = speed;
        this.health = 100;
        this.attack = attack;
        this.coordinates = coordinates;
    }

    @Override
    public void makeMove(Map<Coordinates, Entity> worldView) {
        Coordinates preyCord = findPrey(worldView);
        if (preyCord != null) {
            System.out.println("I see prey");
            if (coordinates.equals(preyCord)) {
                //            eatPrey
                System.out.println("PREY IS CAUGHT!");
            }
            moveToTarget(preyCord, worldView);
        } else {
            moveRandom(worldView);
        }
    }

    private void moveRandom(Map<Coordinates, Entity> worldView) {
        List<Coordinates> directions = List.of(
                coordinates.shift(1, 0),
                coordinates.shift(-1, 0),
                coordinates.shift(0, 1),
                coordinates.shift(0, -1)
        );
        int mistakes = 0;

        while (true) {
            int rndm = random.nextInt(4);
            Coordinates target = directions.get(rndm);
            if (canMoveTo(target, worldView)) {
                coordinates = target;
                System.out.println("Predator: moved to: " + coordinates);
                return;
            } else {
                mistakes++;
                if (mistakes == 20) {
                    throw new ArithmeticException("Need to solve that later...");
                }
            }
        }
    }

    private boolean canMoveTo(Coordinates coordinates, Map<Coordinates, Entity> worldView) {
        if (coordinates.getX() <= 0 || coordinates.getY() <= 0 ||
                coordinates.getX() > 5 || coordinates.getY() > 5) {
            return false;
        }
        Entity entity = worldView.get(coordinates);
        return entity == null || entity instanceof Herbivore || entity == this;
    }

    private void moveToTarget(Coordinates preyCord, Map<Coordinates, Entity> worldView) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        int preyX = preyCord.getX();
        int preyY = preyCord.getY();

        if (y < preyY) {
            Coordinates right = new Coordinates(x + 1, y);
            if (canMoveTo(right, worldView)) {
                coordinates = right;
                return;
            }
        } else if (y > preyY) {
            Coordinates left = new Coordinates(x - 1, y);
            if (canMoveTo(left, worldView)) {
                coordinates = left;
                return;
            }
        }
        if (x < preyX) {
            Coordinates down = new Coordinates(x, y + 1);
            if (canMoveTo(down, worldView)) {
                coordinates = down;
            }
        } else if (x > preyX) {
            Coordinates up = new Coordinates(x, y - 1);
            if (canMoveTo(up, worldView)) {
                coordinates = up;
            }
        }
    }

    private Coordinates findPrey(Map<Coordinates, Entity> worldView) {
        for (Map.Entry<Coordinates, Entity> entry : worldView.entrySet()) {
            if (entry.getValue() instanceof Herbivore) {
                return entry.getKey();
            }
        }
        return null;
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
    public Coordinates getCoordinates() {
        return coordinates;
    }
}
