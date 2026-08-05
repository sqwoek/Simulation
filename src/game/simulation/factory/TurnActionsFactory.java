package game.simulation.factory;

import game.simulation.actions.AddEntityWorldAction;
import game.simulation.actions.WorldAction;
import game.simulation.actions.MoveAnimalsAction;
import game.simulation.creatures.Herbivore;
import game.simulation.fieldObjects.Grass;

import java.util.ArrayList;
import java.util.List;

public class TurnActionsFactory {
    public static List<WorldAction> createActions() {
        List<WorldAction> worldActions = new ArrayList<>();
        worldActions.add(new AddEntityWorldAction(new Grass()));
        worldActions.add(new AddEntityWorldAction(new Herbivore(1, 50)));
        worldActions.add(new MoveAnimalsAction());
        return worldActions;
    }
}
