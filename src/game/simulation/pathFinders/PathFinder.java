package game.simulation.pathFinders;

import game.ForestMap;
import game.simulation.Coordinates;

import java.util.List;

public interface PathFinder {
    List<Coordinates> getPath(ForestMap map, Coordinates from, Coordinates to);
}
