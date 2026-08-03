package game.simulation.creatures;

import game.ForestMap;
import game.simulation.Coordinates;
import game.simulation.Entity;

import java.util.List;
import java.util.Random;

public abstract class Creature extends Entity {
    private int speed;
    private int health;
    private final Class<? extends Entity> target;
    private final Random random = new Random();

    public Creature(int speed, int health, Class<? extends Entity> target) {
        this.speed = speed;
        this.health = health;
        this.target = target;
    }

    public void makeMove(ForestMap forestMap) {
        for (int i = 0; i < speed; i++) {
            Coordinates currentCoords = forestMap.getCurrentCoords(this);
            if (currentCoords == null) {
                return;
            }
            Coordinates targetCoords = forestMap.getNearestTargetCords(this, target);
            if (targetCoords == null) {
                wanderAround(currentCoords, forestMap);
                continue;
            }

            if (forestMap.isTargetClose(this, targetCoords)) {
                devourTarget(targetCoords, forestMap);
            } else {
                moveToTarget(targetCoords, forestMap);
            }
        }
    }

    private void moveToTarget(Coordinates targetCoords, ForestMap forestMap) {
        Coordinates currentCoords = forestMap.getCurrentCoords(this);

        int x = currentCoords.getX();
        int y = currentCoords.getY();

        int targetX = targetCoords.getX();
        int targetY = targetCoords.getY();

        Coordinates nextStep = currentCoords;

        if (x < targetX) {
            nextStep = new Coordinates(x + 1, y);
        } else if (x > targetX) {
            nextStep = new Coordinates(x - 1, y);
        } else if (y < targetY) {
            nextStep = new Coordinates(x, y + 1);
        } else if (y > targetY) {
            nextStep = new Coordinates(x, y - 1);
        }
        if (forestMap.isSquareGoodForMove(this, nextStep)) {
            forestMap.moveCreatureTo(this, nextStep);
        }
    }

    abstract void devourTarget(Coordinates targetCoords, ForestMap forestMap);

    private void wanderAround(Coordinates coordinates, ForestMap forestMap) {
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
            if (forestMap.isSquareGoodForMove(this, target)) {
                forestMap.moveCreatureTo(this, target);
                System.out.println(this + " moved to: " + coordinates);
                return;
            } else {
                mistakes++;
                if (mistakes == 100) {
                    throw new ArithmeticException("Need to solve that later...");
                }
            }
        }
    }

    public abstract int getSpeed();

    public abstract int getHealth();

    public abstract void setHealth(int damage);
}
