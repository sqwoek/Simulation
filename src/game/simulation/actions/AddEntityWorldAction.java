package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.GameContext;
import game.simulation.creatures.Herbivore;
import game.simulation.fieldObjects.Grass;

public class AddEntityWorldAction implements WorldAction {
    private final Entity entityInstance;

    public AddEntityWorldAction(Entity entity) {
        this.entityInstance = entity;
    }

    @Override
    public void execute(GameContext gameContext) {
        if (gameContext.needAddEntity(entityInstance)) {
            Coordinates coords = gameContext.getEmptyCell();
            if (entityInstance instanceof Grass) {
                gameContext.addEntity(new Grass(), coords);
            }
            if (entityInstance instanceof Herbivore) {
                gameContext.addEntity(new Herbivore(1, 50), coords);
            }
        }
    }
}