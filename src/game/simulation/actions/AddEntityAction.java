package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.SimulationContext;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.Entity;
import game.simulation.factory.EntityFactory;
import game.simulation.entities.fieldObjects.Grass;

public class AddEntityAction implements Action {
    private static final int GRASS_MINIMUM = 4;
    private static final int HERBIVORE_MINIMUM = 4;
    private final Class<? extends Entity> entityClass;
    private final EntityFactory entityFactory;

    public AddEntityAction(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
        this.entityFactory = EntityFactory.getInstance();
    }

    @Override
    public void execute(SimulationContext simulationContext) {
        Entity entity = entityFactory.create(entityClass);
        if (needAddEntity(simulationContext, entity)) {
            Coordinates coordinates = simulationContext.getEmptyCell();
            simulationContext.addEntity(entity, coordinates);
        }
    }

    private boolean needAddEntity(SimulationContext simulationContext, Entity entity) {
        long count = simulationContext.getEntityTypeCount(entity);

        if (entity instanceof Grass) {
            return count < GRASS_MINIMUM;
        }
        if (entity instanceof Herbivore) {
            return count < HERBIVORE_MINIMUM;
        }
        return false;
    }
}