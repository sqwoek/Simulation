package game.simulation.creatures;

import game.MapFabric;
import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.fieldObjects.FieldObject;

import java.util.Map;

public abstract class Creature extends Entity {
    private int speed;
    private int health;
    private Coordinates coordinates;

    public abstract int getSpeed();

    public abstract int getHealth();

    public abstract void setHealth(int damage);

    public abstract void makeMove(Map<Coordinates, Entity> worldView);

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private boolean isSquareAvailableForMove(Coordinates coordinates, MapFabric map) {
        return map.isEmpty(coordinates) || !(map.getEntity(coordinates) instanceof FieldObject fieldObject);
    }
}
