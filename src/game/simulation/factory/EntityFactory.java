package game.simulation.factory;

import game.simulation.entities.Entity;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.creatures.Predator;
import game.simulation.entities.fieldObjects.Grass;
import game.simulation.entities.fieldObjects.Rock;
import game.simulation.entities.fieldObjects.Tree;

public class EntityFactory {
    private final static int HERBIVORE_SPEED = 1;
    private final static int HERBIVORE_HEALTH = 50;
    private final static int PREDATOR_SPEED = 2;
    private final static int PREDATOR_HEALTH = 100;
    private final static int PREDATOR_ATTACK = 35;
    private static volatile EntityFactory instance;

    private EntityFactory() {
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            synchronized (EntityFactory.class) {
                if (instance == null) {
                    instance = new EntityFactory();
                }
            }
        }
        return instance;
    }

    public Entity create(Class<? extends Entity> entityClass) {
        if (entityClass == Herbivore.class) {
            return new Herbivore(HERBIVORE_SPEED, HERBIVORE_HEALTH);
        }
        if (entityClass == Predator.class) {
            return new Predator(PREDATOR_SPEED, PREDATOR_HEALTH, PREDATOR_ATTACK);
        }
        if (entityClass == Rock.class) {
            return new Rock();
        }
        if (entityClass == Tree.class) {
            return new Tree();
        }
        if (entityClass == Grass.class) {
            return new Grass();
        }
        throw new RuntimeException("Unknow entity class " + entityClass);
    }
}