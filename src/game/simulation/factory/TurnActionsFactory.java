package game.simulation.factory;

import game.simulation.Entity;
import game.simulation.actions.AddEntityWorldAction;
import game.simulation.actions.WorldAction;
import game.simulation.actions.MoveAnimalsAction;
import game.simulation.creatures.Herbivore;
import game.simulation.fieldObjects.Grass;

import java.util.ArrayList;
import java.util.List;

public class TurnActionsFactory {
    public static List<WorldAction> createActions() {
        EntityFactory entityFactory = EntityFactory.getInstance();
        List<WorldAction> worldActions = new ArrayList<>();

        Entity grass = entityFactory.create(Grass.class);
        Entity herbivore = entityFactory.create(Herbivore.class);

        worldActions.add(new AddEntityWorldAction(grass));
        worldActions.add(new AddEntityWorldAction(herbivore));

        worldActions.add(new MoveAnimalsAction());
        return worldActions;
    }
}
