package game.simulation.factory;

import game.simulation.actions.Action;
import game.simulation.actions.InitializeAction;

import java.util.ArrayList;
import java.util.List;

public class InitActionsFactory {
    public static List<Action> createActions() {
        List<Action> worldActions = new ArrayList<>();
        worldActions.add(new InitializeAction());
        return worldActions;
    }
}
