package game.simulation.creatures;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.fieldObjects.Grass;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Herbivore extends Creature {
    private int speed;
    private int health;
    private Coordinates coordinates;
    private Random random = new Random(); // temporary

    public Herbivore(int speed, Coordinates coordinates) {
        this.speed = speed;
        this.health = 100;
        this.coordinates = coordinates;
    }

    @Override
    public void makeMove(Map<Coordinates, Entity> worldView) {
        Coordinates grassCord = findGrass(worldView);
        if (grassCord != null) {
            System.out.println("I see grass");
            if (coordinates.equals(grassCord)) {
                //            eatGrass
                System.out.println("om-nom-nom");
            }
            moveToGrass(grassCord, worldView);
        } else {
            System.out.println("MOVING");
            moveRandom(worldView);
        }
    }

    private void moveToGrass(Coordinates grassCord, Map<Coordinates, Entity> worldView) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        int grassX = grassCord.getX();
        int grassY = grassCord.getY();

        if (y < grassY) {
            Coordinates right = new Coordinates(x + 1, y);
            if (canMoveTo(right, worldView)) {
                coordinates = right;
                return;
            }
        } else if (y > grassY) {
            Coordinates left = new Coordinates(x - 1, y);
            if (canMoveTo(left, worldView)) {
                coordinates = left;
                return;
            }
        }
        if (x < grassX) {
            Coordinates down = new Coordinates(x, y + 1);
            if (canMoveTo(down, worldView)) {
                coordinates = down;
            }
        } else if (x > grassX) {
            Coordinates up = new Coordinates(x, y - 1);
            if (canMoveTo(up, worldView)) {
                coordinates = up;
            }
        }
    }

    private boolean canMoveTo(Coordinates cord, Map<Coordinates, Entity> worldView) {
        if (cord.getX() <= 0 || cord.getY() <= 0 ||
                cord.getX() > 5 || cord.getY() > 5) {
            return false;
        }
        Entity entity = worldView.get(cord);
        return entity == null || entity instanceof Grass || entity == this;
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
                System.out.println("Herbivore: moved to: " + coordinates);
                return;
            } else {
                mistakes++;
                if (mistakes == 20) {
                    throw new ArithmeticException("Need to solve that later...");
                }
            }
        }
    }

    private Coordinates findGrass(Map<Coordinates, Entity> worldView) {
        for (Map.Entry<Coordinates, Entity> entry : worldView.entrySet()) {
            if (entry.getValue() instanceof Grass) {
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
        health = health - damage;
        if (health <= 0) {
            // dead;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
