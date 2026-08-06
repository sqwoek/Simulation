package game.simulation.pathFinders;

import game.simulation.SimulationMap;
import game.simulation.Coordinates;

import java.util.*;

public class BreadthPathFinder implements PathFinder{
    private static final List<Coordinates> DIRECTIONS = List.of(
            new Coordinates(1, 0),
            new Coordinates(-1, 0),
            new Coordinates(0, 1),
            new Coordinates(0, -1)
    );

    @Override
    public List<Coordinates> getPath(SimulationMap map, Coordinates from, Coordinates to) {
        if (from.equals(to)) {
            return Collections.emptyList();
        }

        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();
        Set<Coordinates> visited = new HashSet<>();

        queue.add(from);
        visited.add(from);
        cameFrom.put(from, null);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            if (current.equals(to)) {
                return buildPath(cameFrom, from, to);
            }
            for (Coordinates dir : DIRECTIONS) {
                Coordinates next = current.shift(dir.x(), dir.y());
                if (!map.isWithinBorders(next)) {
                    continue;
                }

                boolean isTarget = next.equals(to);
                boolean isFree = map.isEmpty(next);

                if (!isFree && !isTarget) {
                    continue;
                }
                if (visited.contains(next)) {
                    continue;
                }
                cameFrom.put(next, current);
                visited.add(next);
                queue.add(next);
            }
        }
        return Collections.emptyList();
    }

    private List<Coordinates> buildPath(Map<Coordinates, Coordinates> cameFrom, Coordinates from, Coordinates to) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = to;
        while (!current.equals(from)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
