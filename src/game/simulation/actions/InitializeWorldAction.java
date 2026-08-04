package game.simulation.actions;

import game.ForestMap;
import game.simulation.Coordinates;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.FieldObject;
import game.simulation.fieldObjects.Grass;
import game.simulation.fieldObjects.Rock;
import game.simulation.fieldObjects.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitializeWorldAction implements WorldAction {
    private static final int MAP_SIZE = 10;
    private final Random random = new Random();

    @Override
    public void execute(ForestMap forestMap) {
        List<Creature> creatures = new ArrayList<>();
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Predator(2, 100, 35));
        creatures.add(new Predator(2, 100, 35));

        for (int i = 1; i <= MAP_SIZE; i++) {
            for (int j = 1; j <= MAP_SIZE; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                int rndm = random.nextInt(MAP_SIZE);
                if (rndm == 0) {
                    forestMap.placeEntity(coordinates, new Rock());
                } else if (rndm == 1) {
                    forestMap.placeEntity(coordinates, new Tree());
                } else if (rndm == 2) {
                    forestMap.placeEntity(coordinates, new Grass());
                }
            }
        }

        for (Creature creature : creatures) {
            int x = random.nextInt(1, MAP_SIZE);
            int y = random.nextInt(1, MAP_SIZE);
            forestMap.placeEntity(new Coordinates(x, y), creature);
        }
    }
}
