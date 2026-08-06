package game.simulation.factory;

import game.simulation.actions.AddEntityAction;
import game.simulation.actions.Action;
import game.simulation.actions.MoveAnimalsAction;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.fieldObjects.Grass;

import java.util.ArrayList;
import java.util.List;

public class TurnActionsFactory {
    public static List<Action> createActions() {
        List<Action> worldActions = new ArrayList<>();

        worldActions.add(new AddEntityAction(Grass.class));
        worldActions.add(new AddEntityAction(Herbivore.class));

        worldActions.add(new MoveAnimalsAction());
        return worldActions;
    }
}
