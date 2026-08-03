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
        int steps = speed;
        while (steps > 0) {
            Coordinates currentCoords = forestMap.getCurrentCoords(this);
            if (currentCoords == null) {
                return;
            }
            Coordinates targetCoords = forestMap.getNearestTargetCords(this, target);
            if (targetCoords == null) {
                wanderAround(forestMap);
                steps--;
                continue;
            }
            System.out.println(this + " " + currentCoords + " see target at: " + targetCoords);

            if (forestMap.isTargetClose(this, targetCoords)) {
                devourTarget(targetCoords, forestMap);
                break;
            }
            moveToTarget(targetCoords, forestMap);
            steps--;
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
        // going forward to the target
        if (forestMap.isSquareGoodForMove(this, nextStep)) {
            forestMap.moveCreatureTo(this, nextStep);
            return;
        }

        // if the path is blocked, try to go in x direction
        if (x != targetX) {
            Coordinates up = new Coordinates(x, y - 1);
            Coordinates down = new Coordinates(x, y + 1);

            if (forestMap.isSquareGoodForMove(this, up)) {
                forestMap.moveCreatureTo(this, up);
                return;
            }
            if (forestMap.isSquareGoodForMove(this, down)) {
                forestMap.moveCreatureTo(this, down);
                return;
            }
        }

        // if the path is blocked, try to go in y direction
        if (y != targetY) {
            Coordinates left = new Coordinates(x - 1, y);
            Coordinates right = new Coordinates(x + 1, y);

            if (forestMap.isSquareGoodForMove(this, left)) {
                forestMap.moveCreatureTo(this, left);
                return;
            }
            if (forestMap.isSquareGoodForMove(this, right)) {
                forestMap.moveCreatureTo(this, right);
                return;
            }
        }

        // if either of paths is blocked, go to randoms square
        wanderAround(forestMap);
    }

    abstract void devourTarget(Coordinates targetCoords, ForestMap forestMap);

    private void wanderAround(ForestMap forestMap) {
        Coordinates coordinates = forestMap.getCurrentCoords(this);
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
