package game.simulation.factory;

import game.simulation.actions.WorldAction;
import game.simulation.actions.AddGrassAction;
import game.simulation.actions.AddHerbivoreAction;
import game.simulation.actions.MoveAnimalsAction;

import java.util.ArrayList;
import java.util.List;

public class TurnActionsFactory {
    public static List<WorldAction> createActions() {
        List<WorldAction> worldActions = new ArrayList<>();
        worldActions.add(new AddGrassAction());
        worldActions.add(new AddHerbivoreAction());
        worldActions.add(new MoveAnimalsAction());
        return worldActions;
    }
}
