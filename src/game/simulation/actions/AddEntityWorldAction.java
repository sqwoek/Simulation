package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.GameContext;
import game.simulation.creatures.Herbivore;
import game.simulation.factory.EntityFactory;
import game.simulation.fieldObjects.Grass;

public class AddEntityWorldAction implements WorldAction {
    private final Class<? extends Entity> entityClass;
    private final EntityFactory entityFactory;

    public AddEntityWorldAction(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
        this.entityFactory = EntityFactory.getInstance();
    }

    @Override
    public void execute(GameContext gameContext) {
        Entity entity = entityFactory.create(entityClass);
        if (gameContext.needAddEntity(entity)) {
            Coordinates coords = gameContext.getEmptyCell();
            gameContext.addEntity(entity, coords);
        }
    }
}