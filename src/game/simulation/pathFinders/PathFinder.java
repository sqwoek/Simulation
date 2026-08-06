package game.simulation.pathFinders;

import game.simulation.SimulationMap;
import game.simulation.Coordinates;

import java.util.List;

public interface PathFinder {
    List<Coordinates> getPath(SimulationMap map, Coordinates from, Coordinates to);
}
