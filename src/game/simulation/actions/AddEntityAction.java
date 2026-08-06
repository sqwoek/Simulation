package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.SimulationContext;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.Entity;
import game.simulation.factory.EntityFactory;
import game.simulation.entities.fieldObjects.Grass;

public class AddEntityAction implements Action {
    private final int entityMinimum;
    private final Class<? extends Entity> entityClass;
    private final EntityFactory entityFactory;

    public AddEntityAction(EntityFactory entityFactory, Class<? extends Entity> entityClass, int entityMinimum) {
        this.entityClass = entityClass;
        this.entityFactory = entityFactory;
        this.entityMinimum = entityMinimum;
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
        return count < entityMinimum;
    }
}